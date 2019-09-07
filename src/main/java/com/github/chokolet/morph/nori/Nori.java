package com.github.chokolet.morph.nori;

import com.github.chokolet.morph.MorphInterface;
import com.github.chokolet.morph.vo.MorphInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.analysis.ko.KoreanTokenizer;
import org.apache.lucene.analysis.ko.tokenattributes.PartOfSpeechAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by CK on 2019-04-22.
 * Lucene Nori Added
 */
@Slf4j
public class Nori implements MorphInterface {

    KoreanAnalyzer koreanAnalyzer = new KoreanAnalyzer(null, KoreanTokenizer.DecompoundMode.DISCARD, new HashSet<>(), false);

    // 토크닝 추출
    @Override
    public List<MorphInfoVo> getToken(String targetText) {
        return getMorphInfos(targetText, false);
    }

    // 원형 추출
    @Override
    public List<MorphInfoVo> getLemma(String targetText) {
        return getMorphInfos(targetText, true);
    }

    private List<MorphInfoVo> getMorphInfos(String targetText, boolean isOriginal) {

        List<MorphInfoVo> morphInfoVoList;
        try (TokenStream tokenStream = koreanAnalyzer.tokenStream(null, targetText)) {

            // Offset  관련 속성 추출 시
            OffsetAttribute offsetAttribute = tokenStream.getAttribute(OffsetAttribute.class);
            // 키워드 관련 속성 추출 시
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            // 테그 관련 속성 추출 시
            PartOfSpeechAttribute partOfSpeechAttribute = tokenStream.addAttribute(PartOfSpeechAttribute.class);

            tokenStream.reset();

            if (isOriginal) {
                morphInfoVoList = extractVersionOrg(tokenStream, offsetAttribute, charTermAttribute, partOfSpeechAttribute);
            } else {
                morphInfoVoList = extractVersionTxt(targetText, tokenStream, offsetAttribute, partOfSpeechAttribute);
            }

        } catch (IOException e) {
            log.warn("Nori Morph IO Warnning [{}]", e.toString());
            morphInfoVoList = new ArrayList<>();
        }
        return morphInfoVoList;

    }


    /**
     * Version 1
     * 원형 복원 추출
     * 중복위치값에 대한 부분은 제거
     * 학습할 때는 이게 맞는거 같음.. ( 원형 복원 ) /  해야되궁..
     */
    private List<MorphInfoVo> extractVersionOrg(TokenStream tokenStream, OffsetAttribute offsetAttribute, CharTermAttribute charTermAttribute, PartOfSpeechAttribute partOfSpeechAttribute) throws IOException {
        List<MorphInfoVo> morphInfoVoList = new ArrayList<>();
        int index = 0;
        int beginIdx;
        String charStr;
        while (tokenStream.incrementToken()) {
            beginIdx = offsetAttribute.startOffset();
            charStr = charTermAttribute.toString();
            morphInfoVoList.add(new MorphInfoVo(beginIdx, index, charStr, partOfSpeechAttribute.getLeftPOS().toString()));
            index++;
        }
        return morphInfoVoList;
    }

    /**
     * Version 2
     * 원문 우선 추출
     */
    private List<MorphInfoVo> extractVersionTxt(String targetText, TokenStream tokenStream, OffsetAttribute offsetAttribute, PartOfSpeechAttribute partOfSpeechAttribute) throws IOException {

        int index = 0;
        String tmpStr;
        int beginIdx;

        Map<Integer, MorphInfoVo> linkedMorphInfo = new LinkedHashMap<>();
        MorphInfoVo preMorphInfoVo = null;

        while (tokenStream.incrementToken()) {

            tmpStr = targetText.substring(offsetAttribute.startOffset(), offsetAttribute.endOffset());

            if (!ObjectUtils.isEmpty(preMorphInfoVo) && preMorphInfoVo != null
                    && !StringUtils.isEmpty(tmpStr) && tmpStr.equalsIgnoreCase(preMorphInfoVo.getKeyword())) {
                linkedMorphInfo.get(index - 1).setMorphType(preMorphInfoVo.getMorphType() + "+" + partOfSpeechAttribute.getLeftPOS());
                continue;
            }

            beginIdx = offsetAttribute.startOffset();
            preMorphInfoVo = new MorphInfoVo(beginIdx, index, tmpStr, partOfSpeechAttribute.getLeftPOS().toString());
            linkedMorphInfo.put(index, preMorphInfoVo);
            index++;
        }

        return linkedMorphInfo.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}

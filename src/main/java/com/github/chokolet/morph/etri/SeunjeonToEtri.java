package com.github.chokolet.morph.etri;

import com.github.chokolet.morph.etri.vo.MorphEvalVo;
import com.github.chokolet.morph.etri.vo.MorphVo;
import com.github.chokolet.morph.etri.vo.SentenceVo;
import com.github.chokolet.morph.etri.vo.WordVo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitbucket.eunjeon.seunjeon.Analyzer;
import org.bitbucket.eunjeon.seunjeon.Eojeol;
import org.bitbucket.eunjeon.seunjeon.LNode;
import org.bitbucket.eunjeon.seunjeon.Morpheme;
import scala.collection.JavaConverters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SeunjeonToEtri {

    /**
     * @param setId index count ( increase )
     * @return
     */
    public SentenceVo getSentenceVo(int setId, String orgSentence) {

        List<Eojeol> eojeolList = new ArrayList<>();
        SentenceVo sentenceData = new SentenceVo();
        Iterable<Eojeol> getNode = Analyzer.parseEojeolJava(orgSentence);

        sentenceData.setId(setId);
        sentenceData.setReserve_str("");
        sentenceData.setText(orgSentence);

        int endSave = -1;

        AtomicInteger morphIdx = new AtomicInteger(0);

        for (Eojeol eojeol : getNode) {

            if (endSave != -1 && endSave < eojeol.beginOffset()) {
                saveSentenceValues(eojeolList, sentenceData, morphIdx);
                eojeolList.clear();
            }

            eojeolList.add(eojeol);
            endSave = eojeol.endOffset();

        }

        if (eojeolList.size() != 0) {
            saveSentenceValues(eojeolList, sentenceData, morphIdx);
        }


        return sentenceData;
    }

    private void saveSentenceValues(List<Eojeol> eojeolList, SentenceVo sentenceVo, AtomicInteger morphIdx) {

        // 형태소 단위
        List<MorphVo> morph = sentenceVo.getMorp();
        // 어절 단위
        List<MorphEvalVo> morphEval = sentenceVo.getMorp_eval();
        // 단어 단위
        List<WordVo> word = sentenceVo.getWord();

        MorphEvalVo morphEvalVo;

        WordVo wordVo;

        int incWordMerges = -1;

        int firstMorphIdx = morphIdx.get();

        morphEvalVo = new MorphEvalVo();
        wordVo = new WordVo();

        int tmpEndOffset = -1;

        for (Eojeol eojeol : eojeolList) {

            for (LNode node : eojeol.nodesJava()) {

                if (tmpEndOffset != -1 && tmpEndOffset < node.beginOffset()) {

                    setMorphInfo(morphEval, word, morphEvalVo, wordVo, incWordMerges, firstMorphIdx);
                    morphEvalVo = new MorphEvalVo();
                    wordVo = new WordVo();

                }

                for (LNode deInf : node.deCompoundJava()) {
                    if (deInf.morpheme().deComposite().size() == 0) {
                        incWordMerges = getIncWordMerges(sentenceVo, morphIdx, morph, morphEvalVo, incWordMerges, deInf, deInf.morpheme());
                    } else {
                        List<Morpheme> morphemeList = JavaConverters.seqAsJavaList(deInf.morpheme().deComposite());
                        for (Morpheme morpheme : morphemeList) {
                            incWordMerges = getIncWordMerges(sentenceVo, morphIdx, morph, morphEvalVo, incWordMerges, deInf, morpheme);
                        }
                    }
                }
                morphEvalVo.setTarget(morphEvalVo.getTarget() + node.morpheme().getSurface());
                tmpEndOffset = node.endOffset();
            }
        }

        if (!ObjectUtils.isEmpty(morphEvalVo)) {
            setMorphInfo(morphEval, word, morphEvalVo, wordVo, incWordMerges, firstMorphIdx);
        }

    }

    private int getIncWordMerges(SentenceVo sentenceVo, AtomicInteger morphIdx, List<MorphVo> morph, MorphEvalVo morphEvalVo, int incWordMerges, LNode deInf, Morpheme morpheme2) {
        MorphVo morphVo;
        String resultTmp;
        morphVo = new MorphVo(morphIdx.getAndIncrement(), morpheme2.getSurface(), morpheme2.getFeatureHead(), sentenceVo.getText().substring(0, deInf.beginOffset() + morpheme2.getSurface().length()).getBytes().length, morpheme2.getCost());
        morph.add(morphVo);
        incWordMerges++;

        resultTmp = morpheme2.getSurface() + "/" + morpheme2.getFeatureHead();
        resultTmp = !StringUtils.isEmpty(morphEvalVo.getResult()) ? "+" + resultTmp : resultTmp;
        morphEvalVo.setResult(morphEvalVo.getResult() + resultTmp);
        return incWordMerges;
    }

    private void setMorphInfo(List<MorphEvalVo> morphEval, List<WordVo> word, MorphEvalVo morphEvalVo, WordVo wordVo, int incWordMerges, int firstMorphIdx) {
        morphEvalVo.setM_begin(firstMorphIdx);
        morphEvalVo.setM_end(firstMorphIdx + incWordMerges);
        morphEvalVo.setId(morphEval.size());
        morphEvalVo.setWord_id(morphEval.size());

        wordVo.setBegin(morphEvalVo.getM_begin());
        wordVo.setEnd(morphEvalVo.getM_end());
        wordVo.setType("");
        wordVo.setText(morphEvalVo.getTarget());
        wordVo.setId(word.size());

        morphEval.add(morphEvalVo);
        word.add(wordVo);
    }
}

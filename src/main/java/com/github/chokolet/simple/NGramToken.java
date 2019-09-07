package com.github.chokolet.simple;

import com.github.chokolet.morph.MorphInterface;
import com.github.chokolet.morph.vo.MorphInfoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DataSolution on 2019-01-30.
 * Ngram Tokenizer
 */
public class NGramToken implements MorphInterface {

    private int nCountOpt;

    /**
     * NGram Option
     *
     * @param getCnt
     */
    public NGramToken(int getCnt) {
        nCountOpt = getCnt;
    }

    /**
     * Parse
     *
     * @param targetText
     * @return
     */
    @Override
    public List<MorphInfoVo> getToken(String targetText) {

        List<MorphInfoVo> morphInfoVoList = new ArrayList<>();
        int idx = 0;

        String tmpMorphStr;
        int tmpBeginIdx;
        char[] getTargetText;

        SpaceToken spaceToken = new SpaceToken();
        List<MorphInfoVo> tmpMorph = spaceToken.getToken(targetText);

        StringBuilder stackText;

        for (MorphInfoVo morphInfoVo : tmpMorph) {

            tmpMorphStr = morphInfoVo.getKeyword();
            tmpBeginIdx = morphInfoVo.getOrgBegin();
            getTargetText = tmpMorphStr.toCharArray();

            if (getTargetText.length + 1 - nCountOpt <= 0) {
                morphInfoVoList.add(new MorphInfoVo(tmpBeginIdx, idx, tmpMorphStr));
                continue;
            }

            stackText = new StringBuilder();

            for (int pos = 0; pos < getTargetText.length + 1 - nCountOpt; pos += nCountOpt) {

                morphInfoVoList.add(new MorphInfoVo(pos + tmpBeginIdx, idx, tmpMorphStr.substring(pos, pos + nCountOpt)));
                idx++;
                stackText.append(tmpMorphStr.substring(pos, pos + nCountOpt));

            }

            if (tmpMorphStr.length() > stackText.length()) {

                int last = tmpMorphStr.length() - stackText.length();
                String lastTxt = tmpMorphStr.substring(stackText.length() + last - 1);
                int lastPos = tmpBeginIdx + stackText.length();
                morphInfoVoList.add(new MorphInfoVo(lastPos, idx, lastTxt));
                idx++;

            }

        }

        return morphInfoVoList;
    }

    @Override
    public List<MorphInfoVo> getLemma(String targetText) {
        List<MorphInfoVo> morphInfoVoList = new ArrayList<>();
        int idx = 0;

        String tmpMorphStr;
        int tmpBeginIdx;
        char[] getTargetText;

        SpaceToken spaceToken = new SpaceToken();
        List<MorphInfoVo> tmpMorph = spaceToken.getToken(targetText);

        for (MorphInfoVo morphInfoVo : tmpMorph) {

            tmpMorphStr = morphInfoVo.getKeyword();
            tmpBeginIdx = morphInfoVo.getOrgBegin();
            getTargetText = tmpMorphStr.toCharArray();

            if (getTargetText.length + 1 - nCountOpt <= 0) {
                morphInfoVoList.add(new MorphInfoVo(tmpBeginIdx, idx, tmpMorphStr));
                continue;
            }

            for (int pos = 0; pos < getTargetText.length + 1 - nCountOpt; pos++) {
                morphInfoVoList.add(new MorphInfoVo(pos + tmpBeginIdx, idx, tmpMorphStr.substring(pos, pos + nCountOpt)));
                idx++;
            }

        }

        return morphInfoVoList;
    }
}

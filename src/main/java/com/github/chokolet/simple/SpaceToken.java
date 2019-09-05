package com.github.chokolet.simple;

import com.github.chokolet.morph.MorphInterface;
import com.github.chokolet.morph.vo.MorphInfoVo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DataSolution on 2019-01-30.
 * Space Tokenizer using by regex
 */
public class SpaceToken implements MorphInterface {

    @Override
    public List<MorphInfoVo> geToken(String targetText) {

        List<MorphInfoVo> morphInfoVoList = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\S+").matcher(targetText);
        int idx = 0;

        while (matcher.find()) {
            morphInfoVoList.add(new MorphInfoVo(matcher.start(), idx, matcher.group()));
            idx++;
        }
        return morphInfoVoList;
    }

    @Override
    public List<MorphInfoVo> getLemma(String targetText) {
        return geToken(targetText);
    }

}

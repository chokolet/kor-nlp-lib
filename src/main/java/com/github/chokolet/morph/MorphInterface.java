package com.github.chokolet.morph;

import com.github.chokolet.morph.vo.MorphInfoVo;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by CK on 2019-04-22.
 */
public interface MorphInterface {

    // 토크닝 추출
    List<MorphInfoVo> getToken(String targetText);

    // 원형 추출
    List<MorphInfoVo> getLemma(String targetText);

}

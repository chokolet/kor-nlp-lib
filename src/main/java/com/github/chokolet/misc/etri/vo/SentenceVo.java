package com.github.chokolet.misc.etri.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CK on 2019-07-18.
 */
@Data
@EqualsAndHashCode
public class SentenceVo {

    int id;
    String reserve_str;
    String text;

    // 형태소 단위
    List<MorphVo> morp = new ArrayList<>();

    // 어절 단위
    List<MorphEvalVo> morp_eval = new ArrayList<>();

    // 어절 단위
    List<WordVo> word = new ArrayList<>();

    List<Object> NE = new ArrayList<>();
    List<Object> chunk = new ArrayList<>();
    List<Object> dependency = new ArrayList<>();
    List<Object> SRL = new ArrayList<>();
    List<Object> relation = new ArrayList<>();
    List<Object> SA = new ArrayList<>();

}

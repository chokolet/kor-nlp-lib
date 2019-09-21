package com.github.chokolet.misc.etri.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by CK on 2019-07-18.
 */
@Data
@EqualsAndHashCode
public class MorphEvalVo {
    int id;
    String result = "";
    String target = "";
    int word_id;
    int m_begin;
    int m_end;
}

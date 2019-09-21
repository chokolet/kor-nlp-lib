package com.github.chokolet.misc.etri.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by CK on 2019-07-18.
 */
@Data
@EqualsAndHashCode
public class WordVo {

    int id;
    String text;
    String type;
    int begin;
    int end;

}

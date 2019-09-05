package com.github.chokolet.morph.etri.vo;

import com.github.chokolet.morph.etri.util.TagUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by CK on 2019-07-18.
 */
@Data
@EqualsAndHashCode
public class MorphVo {

    int id;
    String lemma;
    String type;
    int position;
    double weight;

    public MorphVo(int id, String lemma, String type, int position, double weight) {
        this.id = id;
        this.lemma = lemma;
        this.type = TagUtils.convertEtriPosTag(lemma, type);
        this.position = position;
        this.weight = weight;
    }
}

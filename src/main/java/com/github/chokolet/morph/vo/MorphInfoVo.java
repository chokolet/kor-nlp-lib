package com.github.chokolet.morph.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by DataSolution on 2018-04-02.
 */
@Data
@EqualsAndHashCode
public class MorphInfoVo {

    int orgBegin; // 시작점
    int tokIdx; // 토큰 idx
    String keyword; // 형태소
    String morphType; // 태그

    public MorphInfoVo(int getOrgBegin, int getTokIdx, String getText) {
        this.orgBegin = getOrgBegin;
        this.tokIdx = getTokIdx;
        this.keyword = getText;
        this.morphType = "UNK";
    }

    public MorphInfoVo(int getOrgBegin, int getTokIdx, String getText, String getTag) {
        this(getOrgBegin, getTokIdx, getText);
        this.morphType = getTag;
    }

}

package com.github.chokolet.morph;

import com.github.chokolet.morph.nori.Nori;
import com.github.chokolet.morph.seunjeon.Seunjeon;
import com.github.chokolet.simple.BigramToken;
import com.github.chokolet.simple.SpaceToken;
import com.github.chokolet.simple.UnigramToken;
import com.github.chokolet.util.InvalidFormatException;
import com.github.chokolet.util.ReflectionUtils;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by CK on 2019-04-22.
 * 형태소 분석기 공장
 */
@Slf4j
public class MorphFactory {

    private final Map<MorphEnum, MorphInterface> reuseFactoryMorph = new ConcurrentHashMap<>();

    private static class MorphFactoryHolder {
        public static final MorphFactory morphFactory = new MorphFactory();
    }

    public static MorphFactory getInstance() {
        return MorphFactoryHolder.morphFactory;
    }

    public enum DefaultMorph implements MorphEnum {

        SEUNJEON(Seunjeon.class.getName()),
        NORI(Nori.class.getName()),
        SPACETOKEN(SpaceToken.class.getName()),
        UNIGRAM(UnigramToken.class.getName()),
        BIGRAM(BigramToken.class.getName());

        String value;

        @Override
        public String getValue() {
            return value;
        }

        DefaultMorph(String getValue) {
            value = getValue;
        }
    }

    public MorphInterface reuseFactoryMorph(String morphNLP) throws InvalidFormatException {
        MorphEnum morphEnum = (MorphEnum) ReflectionUtils.getEnumMatchedKey(morphNLP, DefaultMorph.class);

        return this.reuseFactoryMorph(morphEnum);
    }

    @Synchronized
    public MorphInterface reuseFactoryMorph(MorphEnum targetNlp) {

        MorphInterface nlpMart = null;

        if (reuseFactoryMorph.containsKey(targetNlp)) {
            return reuseFactoryMorph.get(targetNlp);
        }
        try {
            nlpMart = (MorphInterface) Class.forName(targetNlp.getValue()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nlpMart == null) {
            throw new NullPointerException("Initialize Fail [" + targetNlp.getValue() + "]");
        }

        reuseFactoryMorph.put(targetNlp, nlpMart);

        return nlpMart;

    }
}


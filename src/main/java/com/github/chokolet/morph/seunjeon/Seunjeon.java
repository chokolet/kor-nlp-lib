package com.github.chokolet.morph.seunjeon;


import com.github.chokolet.morph.MorphInterface;
import com.github.chokolet.morph.etri.vo.MorphVo;
import com.github.chokolet.morph.vo.MorphInfoVo;
import org.apache.commons.lang3.ObjectUtils;
import org.bitbucket.eunjeon.seunjeon.Analyzer;
import org.bitbucket.eunjeon.seunjeon.Eojeol;
import org.bitbucket.eunjeon.seunjeon.LNode;
import org.bitbucket.eunjeon.seunjeon.Morpheme;
import scala.collection.JavaConverters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by DataSolution on 2018-04-01.
 * Seunjeon morph analyzer parse
 */
public class Seunjeon implements MorphInterface {

    @Override
    public List<MorphInfoVo> getToken(String targetText) {
        List<MorphInfoVo> morphToken = new ArrayList<>();
        Iterable<LNode> getNode = Analyzer.parseJava(targetText);
        int i = 0;
        for (LNode node : getNode) {
            morphToken.add(new MorphInfoVo(node.beginOffset(), i, node.morpheme().getSurface(), node.morpheme().getFeatureHead()));
            i++;
        }
        return morphToken;
    }


    @Override
    public List<MorphInfoVo> getLemma(String targetText) {
        List<MorphInfoVo> morphToken = new ArrayList<>();

        Iterable<Eojeol> getNode = Analyzer.parseEojeolJava(targetText);

        AtomicInteger incDocId = new AtomicInteger(0);
        MorphVo morphVo;
        for (Eojeol eojeol : getNode) {

            for (LNode lNode : eojeol.nodesJava()) {

                for (LNode deInf : lNode.deCompoundJava()) {

                    if (deInf.morpheme().deComposite().size() == 0) {
                        morphVo = getMorphVo(incDocId, deInf.morpheme(), lNode);
                        morphToken.add(new MorphInfoVo(morphVo.getPosition(), incDocId.get(), morphVo.getLemma(), morphVo.getType()));
                    } else {
                        List<Morpheme> morphemeList = JavaConverters.seqAsJavaList(deInf.morpheme().deComposite());
                        for (Morpheme morpheme : morphemeList) {
                            morphVo = getMorphVo(incDocId, morpheme, lNode);
                            morphToken.add(new MorphInfoVo(morphVo.getPosition(), incDocId.get(), morphVo.getLemma(), morphVo.getType()));
                        }
                    }
                }
            }
        }

        return morphToken;

    }

    public MorphVo getMorphVo(AtomicInteger incDocId, Morpheme morpheme, LNode deInf) {
        return new MorphVo(incDocId.getAndIncrement(), morpheme.getSurface(), morpheme.getFeatureHead(), deInf.beginOffset(), morpheme.getCost());
    }

}

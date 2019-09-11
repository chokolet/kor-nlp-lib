package com.github.chokolet;

import com.github.chokolet.misc.Jaso;
import com.github.chokolet.morph.MorphFactory;
import com.github.chokolet.morph.etri.SeunjeonToEtri;
import com.github.chokolet.morph.etri.vo.SentenceVo;
import com.github.chokolet.sentence.SegmentSentence;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by CK on 2019-04-22.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MorphTest.class)
public class MorphTest {

    static final String targetText = "아버지가 방에 들어가신다.";
    String tmpText = "강남역 맛집으로 소문난 강남 토끼정에 다녀왔습니다. 회사 동료 분들과 다녀왔는데 분위기도 좋고 음식도 맛있었어요 다만, 강남 토끼정이 강남 쉑쉑버거 골목길로 쭉 올라가야 하는데 다들 쉑쉑버거의 유혹에 넘어갈 뻔 했답니다 강남역 맛집 토끼정의 외부 모습. 강남 토끼정은 4층 건물 독채로 이루어져 있습니다.";
    static Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void test() {
        MorphFactory.getInstance();
    }

    @Test
    public void testOneMorph() {
        List get = MorphFactory.getInstance().reuseFactoryMorph(MorphFactory.DefaultMorph.BIGRAM).getToken(targetText);
        log.info("BIGRAM : [{}]", gsonBuilder.toJson(get));
        get = MorphFactory.getInstance().reuseFactoryMorph(MorphFactory.DefaultMorph.UNIGRAM).getToken(targetText);
        log.info("unigram : [{}]", gsonBuilder.toJson(get));
        Assert.assertNotNull(get);
    }

    @Test
    public void testSeunjeonMorph() {
        List get = MorphFactory.getInstance().reuseFactoryMorph(MorphFactory.DefaultMorph.SEUNJEON).getToken(targetText);
        get.stream().forEach(System.out::println);

        List get2 = MorphFactory.getInstance().reuseFactoryMorph(MorphFactory.DefaultMorph.SEUNJEON).getLemma(targetText);
        get2.stream().forEach(System.out::println);
        Assert.assertNotNull(get);
    }

    @Test
    public void testNoriMorph() {
        List get = MorphFactory.getInstance().reuseFactoryMorph(MorphFactory.DefaultMorph.NORI).getLemma(targetText);
        get.stream().forEach(System.out::println);
        get = MorphFactory.getInstance().reuseFactoryMorph(MorphFactory.DefaultMorph.NORI).getToken(targetText);
        get.stream().forEach(System.out::println);
        Assert.assertNotNull(get);
    }


    @Test
    public void choJungTest() {
        String tmp = new Jaso().mergeData("하", "ㄴ");
        System.out.println(tmp);
        Assert.assertNotNull(tmp);
    }

    @Test
    public void threadTest() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                List get = null;
                try {
                    get = MorphFactory.getInstance().reuseFactoryMorph(MorphFactory.DefaultMorph.NORI).getToken(targetText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("seungeon morph count is : [{}]", get.size());
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void splitTest() {
        List<String> tmp = SegmentSentence.getSentence(tmpText);
        System.out.println(gsonBuilder.toJson(tmp));
    }

    @Test
    public void seEtriTest() {
        SeunjeonToEtri seunjeonToEtri = new SeunjeonToEtri();
        SentenceVo tmpData = seunjeonToEtri.getSentenceVo(0, targetText);
        System.out.println(gsonBuilder.toJson(tmpData));
    }


}

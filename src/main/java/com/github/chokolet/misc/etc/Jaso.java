package com.github.chokolet.misc.etc;

import java.util.HashMap;

/**
 * Created by CK on 2019-04-22.`
 */
public class Jaso {
    /**
     * The constant ChoSung.
     */
    static final char[] ChoSung = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
    /**
     * The constant JungSung.
     */
    static final char[] JungSung = {'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'};
    /**
     * The constant JongSung.
     */
    static final char[] JongSung = {'\000', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    /**
     * The constant chosung2Index.
     */
    HashMap<Character, Character> chosungToIndex = createMap(ChoSung);
    /**
     * The constant jungsung2Index.
     */
    HashMap<Character, Character> jungsungToIndex = createMap(JungSung);
    /**
     * The constant jongsung2Index.
     */
    HashMap<Character, Character> jongsungToIndex = createMap(JongSung);

    /***
     * Input이 완성형 인 경우 바보가 됨.. / 수정해봐야 할듯
     * @param choJun
     * @param jong
     * @return
     */
    public String mergeData(String choJun, String jong) {

        char uniVal = getUniVal(choJun);

        char cho = (char) (((uniVal - (uniVal % 28)) / 28) / 21);
        char jun = (char) (((uniVal - (uniVal % 28)) / 28) % 21);
        char jon = jongsungToIndex.get(jong.toCharArray()[0]);
        char temp = (char) (0xAC00 + 28 * 21 * (cho) + 28 * (jun) + (jon));
        return String.valueOf(temp);
    }

    private HashMap<Character, Character> createMap(char[] charArr) {
        HashMap<Character, Character> temp = new HashMap();

        char i = '\000';
        for (char c : charArr) {
            temp.put(Character.valueOf(c), Character.valueOf(i));
            i = (char) (i + '\001');
        }
        return temp;
    }


    private char getUniVal(String choJun) {
        char test = choJun.charAt(0);
        return (char) (test - 0xAC00);
    }

}

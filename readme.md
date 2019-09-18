# 형태소 분석기들

다양한 형태소 분석기를 활용할 수 있도록 패키징

## Getting Started

- `MorphFactory` class 에서  `getNLPApi`  및 `getToken, getLemma` 메서드를 사용하여 특정 Text에 대해 형태소 분석 한 결과를 추출
- nori analyzer , seunjeon analyzer, Ngram, SpaceToken , sentence
### Prerequisites / usage

- 형태소 분석기

  | morph    | based                                          | opensource                                                   | comment                                                      |
  | -------- | ---------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | Nori     | Kuromoji based ( mecab based ) / mecab ko dict | [오픈소스](https://github.com/apache/lucene-solr/tree/master/lucene/analysis/nori) | [Nori Slide Share](https://www.elastic.co/kr/blog/nori-the-official-elasticsearch-plugin-for-korean-language-analysis) / [기술블로그](https://www.elastic.co/kr/blog/nori-the-official-elasticsearch-plugin-for-korean-language-analysis) |
  | Seunjeon | mecab based / mecab ko dict                    | [오픈소스](https://bitbucket.org/eunjeon/seunjeon/src/master/) | [기술블로그](http://eunjeon.blogspot.com/)                   |
  
- Seunjeon to WiseNLU ( ETRI NLU ) format

  - Seunjeon 을 활용한 etri nlp api 의 morph, morph_eval, word 단 파서

    ```java
    SeunjeonToEtri seunjeonToEtri = new SeunjeonToEtri();
    SentenceVo tmpData = seunjeonToEtri.getSentenceVo(0, targetText);
    System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(tmpData));
    ```


- 음절 분리기

  | 종류       | 설명          | 예제 ( 데이타 솔루션 )      |
  | ---------- | ------------- | --------------------------- |
  | Unigram    | 1음절 분리    | 데 / 이 / 타 / 솔 / 루 / 션 |
  | Bigram     | 2음절 분리    | 데이 / 타 / 솔루 / 션   |
  | SpaceToken | 띄어쓰기 분리 | 데이타 / 솔루션             |

- 분석기 종류 선택은 `MorphFactory` 내에 있는 `Morph` enum 으로 지정

- 분석기 초기화는 최초 호출 시 Initialize , 이후로는 재사용

- java 소스 내 library 형태로 사용 ( test 참조 )

- 문장 분리기


  - java 기본 segment sentence

  - [문장 분리기 비교글](http://semantics.kr/%ED%95%9C%EA%B5%AD%EC%96%B4-%ED%98%95%ED%83%9C%EC%86%8C-%EB%B6%84%EC%84%9D%EA%B8%B0-%EB%B3%84-%EB%AC%B8%EC%9E%A5-%EB%B6%84%EB%A6%AC-%EC%84%B1%EB%8A%A5%EB%B9%84%EA%B5%90/) 보고 반영해봄

    ```java
    List<String> tmp = JavaDefaultSentence.getSentence(tmpText);
    System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(tmp));
    ```

## Built With

- Maven 
- Java 1.8 이상
- Lombok

## Authors

- chickin7@gmail.com

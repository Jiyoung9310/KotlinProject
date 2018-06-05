package com.example.hello.crosstest;

/**
 * Created by XNOTE on 2018-06-05.
 */
//레트로핏 객체, 통신 도메인 설정
public class Net {
    private static final Net ourInstance = new Net();

    public static Net getInstance() {
        return ourInstance;
    }

    private Net() {
    }
}

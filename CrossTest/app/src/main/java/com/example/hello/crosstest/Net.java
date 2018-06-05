package com.example.hello.crosstest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    KakaoImp kakaoImp;
    public KakaoImp getKakaoImp() {
        if(kakaoImp == null) {
            kakaoImp = retrofit.create(KakaoImp.class);
        }
        return kakaoImp;
    }
}

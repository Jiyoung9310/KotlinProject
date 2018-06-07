package com.example.edu.crosstest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//싱글톤 패턴
//레트로핏 객체, 통신 도메인 설정
object Net {
    ////////////////////////////////////////////////////////////////
    // retrofit 통신 모듈
    internal var retrofit = Retrofit.Builder()
            // 기본 통신 도메인
            .baseUrl("https://dapi.kakao.com")
            // 통신 결과를 json으로 파싱하여 처리
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    // api 사용을 할 수 있게 인터페이스 객체를 생성
    internal val kakaoImp: KakaoImp = retrofit.create<KakaoImp>(KakaoImp::class.java!!)

}

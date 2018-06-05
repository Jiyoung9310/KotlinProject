package com.example.hello.crosstest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by XNOTE on 2018-06-05.
 */
//통신 API 정의
public interface KakaoImp {
    //get, header 내용 세팅, 검색어 query
    @Headers("Authorization: KakaoAK 2ddcb725149de71a27a9f8601ab16778")
    @GET("v2/search/image")
    Call<Model> imgSearch(@Query("query") String query,
                          @Query("sort") String sort,
                          @Query("page") int page,
                          @Query("size") int size);
}

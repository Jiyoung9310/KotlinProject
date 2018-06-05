package com.example.hello.crosstest;

import java.util.ArrayList;

/**
 * Created by XNOTE on 2018-06-05.
 */
//통신 결과를 받는 클래스 : 데이터를 담는 그릇
public class Model {
    Meta meta;
    class Meta{
        boolean is_end;
        int total_count; //:34281
        int pageable_count; //:3893

        public boolean isIs_end() {
            return is_end;
        }

        public void setIs_end(boolean is_end) {
            this.is_end = is_end;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getPageable_count() {
            return pageable_count;
        }

        public void setPageable_count(int pageable_count) {
            this.pageable_count = pageable_count;
        }
    }

    ArrayList<Document> documents;
    class Document {
        String collection;// "blog",
        String datetime;// "2014-10-29T22:15:00.000+09:00",
        int height;//:339,
        int width;// 510,
        String thumbnail_url;// "https://search3.kakaocdn.net/argon/130x130_85_c/Ca9K0s3wDWo",
        String image_url;// "http://postfiles9.naver.net/20141029_280/wenzelcmm_1414587860776QVNIy_JPEG/%B7%AF%BD%C3%BE%C6%BF%F9%B5%E5%C4%C5%BF%A5%BA%ED%B7%B3.jpg?type=w1",
        String display_sitename; //"네이버블로그",
        String doc_url;// "http://wenzelcmm.blog.me/220165746879"

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getThumbnail_url() {
            return thumbnail_url;
        }

        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getDisplay_sitename() {
            return display_sitename;
        }

        public void setDisplay_sitename(String display_sitename) {
            this.display_sitename = display_sitename;
        }

        public String getDoc_url() {
            return doc_url;
        }

        public void setDoc_url(String doc_url) {
            this.doc_url = doc_url;
        }
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }
}

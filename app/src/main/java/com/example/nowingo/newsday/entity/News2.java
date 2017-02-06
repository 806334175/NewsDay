package com.example.nowingo.newsday.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NowINGo on 2016/12/29.
 */
public class News2 implements Serializable{

    private ResultEntity result;
    private String reason;
    private int error_code;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ResultEntity getResult() {
        return result;
    }

    public String getReason() {
        return reason;
    }

    public int getError_code() {
        return error_code;
    }

    public class ResultEntity implements Serializable{

        private String stat;
        private List<DataEntity> data;

        public void setStat(String stat) {
            this.stat = stat;
        }

        public void setData(List<DataEntity> data) {
            this.data = data;
        }

        public String getStat() {
            return stat;
        }

        public List<DataEntity> getData() {
            return data;
        }

        public class DataEntity implements Serializable {

            private String date;
            private String author_name;
            private String thumbnail_pic_s;
            private String thumbnail_pic_s03;
            private String title;
            private String category;
            private String url;

            public void setDate(String date) {
                this.date = date;
            }

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public void setThumbnail_pic_s(String thumbnail_pic_s) {
                this.thumbnail_pic_s = thumbnail_pic_s;
            }

            public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
                this.thumbnail_pic_s03 = thumbnail_pic_s03;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getDate() {
                return date;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public String getThumbnail_pic_s() {
                return thumbnail_pic_s;
            }

            public String getThumbnail_pic_s03() {
                return thumbnail_pic_s03;
            }

            public String getTitle() {
                return title;
            }

            public String getCategory() {
                return category;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}

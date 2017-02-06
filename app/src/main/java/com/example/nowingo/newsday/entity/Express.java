package com.example.nowingo.newsday.entity;

import java.util.List;

/**
 * Created by NowINGo on 2017/1/11.
 */
public class Express {

    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"mailNo":"610349360550","ret_code":0,"flag":true,"status":4,"tel":"95338","expSpellName":"shunfeng","data":[{"time":"2016-10-13 08:15:22","context":"【南京浦口丽景路营业点】正在派送途中,请您准备签收(派件人:王德彬,18261946720)"},{"time":"2016-10-12 07:32:32","context":"【南京浦口丽景路营业点】已与收方客户约定新派送时间 201610131631,待派送"},{"time":"2016-10-11 11:57:25","context":"【南京浦口丽景路营业点】快件派送不成功(已与收方客户约定新派送时间 2016-10-15 11:55:00),待再次派送"},{"time":"2016-10-11 08:17:30","context":"【南京浦口丽景路营业点】正在派送途中,请您准备签收(派件人:王德彬,18261946720)"},{"time":"2016-10-11 07:34:34","context":"【南京浦口丽景路营业点】快件到达 【南京浦口丽景路营业点】"},{"time":"2016-10-11 02:35:26","context":"【南京江宁集散中心】快件在【南京江宁集散中心】已装车，准备发往 【南京浦口丽景路营业点】"},{"time":"2016-10-11 01:12:16","context":"【南京江宁集散中心】快件到达 【南京江宁集散中心】"},{"time":"2016-10-09 20:39:49","context":"【深圳集散中心】快件到达 【深圳集散中心】"},{"time":"2016-10-09 20:39:49","context":"【深圳集散中心】快件在【深圳集散中心】已装车，准备发往 【南京江宁集散中心】"},{"time":"2016-10-09 17:10:28","context":"【深圳龙岗龙东社区营业部】快件在【深圳龙岗龙东社区营业部】已装车，准备发往 【深圳集散中心】"},{"time":"2016-10-09 15:42:09","context":"【深圳龙岗龙东社区营业部】顺丰速运 已收取快件"}],"expTextName":"顺丰速运"}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * mailNo : 610349360550
         * ret_code : 0
         * flag : true
         * status : 4
         * tel : 95338
         * expSpellName : shunfeng
         * data : [{"time":"2016-10-13 08:15:22","context":"【南京浦口丽景路营业点】正在派送途中,请您准备签收(派件人:王德彬,18261946720)"},{"time":"2016-10-12 07:32:32","context":"【南京浦口丽景路营业点】已与收方客户约定新派送时间 201610131631,待派送"},{"time":"2016-10-11 11:57:25","context":"【南京浦口丽景路营业点】快件派送不成功(已与收方客户约定新派送时间 2016-10-15 11:55:00),待再次派送"},{"time":"2016-10-11 08:17:30","context":"【南京浦口丽景路营业点】正在派送途中,请您准备签收(派件人:王德彬,18261946720)"},{"time":"2016-10-11 07:34:34","context":"【南京浦口丽景路营业点】快件到达 【南京浦口丽景路营业点】"},{"time":"2016-10-11 02:35:26","context":"【南京江宁集散中心】快件在【南京江宁集散中心】已装车，准备发往 【南京浦口丽景路营业点】"},{"time":"2016-10-11 01:12:16","context":"【南京江宁集散中心】快件到达 【南京江宁集散中心】"},{"time":"2016-10-09 20:39:49","context":"【深圳集散中心】快件到达 【深圳集散中心】"},{"time":"2016-10-09 20:39:49","context":"【深圳集散中心】快件在【深圳集散中心】已装车，准备发往 【南京江宁集散中心】"},{"time":"2016-10-09 17:10:28","context":"【深圳龙岗龙东社区营业部】快件在【深圳龙岗龙东社区营业部】已装车，准备发往 【深圳集散中心】"},{"time":"2016-10-09 15:42:09","context":"【深圳龙岗龙东社区营业部】顺丰速运 已收取快件"}]
         * expTextName : 顺丰速运
         */

        private String mailNo;
        private int ret_code;
        private boolean flag;
        private int status;
        private String tel;
        private String expSpellName;
        private String expTextName;
        private List<DataBean> data;

        public String getMailNo() {
            return mailNo;
        }

        public void setMailNo(String mailNo) {
            this.mailNo = mailNo;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getExpSpellName() {
            return expSpellName;
        }

        public void setExpSpellName(String expSpellName) {
            this.expSpellName = expSpellName;
        }

        public String getExpTextName() {
            return expTextName;
        }

        public void setExpTextName(String expTextName) {
            this.expTextName = expTextName;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * time : 2016-10-13 08:15:22
             * context : 【南京浦口丽景路营业点】正在派送途中,请您准备签收(派件人:王德彬,18261946720)
             */

            private String time;
            private String context;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }
        }
    }
}

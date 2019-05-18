package com.wml.model;

import java.util.List;

public class HzSecondInfo {

    /**
     * birthday : 1990-01-01
     * loanAdCodeSecond :
     * titleReqList : [{"optionKey":["loan_amount_20000-50000"],"titleKey":"loan_amount"},{"optionKey":["loan_time_limit_6"],"titleKey":"loan_time_limit"},{"optionKey":["local_census_register"],"titleKey":"family_register_type"},{"optionKey":["civil_servant"],"titleKey":"job_type"},{"optionKey":["monthly_income_3000-6000"],"titleKey":"monthly_income"},{"optionKey":["credit_card_yes"],"titleKey":"credit_card"},{"optionKey":["is_social_security_yes"],"titleKey":"is_social_security"},{"optionKey":["is_accumulation_fund_no"],"titleKey":"is_accumulation_fund"},{"optionKey":["own_house_no"],"titleKey":"own_house"},{"optionKey":["house_loan_no"],"titleKey":"house_loan"},{"optionKey":["own_car_yes"],"titleKey":"own_car"},{"optionKey":["car_loan_no"],"titleKey":"car_loan"},{"optionKey":["guarantee_slip_no"],"titleKey":"guarantee_slip"},{"optionKey":["Weilidai_no"],"titleKey":"Weilidai"}]
     * loanCityNameSecond : 上海市
     * loanUserCode : 20190315143619070128320867951
     */
    private String birthday;
    private String loanAdCodeSecond;
    private List<TitleReqListEntity> titleReqList;
    private String loanCityNameSecond;
    private String loanUserCode;

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setLoanAdCodeSecond(String loanAdCodeSecond) {
        this.loanAdCodeSecond = loanAdCodeSecond;
    }

    public void setTitleReqList(List<TitleReqListEntity> titleReqList) {
        this.titleReqList = titleReqList;
    }

    public void setLoanCityNameSecond(String loanCityNameSecond) {
        this.loanCityNameSecond = loanCityNameSecond;
    }

    public void setLoanUserCode(String loanUserCode) {
        this.loanUserCode = loanUserCode;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLoanAdCodeSecond() {
        return loanAdCodeSecond;
    }

    public List<TitleReqListEntity> getTitleReqList() {
        return titleReqList;
    }

    public String getLoanCityNameSecond() {
        return loanCityNameSecond;
    }

    public String getLoanUserCode() {
        return loanUserCode;
    }

    public static class TitleReqListEntity {
        /**
         * optionKey : ["loan_amount_20000-50000"]
         * titleKey : loan_amount
         */
        private List<String> optionKey;
        private String titleKey;

        public void setOptionKey(List<String> optionKey) {
            this.optionKey = optionKey;
        }

        public void setTitleKey(String titleKey) {
            this.titleKey = titleKey;
        }

        public List<String> getOptionKey() {
            return optionKey;
        }

        public String getTitleKey() {
            return titleKey;
        }
    }
}

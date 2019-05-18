package com.wml.config;

import java.util.Arrays;
import java.util.List;

public class UserInfo {

    //  贷款额度
    public static final List<String> loan_amount = Arrays.asList("loan_amount_1000-5000",
            "loan_amount_5000-10000","loan_amount_10000-20000",
            "loan_amount_20000-50000","loan_amount_50000-100000",
            "loan_amount_100000-200000","loan_amount_200000+");

    // 贷款期限
    public static final List<String> loanTime = Arrays.asList("loan_time_limit_1m",
            "loan_time_limit_3","loan_time_limit_6",
            "loan_time_limit_12","loan_time_limit_24","loan_time_limit_36");

    // 户籍地址
    public static final List<String> family_register_type = Arrays.asList("local_census_register",
            "not_local_census_register");

    // 职业类型
    public static final List<String> employer = Arrays.asList("civil_servant","employees","employer",
            "merchant","soho");

    // 工资发放
    public static final List<String> salary = Arrays.asList("clock_in","transfer_accounts","ready_money");

    // 月收入
    public static final List<String> imcome = Arrays.asList("monthly_income_<3000",
            "monthly_income_3000-6000","monthly_income_6000-10000",
            "monthly_income_10000-20000","monthly_income_20000+");

    // 有无信用卡
    public static final List<String> credit_card = Arrays.asList("credit_card_yes","credit_card_no");

    // 有无社保
    public static final List<String> is_social_security = Arrays.asList("is_social_security_yes",
            "is_social_security_no");

    // 有无公积金
    public static final List<String> is_accumulation_fund = Arrays.asList("is_accumulation_fund_yes",
            "is_accumulation_fund_no");

    // 有无房
    public static final List<String> own_house = Arrays.asList("own_house_yes","own_house_no");

    // 有无房贷
    public static final List<String> house_loan = Arrays.asList("house_loan_yes","house_loan_no");

    // 有无车
    public static final List<String> own_car = Arrays.asList("own_car_yes","own_car_no");

    // 有无车贷
    public static final List<String> car_loan = Arrays.asList("car_loan_yes","car_loan_no");

    // 有无寿险保单
    public static final List<String> guarantee_slip = Arrays.asList("guarantee_slip_yes",
            "guarantee_slip_no");

    // 有无微粒贷
    public static final List<String> weilidai = Arrays.asList("Weilidai_yes","Weilidai_no");
}

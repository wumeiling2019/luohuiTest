package com.wml.config;

public class CacheKeyConstants {

    /**  验证码华赞用户跟信贷员共用 会在存放redis的时候会根据请求接口区分   */
    /** 注册验证码code */
    public static final String LOAN_REGISTER_CODE = "loan:captcha:register.";
    /** 登陆验证码code */
    public static final String LOAN_LOGIN_CODE = "loan:captcha:login.";
    /** 找回密码验证码code */
    public static final String LOAN_RESET_PASSWORD_CODE = "loan:captcha:reset.password.";
    /** 绑定手机验证码code */
    public static final String LOAN_BIND_MOBILE_CODE = "loan:captcha:bind.moblie.";
}

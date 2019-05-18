package com.wml.config;

public enum SmsCodeTypeEnum {


    /**抢单侠-注册-推广 */
    QIANGDANXIA_GENERALIZE_REG(10000, "推广-抢单侠-注册", "注册", "loan.officer.reg","3"),
    /** 信贷员(抢单侠) */
    QIANGDANXIA_REG(10001, "信贷员-注册", "注册", "loan.officer.reg","1"),
    QIANGDANXIA_LOGIN(10002, "信贷员-登陆", "登陆", "loan.officer.login","1"),
    QIANGDANXIA_BIND_MOBILE(10003, "信贷员-绑定手机号", "绑定手机号", "loan.officer.bindMobile","1"),
    QIANGDANXIA_RE_PWD(10004, "信贷员-重置密码", "重置密码", "loan.officer.rePwd","1"),

    /**注册推广-华赞  */
    HUAZAN_GENERALIZE_REG(20000, "推广-华赞-注册", "注册", "loan.user.reg","2"),
    /** 华赞 */
    HUAZAN_REG(20001, "华赞-注册", "注册", "loan.user.reg","0"),
    HUAZAN_LOGIN(20002, "华赞-登陆", "登陆","loan.user.login","0"),
    HUAZAN_BIND_MOBILE(20003, "华赞-绑定手机号", "绑定手机号", "loan.user.bindMobile","0"),
    HUAZAN_RE_PWD(20004, "华赞-重置密码", "重置密码", "loan.user.rePwd","0");

    private int code;

    private String name;

    private String aliasName;

    /**
     * 短信模板code
     */
    private String templateCode;

    /**
     * 业务code ${}
     */
    private String businessType;

    private SmsCodeTypeEnum(int code, String name, String aliasName, String templateCode,String businessType) {
        this.code = code;
        this.name = name;
        this.aliasName = aliasName;
        this.templateCode = templateCode;
        this.businessType = businessType;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public String getBusinessType() {
        return businessType;
    }
    public static String getName(int code) {
        for (SmsCodeTypeEnum string : SmsCodeTypeEnum.values()) {
            if (string.getCode() == code) {
                return string.name();
            }
        }
        return null;
    }

    public static boolean exits(int code) {
        for (SmsCodeTypeEnum string : SmsCodeTypeEnum.values()) {
            if (string.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    public static SmsCodeTypeEnum getEntity(int code) {
        for (SmsCodeTypeEnum e : SmsCodeTypeEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }


    /**
     * 由于switch 跟枚举一起的时候只能在枚举里面用 正好这个业务也属于这个枚举的
     *
     * @param smsCodeTypeEnum  类型
     * @return
     */
    public static String getRedisKey(SmsCodeTypeEnum smsCodeTypeEnum) {
        String key;
        switch (smsCodeTypeEnum) {
            // 1注册
            case QIANGDANXIA_REG:
                key = CacheKeyConstants.LOAN_REGISTER_CODE;
                break;
            case QIANGDANXIA_GENERALIZE_REG:
                key = CacheKeyConstants.LOAN_REGISTER_CODE;
                break;
            case HUAZAN_REG:
                key = CacheKeyConstants.LOAN_REGISTER_CODE;
                break;
            case HUAZAN_GENERALIZE_REG:
                key = CacheKeyConstants.LOAN_REGISTER_CODE;
                break;
            // 2登陆
            case QIANGDANXIA_LOGIN:
                key = CacheKeyConstants.LOAN_LOGIN_CODE;
                break;
            case HUAZAN_LOGIN:
                key = CacheKeyConstants.LOAN_LOGIN_CODE;
                break;

            // 3绑定手机号
            case QIANGDANXIA_BIND_MOBILE:
                key = CacheKeyConstants.LOAN_BIND_MOBILE_CODE;
                break;
            case HUAZAN_BIND_MOBILE:
                key = CacheKeyConstants.LOAN_BIND_MOBILE_CODE;
                break;

            // 4重置密码
            case QIANGDANXIA_RE_PWD:
                key = CacheKeyConstants.LOAN_RESET_PASSWORD_CODE;
                break;
            case HUAZAN_RE_PWD:
                key = CacheKeyConstants.LOAN_RESET_PASSWORD_CODE;
                break;
            default:
                key = CacheKeyConstants.LOAN_REGISTER_CODE;
                break;
        }
        return key + smsCodeTypeEnum.getBusinessType()+".";

    }
}

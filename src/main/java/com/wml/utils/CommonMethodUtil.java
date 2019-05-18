package com.wml.utils;

import com.wml.config.SmsCodeTypeEnum;
import redis.clients.jedis.Jedis;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 存放一些共用的方法
 */
public class CommonMethodUtil {

    // 手机号已以下数据为开头
    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    /**
     *  sign随机加密
     * @param str
     * @return
     */
    public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *   获取随机数
     * @param start
     * @param end
     * @return
     */
    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    /**
     *   获取随机手机号
     * @return String  随机的手机号
     */
    public static String getTelphone() {
        int index = getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }

    /**
     *  获取缓存中的验证码
     * @param telphone  手机号
     * @return
     */
    public static String getRedisCode(String telphone, SmsCodeTypeEnum smsCodeTypeEnum){
        Jedis jedis = new Jedis("106.14.135.107",6379);
        jedis.auth("Netwisdom20180320");
        // 切库（默认连接redis的d0库）
        jedis.select(1);
        String resultCode = jedis.get(SmsCodeTypeEnum.getRedisKey(smsCodeTypeEnum) + telphone);
        return resultCode;
    }

    /**
     * 设置等待
     * @param millis 以千为单位，为几秒
     */
    public static void sleep(long millis){
        try{
            Thread.currentThread().sleep(2000);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

}

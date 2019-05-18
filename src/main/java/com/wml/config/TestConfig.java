package com.wml.config;


import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestConfig {

    public static DefaultHttpClient defaultHttpClient;

    public static CookieStore store;

    // H5 加密秘钥
    public static String SALT_H5 = "*(&!*(Q#IUHAX89y19823h*&(YQ#($(*AGFsd";

    // App 加密秘钥
    public static String SALT_APP = "sjdo12379341jik890879&*#@$^&*Q*&";



}

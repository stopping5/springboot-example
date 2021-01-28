package com.example.demo.utils;

import java.nio.charset.Charset;

public class EctripOtaUtils {
    private static Charset UTF_8 = Charset.forName("UTF-8");
    /**
     * 私有构造方法
     */
    private EctripOtaUtils() {
    }

    public static final String SECURITY_MD5 = "MD5";
    /**
     * 验证签名
     * @param key key
     * @param data 数据
     * @param signed 请求过来的签名
     * @return
     */
    public static boolean checkSign(String key, String data, String signed) {
        if(null == key || null == data){
            return false;
        }
        String dataEncryption = EncryptUtil.MD5Hex(key + data).toUpperCase();//根据key和data获得签名
        return dataEncryption.equalsIgnoreCase(signed);//返回校验结果
    }

    /**
     * 新版本验证签名
     * @param signedKey
     * @param createUser 创建用户
     * @param createTime 创建时间
     * @param signed 请求过来的签名验证
     * */
    public static boolean checkSignV3(String signedKey, String createUser,String createTime, String signed) {
        if(null == signedKey || null == createUser || null == createTime){
            return false;
        }
        String dataEncryption = EncryptUtil.EncryptSHA256(signedKey, createUser,createTime).toUpperCase();//根据key和data获得签名
        return dataEncryption.equalsIgnoreCase(signed);//返回校验结果
    }
}

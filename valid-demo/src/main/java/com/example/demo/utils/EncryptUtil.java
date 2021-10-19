package com.example.demo.utils;


import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;


/**
 * 基础加密算法类?当前支持des,md5码?
 *
 * @author huhaopeng
 */
public class EncryptUtil {

    /**
     * MD5值计码?p>
     * MD5的算法在RFC1321 中定码?
     * 在RFC 1321中，给出了Test suite用来码?码码你的实现是否正确码?
     * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
     * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
     * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
     * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
     * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
     *
     * @param str 源字符串
     * @return md5码?
     */
    public final static byte[] md5(String str) {
        try {
            byte[] res = str.getBytes("UTF-8");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5".toUpperCase());
            mdTemp.update(res);
            byte[] hash = mdTemp.digest();
            return hash;
        } catch (Exception e) {
            return null;
        }
    }

    //hex repr. of md5
    public final static String MD5Hex(String input) {
        String s = null;
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字码?
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};
        try {
            MessageDigest md = MessageDigest
                    .getInstance("MD5");
            md.update(input.getBytes("utf-8"));
            byte tmp[] = md.digest();

            char str[] = new char[16 * 2];

            int k = 0;
            for (int i = 0; i < 16; i++) {

                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        } catch (Exception e) {
            /*ExceptionLog.print("error:", e);*/
        }
        return s;
    }

    // 加密后解码?
    public static String JM(byte[] inStr) {
        String newStr = new String(inStr);
        char[] a = newStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String k = new String(a);
        return k;
    }


    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String BASE64Encrypt(byte[] key) {
        String edata = null;
        try {
            edata = (new BASE64Encoder()).encodeBuffer(key).trim();
        } catch (Exception e) {
            /*ExceptionLog.print("error:", e);*/
        }
        return edata;
    }


    /**
     * BASE64解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] BASE64Decrypt(String data) {
        if (data == null) return null;
        byte[] edata = null;
        try {
            edata = (new BASE64Decoder()).decodeBuffer(data);
            return edata;
        } catch (Exception e) {
            /* ExceptionLog.print("error:", e);*/
        }
        return null;
    }

    /**
     * @param key 24位密码?
     * @param str 源字符串
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeySpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] des3Encrypt(String key, String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {

        byte[] newkey = key.getBytes();

        SecureRandom sr = new SecureRandom();

        DESedeKeySpec dks = new DESedeKeySpec(newkey);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");

        SecretKey securekey = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        byte[] bt = cipher.doFinal(str.getBytes("utf-8"));

        return bt;
    }

    public static String des3EncryptWithString(String key, String str) {
        try {
            return Base64.encodeBase64String(des3Encrypt(key, str));
        } catch (Exception e) {
            /*ExceptionLog.print("error:", e);*/
        }
        return null;
    }

    public static String des3DecryptWithString(byte[] edata, String key) {
        try {
            byte[] bytes = Base64.decodeBase64(edata);
            return des3Decrypt(bytes, key);
        } catch (Exception e) {
            /* ExceptionLog.print("error:", e);*/
        }
        return null;
    }


    /**
     * 解密
     *
     * @param edata
     * @param key
     * @return
     * @throws Exception
     */
    public static String des3Decrypt(byte[] edata, String key) {
        String data = "";
        try {
            if (edata != null) {
                byte[] newkey = key.getBytes();
                DESedeKeySpec dks = new DESedeKeySpec(newkey);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
                SecretKey securekey = keyFactory.generateSecret(dks);
                Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());
                byte[] bb = cipher.doFinal(edata);
                data = new String(bb, "UTF-8");
            }
        } catch (Exception e) {
            /* ExceptionLog.print("error", e);*/
        }
        return data;
    }

    public final static String taoBaoMD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes("GBK");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            /*ExceptionLog.print("error", e);*/
            return null;
        }
    }

    /**
     * 对字符串加密,加密算法使用SHA-256
     *
     * @param signedKey,createUser,createTime 要加密的字符串
     * @return
     */
    public static String EncryptSHA256(String signedKey, String createUser, String createTime) {
        MessageDigest md = null;
        String strDes = null;
        String[] strs = new String[]{signedKey, createUser, createTime};
        Arrays.sort(strs);
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strs) {
            stringBuilder.append(str);
        }
        byte[] bt = stringBuilder.toString().getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static String returnEcryptString(String ecryptId) {
        String data = null;
        try {
            data = new String(EncryptUtil.BASE64Decrypt(ecryptId.trim()), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}



package com.example.securityapidemo.advice;

import com.alibaba.fastjson.JSONObject;
import com.example.securityapidemo.controller.UserController;
import com.example.securityapidemo.model.RequestBodyVo;
import com.example.securityapidemo.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * 解密、验签数据预处理
 * */
@Component
@ControllerAdvice(assignableTypes = UserController.class)
@Setter
@Getter
@Slf4j
public class DecodeControllerAdvice implements RequestBodyAdvice {
    @Value("${security.apiKey}")
    private String KEY;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        return httpInputMessage;
    }
    /**
     * 数据预处理方法
     * */
    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        RequestBodyVo requestBodyVo = (RequestBodyVo) o;
        log.info("请求数据",requestBodyVo);
        String signed = requestBodyVo.getSigned();
        String date = (String) requestBodyVo.getData();
        //验签
        String key = KEY+date;
        String encodeKey = DigestUtils.md5DigestAsHex(key.getBytes());
        if (!signed.equals(encodeKey)){
            log.error("验证签名失败");
            return null;
        }
        //验证成功进行数据解密并且封装到data中
        byte[] bytes = Base64Utils.decodeFromString(date);
        String deCodeData = new String(bytes, Charset.forName("UTF-8"));
        log.info("解密后数据:",deCodeData);
        User user = JSONObject.parseObject(deCodeData, User.class);
        requestBodyVo.setData(user);
        return requestBodyVo;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}

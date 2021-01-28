package com.example.websocket.model;

import lombok.Data;

/**
 * @Description CommonResult
 * @Author stopping
 * @date: 2021/1/27 21:57
 */
@Data
public class CommonResult<T> {
        /**
         * 返回码
         * */
        private long code;

        /**
         * 返回信息
         * */
        private String message;

        /**
         * 返回数据
         * */
        private T data;

        public CommonResult(long code, String message, T data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        public CommonResult(long code, String message) {
            this.code = code;
            this.message = message;
        }

        /**
         * 操作成功:数据返回
         * */
        public static <T> CommonResult<T> success(T data,String message){
            return new CommonResult<>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage(),data);
        }
        /**
         * 操作成功:不带数据
         * */
        public static <T> CommonResult<T> success(String message){
            return new CommonResult<>(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMessage());
        }
        /**
         * 操作失败
         * */
        public static <T> CommonResult<T> failed(){
            return new CommonResult<>(ResultCode.FAILED.getCode(),ResultCode.FAILED.getMessage());
        }
        /**
         * 发生异常
         * */
        public static <T> CommonResult<T> error(){
            return new CommonResult<>(ResultCode.ERROR.getCode(),ResultCode.ERROR.getMessage());
        }
}

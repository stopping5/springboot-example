package com.stopping.swaggerdemo.ResponseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: stopping
 * @Date: 2020/10/22 09:22
 * 转载注明出处、个人博客网站:www.stopping.top
 */
@ApiModel(value = "消息格式返回实体类")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @ApiModelProperty(value = "访问是否成功标志")
    private boolean success;
    @ApiModelProperty(value = "返回数据")
    private Object data;
    @ApiModelProperty(value = "请求访问返回信息")
    private String message;
    @ApiModelProperty(value = "返回码")
    private String code;

    public Response success(Object object){
        Response response = new Response();
        response.setCode("200");
        response.setData(object);
        response.setMessage("请求成功");
        response.setSuccess(true);
        return  response;
    }

    public Response fail(){
        Response response = new Response();
        response.setCode("201");
        response.setMessage("失败成功");
        response.setSuccess(false);
        return  response;
    }
}

package com.example.demo.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author stopping
 * @since 2020-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Goods对象", description = "")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品图片")
    private String img;


}

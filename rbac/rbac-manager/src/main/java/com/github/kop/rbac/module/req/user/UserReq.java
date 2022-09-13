package com.github.kop.rbac.module.req.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户属性")
public class UserReq {

    @ApiModelProperty(value = "用户id")
    private Long userId;
}

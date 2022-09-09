package com.github.kop.rbac.module.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "admin绑定必填参数")
public class AdminBindReq  extends CompanyCreateUserReq{

    @ApiModelProperty(value = "用户id")
    private Long userId;
}

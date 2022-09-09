package com.github.kop.rbac.module.req.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "admin绑定用户与角色")
public class AdminBindRole extends AdminBindReq{

    @ApiModelProperty(value = "角色id")
    private Long roleId;
}

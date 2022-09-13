package com.github.kop.rbac.module.req.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "用户绑定与角色关系")
public class UserBindRoleReq extends UserReq {

    @ApiModelProperty(value = "角色id")
    private Long roleId;


}

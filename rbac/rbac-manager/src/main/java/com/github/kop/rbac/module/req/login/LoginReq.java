package com.github.kop.rbac.module.req.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginReq {

  @ApiModelProperty(value = "账号")
  private String username;

  @ApiModelProperty(value = "密码")
  private String password;
}

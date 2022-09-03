package com.github.kop.rbac.module.req.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyCreateUserReq extends CreateUserReq {

  @ApiModelProperty(value = "企业")
  private Long companyId;
}

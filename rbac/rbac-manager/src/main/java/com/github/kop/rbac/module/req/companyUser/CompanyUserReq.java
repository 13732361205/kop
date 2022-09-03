package com.github.kop.rbac.module.req.companyUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "企业用户关联所需参数")
public class CompanyUserReq {

  public CompanyUserReq(Long userId, Long companyId) {
    this.userId = userId;
    this.companyId = companyId;
  }

  /** 员工id */
  @ApiModelProperty(value = "员工id")
  private Long userId;

  /** 企业id */
  @ApiModelProperty(value = "企业id")
  private Long companyId;
}

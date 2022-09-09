package com.github.kop.rbac.module.req.company;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "企业创建请求参数")
public class CreateCompanyReq extends AdminCreateCompanyReq {

  @ApiModelProperty(value = "社会信用编码")
  private Long pid;
}

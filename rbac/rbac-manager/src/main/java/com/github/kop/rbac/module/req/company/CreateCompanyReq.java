package com.github.kop.rbac.module.req.company;

import com.github.kop.rbac.module.req.user.CreateUserReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "企业创建请求参数")
public class CreateCompanyReq   {

  @ApiModelProperty(value = "企业名称")
  private String name;

  @ApiModelProperty(value = "企业地址")
  private String address;

  @ApiModelProperty(value = "社会信用编码")
  private String socialCreditCode;

  /** 姓名 */
  @ApiModelProperty(value = "姓名")
  private String username;

  /** 联系方式 */
  @ApiModelProperty(value = "联系方式")
  private String phone;

  /** 性别 */
  @ApiModelProperty(value = "性别")
  private Integer grade;

  /** 密码 */
  @ApiModelProperty(value = "密码")
  private String password;
}

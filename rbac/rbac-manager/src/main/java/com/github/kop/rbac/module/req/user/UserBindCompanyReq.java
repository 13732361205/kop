package com.github.kop.rbac.module.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "用户绑定企业必填参数")
public class UserBindCompanyReq  extends UserReq{




    @ApiModelProperty(value = "企业")
    private Long companyId;
}

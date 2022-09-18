package com.github.kop.rbac.repo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.kop.rbac.module.entity.RbacCompany;
import com.github.kop.rbac.module.req.company.QueryCompanyReq;
import java.util.List;

public interface CompanyRepository {

  Long create(RbacCompany rbacCompany);

  int update(RbacCompany rbacCompany);

  RbacCompany byId(Long id);

  int deleteById(Long id);

  IPage<RbacCompany> page(Long page, Long size, QueryCompanyReq req);

  List<RbacCompany> list(QueryCompanyReq req);
}

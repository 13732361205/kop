package com.github.kop.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.kop.rbac.module.req.company.CreateCompanyReq;
import com.github.kop.rbac.module.req.company.QueryCompanyReq;
import com.github.kop.rbac.module.req.company.UpdateCompanyReq;
import com.github.kop.rbac.module.res.company.CompanyQueryRes;
import java.util.List;

public interface CompanyService {
  Long create(CreateCompanyReq req);

  int update(UpdateCompanyReq req);

  CompanyQueryRes byId(Long id);

  int deleteById(Long id);

  IPage<CompanyQueryRes> page(Long page, Long size, QueryCompanyReq req);

  List<CompanyQueryRes> list(QueryCompanyReq req);

}

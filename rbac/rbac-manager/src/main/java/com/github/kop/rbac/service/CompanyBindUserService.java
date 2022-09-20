package com.github.kop.rbac.service;


import com.github.kop.rbac.module.req.company.CreateCompanyReq;

public interface CompanyBindUserService {

    Long companyBindUser(CreateCompanyReq req);
    
    Long bindUser(Long userId,Long conpanyId);
}

package com.github.kop.rbac.service.impl;

import com.github.kop.rbac.module.entity.RbacCompanyUser;
import com.github.kop.rbac.module.req.company.CreateCompanyReq;
import com.github.kop.rbac.module.req.user.CompanyCreateUserReq;
import com.github.kop.rbac.repo.CompanyBindUserRepository;
import com.github.kop.rbac.repo.mapper.RbacCompanyUserMapper;
import com.github.kop.rbac.service.CompanyBindUserService;
import com.github.kop.rbac.service.CompanyService;
import com.github.kop.rbac.service.CompanyUserService;
import com.github.kop.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CompanyBindUserServiceImpl  implements CompanyBindUserService {
   @Autowired
   private CompanyBindUserRepository companyBindUserRepository;
   @Autowired
   private CompanyService companyService;



    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @Override
    public Long companyBindUser(CreateCompanyReq req) {
        Long companyId=companyService.create(req);
        CompanyCreateUserReq companyCreateUserReq=new CompanyCreateUserReq();
        companyCreateUserReq.setCompanyId(companyId);
        companyCreateUserReq.setName(req.getUsername());
        companyCreateUserReq.setGrade(req.getGrade());
        companyCreateUserReq.setPassword(req.getPassword());
        companyCreateUserReq.setPhone(req.getPhone());
        Long userId=userService.create(companyCreateUserReq);
        
        return bindUser(userId,companyId);
    }

    @Override
    public Long bindUser(Long userId, Long conpanyId) {
        RbacCompanyUser rbacCompanyUser=new RbacCompanyUser();
        rbacCompanyUser.setUserId(userId);
        rbacCompanyUser.setCompanyId(conpanyId);
        return companyBindUserRepository.createCompanyUser(rbacCompanyUser);
    }
}

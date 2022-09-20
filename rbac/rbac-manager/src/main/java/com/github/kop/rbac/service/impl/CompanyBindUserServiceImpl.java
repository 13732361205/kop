package com.github.kop.rbac.service.impl;

import com.github.kop.rbac.module.entity.RbacCompanyUser;
import com.github.kop.rbac.repo.mapper.RbacCompanyUserMapper;
import com.github.kop.rbac.service.CompanyBindUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyBindUserServiceImpl  implements CompanyBindUserService {
   @Autowired
   private RbacCompanyUserMapper rbacCompanyUserMapper;

    @Override
    public Long companyBindUser(Long userId, Long conpanyId) {
        RbacCompanyUser rbacCompanyUser=new RbacCompanyUser();
        rbacCompanyUser.setUserId(userId);
        rbacCompanyUser.setCompanyId(conpanyId);
         int insert = rbacCompanyUserMapper.insert(rbacCompanyUser);
         if(insert==1){
             return  rbacCompanyUser.getId();
         }
        return null;
    }
}

package com.github.kop.rbac.service.impl;


import com.github.kop.rbac.module.entity.RbacCompanyUser;
import com.github.kop.rbac.module.ex.ValidateException;
import com.github.kop.rbac.module.req.companyUser.CompanyUserReq;
import com.github.kop.rbac.module.req.user.CompanyCreateUserReq;
import com.github.kop.rbac.module.req.user.CreateUserReq;
import com.github.kop.rbac.repo.CompanyUserRepository;
import com.github.kop.rbac.service.CompanyUserService;
import com.github.kop.rbac.service.UserService;
import com.github.kop.rbac.utils.CreateValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyUserServiceImpl   extends UserServiceImpl implements CompanyUserService {

    private final CompanyUserCreateAndUpdateValidate companyUserCreateAndUpdateValidate =
            new CompanyUserCreateAndUpdateValidate();
    @Autowired
    private CompanyUserRepository companyUserRepository;


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long createCompanyUser(CompanyCreateUserReq req) {
        companyUserCreateAndUpdateValidate.createUserValidate(req);
        Long userId = create(req);
        if(req.getCompanyId()!=null){
            CompanyUserReq companyUserReq = new CompanyUserReq(userId, req.getCompanyId());
            companyUserCreateAndUpdateValidate.createValidate(companyUserReq);
            RbacCompanyUser rbacCompanyUser=new RbacCompanyUser();
            rbacCompanyUser.setUserId(userId);
            rbacCompanyUser.setCompanyId(req.getCompanyId());
            companyUserRepository.createCompanyUser(rbacCompanyUser);
        }
        return userId;
    }

    protected static final class CompanyUserCreateAndUpdateValidate extends UserServiceImpl  implements CreateValidate<CompanyUserReq>  {

       public void createUserValidate(CompanyCreateUserReq req){

           this.userCreateAndUpdateValidate.createValidate(req);
       }

        @Override
        public void createValidate(CompanyUserReq req) throws ValidateException {
            if (req == null || req.getCompanyId() == null || req.getUserId() == null) {
                throw new ValidateException("参数不正确");
            }
        }
    }

    @Override
    public Long create(CreateUserReq req) {
        return super.create(req);
    }
}

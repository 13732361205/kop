package com.github.kop.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.kop.rbac.module.entity.RbacCompany;
import com.github.kop.rbac.module.enums.AppHttpCodeEnum;
import com.github.kop.rbac.module.ex.NoceException;
import com.github.kop.rbac.module.ex.ValidateException;
import com.github.kop.rbac.module.req.company.CreateCompanyReq;
import com.github.kop.rbac.module.req.company.QueryCompanyReq;
import com.github.kop.rbac.module.req.company.UpdateCompanyReq;
import com.github.kop.rbac.module.req.user.CompanyCreateUserReq;
import com.github.kop.rbac.module.req.user.CreateUserReq;
import com.github.kop.rbac.module.res.company.CompanyQueryRes;
import com.github.kop.rbac.repo.CompanyRepository;
import com.github.kop.rbac.service.CompanyService;
import com.github.kop.rbac.service.CompanyUserService;
import com.github.kop.rbac.service.UserService;
import com.github.kop.rbac.utils.CreateValidate;
import com.github.kop.rbac.utils.UpdateValidate;
import java.util.ArrayList;
import java.util.List;

import com.github.kop.rbac.utils.UserInfoThread;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {

  private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
  private final CompanyRepository companyRepository;
  private final CompanyCreateAndUpdateValidate companyCreateAndUpdateValidate =
      new CompanyCreateAndUpdateValidate();

  


  public CompanyServiceImpl(CompanyRepository companyRepository) {

    this.companyRepository = companyRepository;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public Long create(CreateCompanyReq req) {
    companyCreateAndUpdateValidate.createValidate(req);
    RbacCompany rbacCompany = new RbacCompany();
    rbacCompany.setName(req.getName());
    rbacCompany.setAddress(req.getAddress());
    rbacCompany.setSocialCreditCode(req.getSocialCreditCode());
    rbacCompany.setPid(UserInfoThread.getCompanyId());
    return companyRepository.create(rbacCompany);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public int update(UpdateCompanyReq req) {
    companyCreateAndUpdateValidate.updateValidate(req);
    Long companyId = UserInfoThread.getCompanyId();
    if(!req.getId().equals(companyId)) {
    throw new NoceException("只能修改自己当前所在的企业");
    }

    RbacCompany company = this.companyRepository.byId(req.getId());

    if (company != null) {
      if (!StringUtils.isEmpty(req.getName())) {
        company.setName(req.getName());
      }
      if (!StringUtils.isEmpty(req.getAddress())) {
        company.setAddress(req.getAddress());
      }
      if (!StringUtils.isEmpty(req.getSocialCreditCode())) {
        company.setSocialCreditCode(req.getSocialCreditCode());
      }
      return companyRepository.update(company);
    }
    return -1;
  }

  @Override
  public CompanyQueryRes byId(Long id) {
    RbacCompany company = this.companyRepository.byId(id);
    return conv(company);
  }

  private CompanyQueryRes conv(RbacCompany company) {
    CompanyQueryRes companyQueryRes = new CompanyQueryRes();
    companyQueryRes.setId(company.getId());
    companyQueryRes.setName(company.getName());
    companyQueryRes.setAddress(company.getAddress());
    companyQueryRes.setSocialCreditCode(company.getSocialCreditCode());
    return companyQueryRes;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public int deleteById(Long id) {
    return companyRepository.deleteById(id);
  }

  @Override
  public IPage<CompanyQueryRes> page(Long page, Long size, QueryCompanyReq req) {
    IPage<RbacCompany> ipage = this.companyRepository.page(page, size, req);

    return ipage.convert(this::conv);
  }

  @Override
  public List<CompanyQueryRes> list(QueryCompanyReq req) {
    List<RbacCompany> list = this.companyRepository.list(req);
    List<CompanyQueryRes> res = new ArrayList<>(list.size());
    for (RbacCompany company : list) {
      res.add(conv(company));
    }

    return res;
  }

  protected static class CompanyCreateAndUpdateValidate
      implements CreateValidate<CreateCompanyReq>, UpdateValidate<UpdateCompanyReq> {



    @Override
    public void createValidate(CreateCompanyReq req) throws ValidateException {
      String name = req.getName();
      if (org.apache.commons.lang3.StringUtils.isEmpty(name)) {
        throw new ValidateException("企业名称必填");
      }
    }

    @Override
    public void updateValidate(UpdateCompanyReq req) throws ValidateException {
      Long id = req.getId();
      if (id == null) {
        throw new ValidateException(AppHttpCodeEnum.UPDATE_ID_ERROR);
      }
      String name = req.getName();
      if (org.apache.commons.lang3.StringUtils.isEmpty(name)) {
        throw new ValidateException("企业名称必填");
      }
    }
  }



}

package com.github.kop.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.kop.rbac.RbacApplication;
import com.github.kop.rbac.module.req.company.CreateCompanyReq;
import com.github.kop.rbac.module.req.company.UpdateCompanyReq;
import com.github.kop.rbac.module.res.company.CompanyQueryRes;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {RbacApplication.class})
class CompanyServiceTest {
  @Autowired private CompanyService companyService;
  @Autowired
  private UserService userService;
  @Autowired private CompanyUserService companyUserService;

  @org.junit.jupiter.api.Test
  void create() {
    CreateCompanyReq req = new CreateCompanyReq();
    req.setName("测试企业");
    req.setAddress("测试地址");
    req.setSocialCreditCode("测试社会信用编码");

    int i = companyService.create(req);
    Assertions.assertTrue(i > 0);
  }

  @org.junit.jupiter.api.Test
  void update() {
    UpdateCompanyReq req = new UpdateCompanyReq();
    req.setId(1551831906574725121L);
    req.setName("更新后名称");
    int update = this.companyService.update(req);
    Assertions.assertTrue(update > 0);
  }

  @org.junit.jupiter.api.Test
  void byId() {}

  @org.junit.jupiter.api.Test
  void deleteById() {
    int i = this.companyService.deleteById(1551831906574725121L);
    Assertions.assertTrue(i > 0);
  }

  @org.junit.jupiter.api.Test
  void page() {
    IPage<CompanyQueryRes> page = this.companyService.page(1L, 5L, null);
    System.out.println();
  }

  @org.junit.jupiter.api.Test
  void list() {}


}

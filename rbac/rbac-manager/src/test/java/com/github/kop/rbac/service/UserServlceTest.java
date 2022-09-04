 package com.github.kop.rbac.service;

 import com.github.kop.rbac.RbacApplication;
 import com.github.kop.rbac.module.req.user.CreateUserReq;
 import com.github.kop.rbac.service.impl.UserServiceImpl;
 import org.junit.jupiter.api.Assertions;
 import org.junit.jupiter.api.Test;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Qualifier;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.boot.test.context.SpringBootTest;

 import javax.annotation.Resources;

 @SpringBootTest(classes = {RbacApplication.class})
 public class UserServlceTest {
    @Autowired private CompanyService companyService;
    @Autowired private CompanyUserService companyUserService;

     @Autowired
     @Qualifier("userServiceImpl")
     private UserService userService;


//    @org.junit.jupiter.api.Test
//    void createUser(){
//        CreateUserReq createUserReq=new CreateUserReq();
//        createUserReq.setCompanyId(1564898952862621698L);
//        createUserReq.setName("xiezhihao1@qq.com");
//        createUserReq.setPassword("zhihaoAKB48");
//        createUserReq.setPhone("12356555");
//        final Long user = companyUserService.createCompanyUser(createUserReq);
//
//
//        Assertions.assertNotNull(user);
//    }



     @org.junit.jupiter.api.Test
     void login(){

         String a="xiezhihao1@qq.com"  ;
         String p="zhihaoAKB48";
         long c=1564898952862621698L;
         userService.login(a,p,c);
     }

    @org.junit.jupiter.api.Test
    void byId() {}
 }

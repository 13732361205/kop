package com.github.kop.rbac.utils;

import com.github.kop.rbac.module.ex.ValidateException;

public interface LoginValidate<T> {

  void checkLogin(T t) throws ValidateException;
}

package com.anwahh.designitandvideomenu.downloadzip.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//ElementType.METHOD方法上面  Target代表放在什么位置
@Retention(RetentionPolicy.RUNTIME)//是编译时检测还是运行时检测  Retention代表什么时候去检测
public @interface PermissionSuccess {
    public int requestCode();//请求码
}

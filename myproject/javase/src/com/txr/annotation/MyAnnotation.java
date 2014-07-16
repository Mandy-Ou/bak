package com.txr.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAnnotation {
	/**
	 * 
	 * 1．  Annotation需要声明为@interface这样的东西
2．  @Target(ElementType.TYPE)
代码这个annotation必须且一定要注释在什么语句上面
ElementType.TYPE代表此Annotation必须声明在public class Student{…}的上面，而不能写在任何的method{}（方法）或者是field（属性）的上方。
@Target:  表示该注解可以用于什么地方。可用ElementType枚举类型主要有：
              TYPE : 类、接口或enum声明
              FIELD: 域(属性)声明
              METHOD: 方法声明
              PARAMETER: 参数声明
              CONSTRUCTOR: 构造方法声明
              LOCAL_VARIABLE:局部变量声明
              ANNOTATION_TYPE:注释类型声明
              PACKAGE: 包声明
3．  Retention如果设为了RUNTIME，代表此annotation的具体实现可以在运行时用类反射来实现
我们看到了，annotation一般为一个@interface，也没啥具体的implementation(实现)
怎么实现这个annotation呢？类反射
@Retention: 表示需要在什么级别保存该注解信息。
可用RetentionPolicy枚举类型主要有：
             SOURCE: 注解将被编译器丢弃。
             CLASS  :  注解在class文件中可能。但会被VM丢弃。
             RUNTIME: VM将在运行时也保存注解(如果需要通过反射读取注解，则
使用该值)。
4. @Documented
@Documented:  将此注解包含在Javadoc中。
	 */
	String value();
}

## SpringMVC

 SpringMVC 是Spring Framework的一部分，是基于Java实现MVC的轻量级Web框架。
 [https://docs.spring.io/spring-framework/docs/5.2.0.RELEASE/spring-framework-reference/web.html#spring-web](https://docs.spring.io/spring-framework/docs/5.2.0.RELEASE/spring-framework-reference/web.html#spring-web)
  Spring MVC 的特点：
  1. 轻量级，简单易学
  2. 高效，基于请求响应的MVC框架
  3. 与Spring兼容性好，无缝结合
  4. 约定优于配置
  5. 功能强大：RESTful、数据验证、格式化、本地化、主题等
  6. 简洁灵活

Spring的web框架围绕**DispatherServlet**【调度Servlet】设计
DispatcherServlet的作用是将请求分发到不同的处理器。从Spring 2.5开始，使用Java 5或者以上版本的用户可以采用基于注解形式进行开发，十分简单
正因为SpringMVC好，简单，便捷，易学，天生和Spring无缝集成，使用约定优于配置，能够进行简单的junit测试，支持Resful风格，异常处理，本地化，国际化，数据验证，类型转换，拦截器等等。。。

## 中心控制器
Spring的web框架围绕**DispatherServlet**【调度Servlet】设计。DispatcherServlet的作用是将请求分发到不同的处理器。从Spring 2.5开始，使用Java 5或者以上版本的用户可以采用基于注解的control声明方式

Spring MVC框架像许多其它MVC框架一样，以请求为驱动，围绕一个中心Servlet分派请求及提供其它功能，DispatcherServlet是一个实际的servlet(它继承自HttpServlet基类)
![在这里插入图片描述](https://img-blog.csdnimg.cn/7ff93bbcbe4e4374b27242a5989e3c7a.png#pic_left)
## SpringMVC的原理如下图所示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/eb60374a0aa5494793e47cdf64de9780.png#pic_left)
当发起请求时被前置的控制器拦截到请求，根据请求参数生成代理请求，找到请求对应的实际控制器，控制器处理请求，创建数据模型，访问数据库，将模型响应给中心控制器，控制器使用模型与视图渲染视图结果，将结果返回给中心控制器，再将结果返回给请求者。

## SpringMVC执行原理
![在这里插入图片描述](https://img-blog.csdnimg.cn/6cc6167156644d51a3dcfc6a4b05bb24.png#pic_left)

实线表示SpringMVC框架提供的技术，不需要开发者实现，虚线表示需要开发者实现。

**分析执行流程：**
1. **DispacherServlet**表示前置控制器，是整个**SpringMVC**的控制中心。用户发出请求，**DispatcherServlet**接收请求并拦截请求。
2. **HandlerMapping**为处理器映射。**DispatcherServlet**调用**HandlerMapping**,**HandlerMapping**根据请求**url**查找**Handler**.
3. **HandlerExecution**表示具体的**Handler**,其主要作用是根据url查找控制器，如上**url**被查找控制器为：**hello**
4. **HandlerExecution**将解析后的信息传递给**DispatcherServlet**,如解析控制器映射等
5. **HandlerAdapter**表示处理器适配，其按照特定的规则去执行**Handler**
6. **Handler**让具体的**Controller**执行
7. **Controller**将具体的执行信息返回给HandlerAdapter,如**ModelAndView**
8. **HandlerAdapter**将视图逻辑名或模型传递给**DispatcherServlet**
9. **DispatcherServlet**调用视图解析器（**ViewResolver**）来解析HandlerAdapter传递的逻辑视图名
10. 视图解析器 将解析的逻辑视图名传给**DispatcherServlet**
11. **DispatcherSerlvet**根据视图解析器解析的视图结果，调用具体的视图
12. 最终视图呈现给用户

**为了理解原理，代码实现，如下:**
web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联一个springmvc的配置文件：【servlet-name】-servlet.xml-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servelt.xml</param-value>
        </init-param>
        <!--启动级别-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--/  匹配所有的请求；（不包括.jsp）-->
    <!--/* 匹配所有的请求；（包括.jsp）-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```
springmvc-servelt.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--1.添加处理映射器-->
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
    <!--2.添加处理适配器-->
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>
    <!-- 3.添加视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--Handler-->
    <bean id="/hello" class="com.release.controller.HelloController"/>
</beans>
```
HelloController .java
```java
public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //ModelAndView 模型和视图
        ModelAndView mv = new ModelAndView();
        //封装对象，放在ModelAndView 中model
        mv.addObject("msg","HelloSpringMVC!");
        //封装要跳转视图，放在放在ModelAndView中
        mv.setViewName("hello");///WEB-INF/jsp/hello.jsp
        return mv;
    }
}
```
hello.jsp

```xml
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

${msg}

</body>
</html>

```
**实际开发中，通过注解实现，代码瞬间变简单了,实现方式如下：**
web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--1.注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联一个springmvc的配置文件：【servlet-name】-servlet.xml-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servelt.xml</param-value>
        </init-param>
        <!--启动级别-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--/  匹配所有的请求；（不包括.jsp）-->
    <!--/* 匹配所有的请求；（包括.jsp）-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```
springmvc-servelt.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--自动扫描包，让指定包下的注解生效，由IOC容器统一管理-->
    <context:component-scan base-package="com.release.controller"/>
    <!--让Spring MVC不处理静态资源 .scc .js .html .mp3  .mp4-->
    <mvc:default-servlet-handler/>
    <!--
        支持mvc注解驱动
            在Spring中一般采用@RequestMapping注解来完成映射关系
            要想使@RequestMapping注解生效
            必须向上下文中注册DefaultAnnotationHandlerMapping
            和一个AnnotationMethodHandlerAdapter实例
            这两个实例分别在类级别和方法级别处理
            而annotation-driven配置帮助我们自动完成上述两个实例的注入。
    -->
    <mvc:annotation-driven/>

    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```
HelloController.java

```java
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("msg", "Hello SpringMVC Annotation!");
        return "hello";//会被视图解析器处理
    }
}
```
## 通过SpringMVC来实现转发和重定向，无需视图解析器

```java
@Controller
public class HelloController {

    @RequestMapping("/h/t1")
    public String test1() {
        //转发
        return "/hello";
    }

    @RequestMapping("/h/t2")
    public String test2() {
        //转发二
        return "forward:/hello";
    }

    @RequestMapping("/h/t3")
    public String test3() {
        //重定向
        return "redirect:/hello";
    }
}
```
## 通过SpringMVC来实现转发和重定向，有视图解析器

```java

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("msg", "Hello SpringMVC Annotation!");
        return "hello";//会被视图解析器处理
    }

    @RequestMapping("/hello2")
    public String test4() {
        //重定向
        return "redirect:/hello";
    }
```
## @Controller和@RestController的区别
1. `@RestController`相当于`@Controller`  和`@ResponseBody`合在一起的作用
2. 如果使用`@RestController`注解`Controller`层的话，则返回的是return里面的内容，无法返回到指定的页面，配置的视图解析器`InternalResourceViewResolver`也就自然没有作用了
3. 如果要返回到指定的页面，则需要用`@Controller`配合视图解析器`InternalResourceViewResolver`
4. 如果需要返回JSON、XML或自定义mediaType内容到页面，则需要在对应的方法上加上`@ResponseBody`注解。


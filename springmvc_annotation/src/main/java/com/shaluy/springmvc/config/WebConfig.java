package com.shaluy.springmvc.config;

import com.shaluy.springmvc.interceptor.FirstInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;
import java.util.Properties;

/*
    <!--组件扫描-->
    <context:component-scan base-package="com.shaluy.springmvc"></context:component-scan>

    <!--开启mvc的注解驱动-->
    <mvc:annotation-driven />

    <!--
        视图控制器：为当前的请求直接设置视图名称实现页面跳转
        若设置视图控制器，则只有视图控制器所设置的请求会被处理，其他的请求将全部404
        此时必须在配置一个标签：<mvc:annotation-driven />
    -->
    <mvc:view-controller path="/" view-name="index"></mvc:view-controller>
    <mvc:view-controller path="/to/add" view-name="employee_add"></mvc:view-controller>

    <!--
           当前工程的web.xml配置的前端控制器DispatcherServlet的url-pattern是/
           tomcat的web.xml配置的DefaultServlet的url-pattern也是/
           此时，浏览器发送的请求会优先被DispatcherServlet进行处理，但是DispatcherServlet无法处理静态资源
           若配置了<mvc:default-servlet-handler />，此时浏览器发送的所有请求都会被DefaultServlet处理
           若配置了<mvc:default-servlet-handler />和<mvc:annotation-driven />
           浏览器发送的请求会先被DispatcherServlet处理，无法处理在交给DefaultServlet处理
       -->
    <!--配置默认的servlet处理静态资源-->
    <mvc:default-servlet-handler/>

    <!--配置文件上传解析器-->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>

    <!--配置拦截器-->
    <mvc:interceptors>
        <!-- <ref bean="firstInterceptor" />-->
        <!--<bean class="com.shaluy.springmvc.interceptor.FirstInterceptor"/>-->
        <ref bean="secondInterceptor" />
        <!--bean和ref标签所配置的拦截器默认对DispatcherServlet处理的所有的请求进行拦截-->
        <mvc:interceptor>
            <!--配置需要拦截的请求的请求路径，/**表示所有请求-->
            <mvc:mapping path="/**"/>
            <!--配置需要排除拦截的请求的请求路径-->
            <mvc:exclude-mapping path="/abc"/>
            <!--配置拦截器-->
            <ref bean="firstInterceptor" />
        </mvc:interceptor>
    </mvc:interceptors>

    <!--配置异常处理器-->
    <!--<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <property name="exceptionMappings">
            <props>
                &lt;!&ndash;key设置要处理的异常，value设置出现该异常时要跳转的页面所对应的逻辑视图&ndash;&gt;
                <prop key="java.lang.ArithmeticException">error</prop>
            </props>
        </property>
        &lt;!&ndash;设置共享在请求域中异常信息的属性名&ndash;&gt;
        <property name="exceptionAttribute" value="ex"/>
    </bean>-->

    <!-- 配置Thymeleaf视图解析器 -->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"/>
        <property name="characterEncoding" value="UTF-8"/>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                        <!-- 视图前缀 -->
                        <property name="prefix" value="/WEB-INF/templates/"/>
                        <!-- 视图后缀 -->
                        <property name="suffix" value=".html"/>
                        <property name="templateMode" value="HTML5"/>
                        <property name="characterEncoding" value="UTF-8" />
                    </bean>
                </property>
            </bean>
        </property>
    </bean>
 */

/**
 * Date:2022/7/10
 * Author:ybc
 * Description:代替SpringMVC的配置文件
 * 组件扫描、mvc的注解驱动、视图控制器、默认的servlet处理静态资源
 * 文件上传解析器、拦截器、异常解析器、视图解析器
 */
//将类标识为配置类
@Configuration
//组件扫描
@ComponentScan("com.shaluy.springmvc")
//开启mvc的注解驱动
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //配置视图控制器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    //配置默认的servlet处理静态资源
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //配置文件上传解析器
    @Bean//@Bean注解可以将标识的方法的返回值作为bean进行管理，bean的id为方法的方法名
    public CommonsMultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        FirstInterceptor firstInterceptor = new FirstInterceptor();
        registry.addInterceptor(firstInterceptor).addPathPatterns("/**");
    }

    //配置异常解析器
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        Properties prop = new Properties();
        prop.setProperty("java.lang.ArithmeticException","error");

        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        exceptionResolver.setExceptionMappings(prop);
        exceptionResolver.setExceptionAttribute("ex");

        resolvers.add(exceptionResolver);
    }

    //配置生成模板解析器
    @Bean
    public ITemplateResolver templateResolver() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过WebApplicationContext 的方法获得
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(
                webApplicationContext.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    //生成模板引擎并为模板引擎注入模板解析器
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    //生成视图解析器并未解析器注入模板引擎
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
}

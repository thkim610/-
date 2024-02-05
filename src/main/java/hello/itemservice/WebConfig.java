package hello.itemservice;

import hello.itemservice.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean<Filter>filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); //로그인 인증 여부 필터 적용.
        filterRegistrationBean.setOrder(1); //필터 순서 : 첫번째
        filterRegistrationBean.addUrlPatterns("/*"); //전체 경로 적용.

        return filterRegistrationBean;
    }
}

package hello.itemservice;

import hello.itemservice.web.filter.LoginCheckFilter;
import hello.itemservice.web.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1) //인터셉터 체인: 첫번째 순서로 적용.
                .addPathPatterns("/**") //모든 경로 인터셉터 허용.
                .excludePathPatterns("/css/**", "/*.ico", "/error"); //해당 경로 인터셉터 제외.
    }

    //@Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean<Filter>filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); //로그인 인증 여부 필터 적용.
        filterRegistrationBean.setOrder(1); //필터 순서 : 첫번째
        filterRegistrationBean.addUrlPatterns("/*"); //전체 경로 적용.

        return filterRegistrationBean;
    }
}

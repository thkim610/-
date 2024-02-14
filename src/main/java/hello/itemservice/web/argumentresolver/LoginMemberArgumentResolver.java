package hello.itemservice.web.argumentresolver;

import hello.itemservice.domain.member.Member;
import hello.itemservice.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ArgumentResolver 를 활용하면 공통 작업이 필요할 때 컨트롤러를 더욱 편리하게 사용할 수 있다.
 */
@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    // @Login 애노테이션이 있으면서 Member 타입이면 해당 ArgumentResolver가 사용된다.
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        //이 파라미터에 어떤 애노테이션이 있는지 물어봄.
        //즉, @Login이 파라미터에 있는가를 체크한다.
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        //파라미터가 Member 타입인지 체크한다.
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType;
    }

    //resolveArgument : 컨트롤러 호출 직전에 호출 되어서 필요한 파라미터 정보를 생성해준다.
    //이후 스프링MVC는 컨트롤러의 메서드를 호출하면서 여기에서 반환된 member 객체를 파라미터에 전달해준다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false); //세션이 생성되지 않도록 false 설정

        if(session == null) {
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }

}

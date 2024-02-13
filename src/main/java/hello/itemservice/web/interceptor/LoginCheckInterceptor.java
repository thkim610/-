package hello.itemservice.web.interceptor;

import hello.itemservice.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        log.info("인증 체크 인터셉터 실행{}", requestURI);

        //세션이 없거나 로그인을 하지 않은 사용자의 경우 로그인 페이지로 이동 시키고, 컨트롤러로 넘어가지 않음.
        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            log.info("미인증 사용자 요청");
            //로그인으로 리다이렉트
            response.sendRedirect("/login?redirectURL="+requestURI);
            return false; //컨트롤러로 실행이 넘어가지 않음.
        }
        return true;
    }
}

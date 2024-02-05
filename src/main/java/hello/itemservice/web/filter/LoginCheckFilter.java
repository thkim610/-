package hello.itemservice.web.filter;

import hello.itemservice.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpResponse;

@Slf4j
public class LoginCheckFilter implements Filter {

    //로그인 필요 없이 접근이 허용 가능한 URL 패턴들(whiteList)
    private static String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //다양한 request, response 기능을 위해 HttpServletRequest, HttpServletResponse 다운캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //어디서 요청이 들어왔는지 경로 확인.
        String requestURI = httpRequest.getRequestURI();


        try{
            log.info("인증 체크 필터 시작 {}", requestURI);

            if(isLoginCheckPath(requestURI)){
                log.info("인증 체크 로직 실행 {}", requestURI);

                HttpSession session = httpRequest.getSession(false);

                //세션이 없거나 세션에 로그인 정보가 없으면, 미인증 사용자로 로그인 페이지로 이동.
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                    log.info("미인증 사용자 요청 {}", requestURI);

                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }
            // 다음 필터가 있으면 필터가 호출되고, 없으면 서블릿(DispatcherServlet)이 호출된다.
            chain.doFilter(request, response);

        }catch (Exception e){
            throw e; //예외 로깅 가능하지만, 톰캣까지 예외를 보내주어야 함
        }finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI); //whiteList인 경로는 false 반환.
    }
}

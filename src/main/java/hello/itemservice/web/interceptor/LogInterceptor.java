package hello.itemservice.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 로그를 출력하는 스프링 인터셉터
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    /**
     * preHandle에서 지정한 값을 postHandle , afterCompletion 에서 함께 사용하려면 어딘가에 담아두어야 한다.
     * LogInterceptor도 싱글톤처럼 사용되기 때문에 맴버변수를 사용하면 위험하다. 따라서 request에 담아두었다.
     * 이 값은 afterCompletion에서 request.getAttribute(LOG_ID)로 찾아서 사용한다.
     */
    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString(); //사용자를 구분하기 위해 UUID를 남김.

        request.setAttribute(LOG_ID, uuid);

        //@RequestMapping: HandlerMethod 사용.
        //정적 리소스: ResourceHttpRequestHandler 사용.
        if(handler instanceof HandlerMethod){
            //호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
            //따라서 필요한 정보를 꺼내 쓸 수 있다.
            HandlerMethod handlerMethod = (HandlerMethod) handler;
        }

        log.info("REQUEST [{}][{}][{}]", requestURI, uuid, handler);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView); // modelAndView에 담긴 데이터 로그 출력.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        Object uuid = request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}][{}][{}]", requestURI, uuid, handler);

        if(ex != null){
            log.info("afterCompletion error!!", ex);
        }
    }
}

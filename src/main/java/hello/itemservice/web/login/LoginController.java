package hello.itemservice.web.login;

import hello.itemservice.domain.login.LoginService;
import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import hello.itemservice.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    //로그인 폼 화면 출력
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginFormDto form){
        return "/login/loginForm";
    }

    //로그인
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginFormDto formDto, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/")String redirectURL,
                        HttpServletRequest request){

        log.info("login: formDto={}", formDto);

        //bindingResult에 오류가 있을 시, 로그인 폼으로 이동.
        if(bindingResult.hasErrors()){
            return "/login/loginForm";
        }

        //입력받은 id, password로 DB(store)에 회원이 있는지 확인 후, 있으면 member 객체 반환.
        Member loginMember = loginService.login(formDto.getLoginId(), formDto.getPassword());

        log.info("login: loginMember={}", loginMember);

        if(loginMember == null){
            bindingResult.reject("loginFail", "아이디 또는 패스워드가 일치하지 않습니다.");
            return "/login/loginForm";
        }

        //로그인 성공 처리
        //request.getSession() : 세션이 있으면 있는 세션을 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보를 보관.
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        //로그인 시, 마지막에 있었던 페이지로 다시 이동.
        return "redirect:" + redirectURL;
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        //생성된 세션으로만 확인.
        //request.getSession(false) : 세션이 있으면 기존 세션을 반환, 없으면 null 반환.
        HttpSession session = request.getSession(false);

        //로그아웃 시, 세션을 만료시켜 제거.
        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }
}

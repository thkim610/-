package hello.itemservice.web.login;

import hello.itemservice.domain.login.LoginService;
import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String login(@Valid @ModelAttribute("loginForm") LoginFormDto formDto, BindingResult bindingResult){

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

        //로그인 성공 처리 TODO

        return "redirect:/";
    }
}

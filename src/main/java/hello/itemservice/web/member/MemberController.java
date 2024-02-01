package hello.itemservice.web.member;

import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    //회원가입 폼 화면 출력
    @GetMapping("/add")
    public String addForm(@ModelAttribute("member")Member member){
        return "/members/addMemberForm";
    }

    //회원가입
    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("member")Member member, BindingResult bindingResult){

        //에러가 존재하면 다시 회원가입 화면으로 돌아감.
        if(bindingResult.hasErrors()){
            return "/members/addMemberForm";
        }

        memberRepository.save(member);

        return "redirect:/"; //회원가입 완료 시, 홈화면으로 돌아감.
    }
}

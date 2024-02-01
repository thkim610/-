package hello.itemservice.domain.login;

import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null이면 로그인 실패
     */

    //로그인
    public Member login (String loginId, String password){
        log.info("login: loginId={}", loginId);
        log.info("login: password={}", password);

        //loginId로 member 조회 후, password가 일치하는지 확인한 후, 일치하면 member 반환.
        //그렇지 않으면, null 반환.
        Member loginMember = memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);

        return loginMember;
    }
}

package hello.itemservice.domain.member;

import hello.itemservice.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    //회원가입
    public Member save(Member member){
        member.setId(++sequence);

        log.info("save: member={}",member);
        store.put(member.getId(), member);

        return member;
    }
}

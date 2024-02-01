package hello.itemservice.domain.member;

import hello.itemservice.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    //회원 조회
    public Member findById(Long id){
        return store.get(id);
    }

    //회원 전체 조회
    public List<Member> findAll(){
        return new ArrayList<>(store.values()); //Map에 저장된 member 객체들을 리스트에 저장하여 반환.
    }

    //로그인 id로 회원 찾기
    public Optional<Member>findByLoginId(String loginId){
        List<Member> all = new ArrayList<>();

        return all.stream().filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    //초기화 메서드(테스트)
    public void storeClear(){
        store.clear();
    }
}

package hello.itemservice.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {


    private Long id;

    @NotEmpty
    private String loginId; //로그인 ID (사용자)
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
}

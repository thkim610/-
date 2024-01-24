package hello.itemservice.web.validation.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateDto {

    @NotNull
    private Long id;

    @NotBlank(message = "공백 X") // errors.properties에 적용된 것이 없다면, 기본 오류 메시지로 출력됨.
    private String itemName; //상품명

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price; //상품가격, Integer를 쓴 이유 : null 값도 들어갈 수 있게 하기 위해서

    @NotNull
    private Integer quantity; //요구사항 : 수량 수정 시, 제한 없음.

}

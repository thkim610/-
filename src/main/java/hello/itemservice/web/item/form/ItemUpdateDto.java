package hello.itemservice.web.item.form;

import hello.itemservice.domain.item.ItemType;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    private Boolean open; // 판매 여부 - HTML 폼에서 'on/off' 문자 값으로 넘어오는데 스프링은 이를 true/false로 변환해준다. (스프링 타입 컨버터)
    private List<String> regions; // 등록 지역
    private ItemType itemType; // 상품 종류
    private String deliveryCode; // 배송 방식


}

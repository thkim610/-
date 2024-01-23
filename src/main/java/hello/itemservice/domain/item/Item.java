package hello.itemservice.domain.item;
/**
 * javax.validation 으로 시작하면 특정 구현에 관계없이 제공되는 표준 인터페이스이고,
 * org.hibernate.validator로 시작하면 하이버네이트 validator 구현체를 사용할 때만 제공되는 검증 기
 * 능이다.
 */

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range; //hibernate.validator에서만 동작.
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank; //어떤 구현체에서도 동작.(표준)
import javax.validation.constraints.NotNull; //어떤 구현체에서도 동작.(표준)
import java.util.List;

@Getter @Setter
public class Item {

    private long id;

    @NotBlank(message = "공백 X") // errors.properties에 적용된 것이 없다면, 기본 오류 메시지로 출력됨.
    private String itemName; //상품명

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price; //상품가격, Integer를 쓴 이유 : null 값도 들어갈 수 있게 하기 위해서

    @NotNull
    @Max(9999)
    private Integer quantity; //수량

    private Boolean open; // 판매 여부 - HTML 폼에서 'on/off' 문자 값으로 넘어오는데 스프링은 이를 true/false로 변환해준다. (스프링 타입 컨버터)
    private List<String> regions; // 등록 지역
    private ItemType itemType; // 상품 종류
    private String deliveryCode; // 배송 방식

    public Item() {
    }

    public Item( String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

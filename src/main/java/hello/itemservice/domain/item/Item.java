package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Item {

    private long id;
    private String itemName; //상품명
    private Integer price; //상품가격, Integer를 쓴 이유 : null 값도 들어갈 수 있게 하기 위해서
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

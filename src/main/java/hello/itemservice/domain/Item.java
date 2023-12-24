package hello.itemservice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {

    private long id;
    private String itemName; //상품명
    private Integer price; //상품가격, Integer를 쓴 이유 : null 값도 들어갈 수 있게 하기 위해서
    private Integer quantity; //수량

    public Item() {
    }

    public Item( String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

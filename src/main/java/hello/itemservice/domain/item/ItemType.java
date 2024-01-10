package hello.itemservice.domain.item;

import lombok.Getter;

@Getter
public enum ItemType {

    Book("도서(Book)"), Food("음식(Food)"), ETC("기타(ETC)");

    private final String description; //설명을 위해 생성

    ItemType(String description) {
        this.description = description;
    }
}

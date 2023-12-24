package hello.itemservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    //itemRepository 객체 생성
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach //테스트가 끝날 때마다 동작하는 어노테이션
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        //상품객체 생성
        Item item = new Item("itemA", 10000, 10);
        //when
        //상품 등록
        Item saveItem = itemRepository.save(item);
        //then
        //findById 메서드를 통해 잘 저장되었는지 확인.
        Item findItem = itemRepository.findById(item.getId());
        //테스트 검증 (Assertions)
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {
        //given
        //상품객체 생성
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 20000, 15);

        //상품 등록
        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        //상품 전체 조회
        List<Item> itemList = itemRepository.findAll();

        //then
        assertThat(itemList.size()).isEqualTo(2); //사이즈를 비교
        assertThat(itemList).contains(item1, item2); //각 상품 객체를 가지고 있는지를 검증.
    }

    @Test
    void update() {
        //given
        //상품객체 생성
        Item item = new Item("itemA", 10000, 10);

        //상품 등록
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        //when
        //수정할 내용을 담은 상품객체 생성
        Item updateParam = new Item("itemB", 20000, 20);

        //수정한 내용으로 상품 수정
        itemRepository.update(itemId, updateParam);

        //then
        //수정 후의 상품 조회
        Item findItem = itemRepository.findById(itemId);

        //수정할 내용과 수정된 상품 객체를 비교하여 변경되었는지 검증
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

}
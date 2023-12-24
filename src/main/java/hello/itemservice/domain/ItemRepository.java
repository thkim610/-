package hello.itemservice.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    //실제로는 hashmap을 사용하지 않는게 좋음. (현재는 데이터베이스를 사용하지 않기 때문에 hashmap 사용)
    //-> 멀티 쓰레드 환경에서 여러개가 동시에 스토어에 접근하게 된다면 동시에 여러 쓰레드가 접근하게 된다.
    // 따라서 'ConcurrentHashMap'을 사용하는 것이 좋다.
    private static final Map<Long, Item> store = new HashMap<>(); //static
    //long도 마찬가지로 long 대신 atomicLong 등을 쓰는 것이 좋다. (멀티 쓰레드 환경에서 값이 꼬일 수 있기 때문에)
    private static long sequance = 0L; //static

    //상품 등록
    public Item save (Item item){
        item.setId(++sequance); //id 값 생성.
        store.put(item.getId(), item); //item 저장

        return item;
    }

    //상품 조회
    public Item findById(Long id){
        return store.get(id);
    }

    //상품 전체 목록 조회
    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    //상품 정보 수정
    public Item update(Long id, Item updateParam){ //(+)ItemParamDTO 생성하여 리팩토링 해보기.
        //수정할 상품 조회
        Item findItem = findById(id);

        //상품 정보 수정
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());

        return findItem;
    }

    //상품 저장소 제거 -> 테스트시, 사용
    public void clearStore(){
        store.clear();
    }
}

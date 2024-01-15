package hello.itemservice.web.v2;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/v2/items")
/*
@RequiredArgsConstructor는 private final 키워드가 붙은 필드의 생성자를 만들어 준다.

    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    => 생성자 주입 방식 (DI)
 */
@RequiredArgsConstructor
public class ItemControllerV2 {

    private final ItemRepository itemRepository;

    /**
     * 성능 측면에서 static 클래스로 따로 분리 해보기!!
     * @return
     */
    //지역 데이터 생성
    @ModelAttribute("regions") //이 컨트롤러를 호출할 때 자동으로 이 ModelAttribute의 데이터를 모델에 담아준다.
    public Map<String, String> regions(){
        //지역 데이터를 넘기기 위한 map 생성
        Map<String, String> regions = new LinkedHashMap<>(); //순서를 보장하기 위해 LinkedHashMap 사용.
        regions.put("SEOUL","서울(SEOUL)");
        regions.put("BUSAN","부산(BUSAN)");
        regions.put("JEJU","제주(JEJU)");

        return regions; // model.addAttribute("regions", regions);
    }

    //상품 유형 데이터 생성
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){

        return ItemType.values();// enum으로 생성한 value(Book, Food, ETC)가 배열로 넘어온다.
    }

    //배송 방식 데이터 생성
    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes(){
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송(Fast)"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "보통 배송(Normal)"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송(Slow)"));

        return deliveryCodes;
    }

    //상품 목록 조회
    @GetMapping
    public String items(Model model){

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "v2/items";
    }

    //상품 상세 조회
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){

        Item item = itemRepository.findById(itemId); //id 값으로 item 객체 생성.
        model.addAttribute("item", item); //모델에 item 객체 저장.

        return "v2/item";

    }

    //상품 등록 화면 출력
    @GetMapping("/add")
    public String viewAddForm(Model model){
        /*
        타임리프에서 th:object를 적용하려면 먼저 해당 오브젝트 정보를 넘겨주어야 한다.
        따라서, 빈 객체를 만들어서 뷰에 전달한다.
         */
        model.addAttribute("item", new Item()); // 빈 item 객체 저장.

        return "v2/addForm";
    }

    //상품 등록
    @PostMapping("/add")
    //@ModelAttribute의 name 속성을 생략했을 때 : 클래스명에서 첫글자만 소문자로 바꾼이름(Item -> item)으로 모델 객체를 만든다.
    //RedirectAttributes : 리다이렉트와 관련된 속성을 넣는 인터페이스
    // 순서 주의 !! => BindingResult는 @ModelAttribute 바로 다음에 넣어주어야 한다. -> item 객체의 바인딩 결과를 담고 있기 때문에
    public String save(@ModelAttribute("item") Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        /* item 값이 잘 들어오는지 검증. */
        /**Binding Result : 자동으로 view에 에러에 대한 정보를 넘겨준다.
         * 따라서, model에 에러 객체를 따로 담을 필요가 없다. (스프링이 자동으로 처리해준다.)
         */

        //검증 로직
        //1. 상품명에 글자가 없을 때
        if(!StringUtils.hasText(item.getItemName())){
            //FieldError(객체명, 필드명, 기본메시지)
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다."));
        }
        //2. 가격이 null이거나 1000원보다 작거나 1000000원보다 클 때
        if(item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000원까지 허용합니다."));
        }
        //3. 수량이 null이거나 9999개보다 클 때
        if(item.getQuantity()==null || item.getQuantity() > 9999){
            bindingResult.addError(new FieldError("item", "quantity", "수량은 0 ~ 9,999개까지 허용합니다."));
        }
        //특정 필드가 아닌 복합 룰 검증(global errors)
        //4. 가격과 수량이 null이 아니고 가격*수량이 10000원 이하일 때
        if(item.getPrice()!=null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }
        //검증에 실패 -> 다시 입력 폼으로 이동.
        if(bindingResult.hasErrors()){
            log.info("errors = {} ", bindingResult);
            return "v2/addForm";
        }


        //검증 성공 로직
        /*
        @ModelAttribute를 사용하면 아래의 모델 객체 생성 과정을 다 처리해준다.
         Item item = new Item();
         item.setItemName(itemName);
         item.setPrice(price);
         item.setQuantity(quantity);
        */

        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemTypes={}", item.getItemType());

        Item savedItem = itemRepository.save(item);
        //model.addAttribute("item", item); //@ModelAttribute는 name 속성으로 지정한 이름("item")으로 모델에 객체를 저장한다.

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true); // 저장이 완료되었을 때 상태

        //return "v2/item"; //새로고침을 통해 바로 뷰 템플릿으로 넘어가면 계속 POST 호출이 되어 중복으로 등록되게 된다.

        //새로 고침 문제를 해결하기 위해 상품 저장 후에 상품 상세 화면으로 리다이렉트를 호출.(GET으로 재호출)
        /* PRG Post/Redirect/Get 해결 방식 */
        return "redirect:/v2/items/{itemId}";
    }

    //상품 수정 화면 출력
    @GetMapping("/{itemId}/edit") // GET 경로에 있는 변수명과 @PathVariable의 변수명이 같아야 함.
    public String viewEditForm(@PathVariable Long itemId, Model model){

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "v2/editForm";
    }

    //상품 수정
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, @ModelAttribute Item editItem){

        itemRepository.update(itemId, editItem);

        //redirect를 하면 /v2/items/{itemId}/edit 경로가 아닌 아래의 경로로 다시 재요청된다.
        return "redirect:/v2/items/{itemId}"; //{itemId}값의 @PathVariable 값으로 들어간다.

    }




}

package hello.itemservice.web;

import hello.itemservice.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * supports : 검증기를 지원하는지 체크
 * validate : 실제 검증이 이루어지는 메서드
 */
@Slf4j
@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        //item == clazz
        //item의 자식클래스여도 해당 메서드를 통과 가능(검증 가능)하다. => 즉, 자식클래스도 검증 가능.
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target; //다운 캐스팅하여 사용.

        /* item 값이 잘 들어오는지 검증. */
        /**
         * FieldError(objectName, field, rejectedValue, bindingFailure, codes, arguments, defaultMessage)
         * objectName : 오류가 발생한 객체 이름
         * field : 오류 필드
         * rejectedValue : 사용자가 입력한 값(거절된 값)
         * bindingFailure : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
         * codes : 메시지 코드
         * arguments : 메시지에서 사용하는 인자
         * defaultMessage : 기본 오류 메시지
         */

        //검증 로직

        //1. 상품명에 글자가 없을 때
        //ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required"); //아래와 동일. 공백 같은 단순한 기능만 제공.
        if(!StringUtils.hasText(item.getItemName())){
            //FieldError(객체명, 필드명, 기본메시지)
//            bindingResult.addError(new FieldError("item", "itemName",
//                    item.getItemName(), false, new String[]{"required.item.itemName"}, null, null ));
            errors.rejectValue("itemName", "required");
        }
        //2. 가격이 null이거나 1000원보다 작거나 1000000원보다 클 때
        if(item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000){
//            bindingResult.addError(new FieldError("item", "price",
//                    item.getPrice(), false, new String[]{"range.item.price"}, null, null));
            errors.rejectValue("price", "range", new Object[]{1000, 10000000}, null);
        }
        //3. 수량이 null이거나 9999개보다 클 때
        if(item.getQuantity()==null || item.getQuantity() > 9999){
//            bindingResult.addError(new FieldError("item", "quantity",
//                    item.getQuantity(), false, new String[]{"max.item.quantity"}, null, null));
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        //특정 필드가 아닌 복합 룰 검증(global errors)
        //4. 가격과 수량이 null이 아니고 가격*수량이 10000원 이하일 때
        if(item.getPrice()!=null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000){
//                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }


    }
}

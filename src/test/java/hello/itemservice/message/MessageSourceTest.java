package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void helloMessage() {
        /**
         * messageSource.getMessage(code, args, locale);
         * code: 입력할 커맨드
         * args: 파라미터 값 -> messages.properties에 파라미터도 설정 가능하기 때문.
         * locale: 지역 정보
         */
        //locale이 null이면, basename으로 등록한 기본이름의 메시지 파일을 조회한다.
        // 따라서, 디폴트인 messages.properties가 동작하여 '안녕'이 출력.
        String result = messageSource.getMessage("hello", null, null);

        assertThat(result).isEqualTo("안녕");
    }

    //messages.properties에 code 값이 없을 때, NoSuchMessageException 예외 발생.
    @Test
    void notFoundMessageCode(){
        assertThatThrownBy(()->messageSource.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    //messages.properties에 code 값이 없을 때, messageSource.getMessage()에 기본 값을 설정하면,
    // 예외가 발생하지 않고 기본 값으로 대체됨.
    @Test
    void notFoundMessageCodeDefaultMessage(){
        String result = messageSource.getMessage("no_code", null, "기본 메세지", null);

        assertThat(result).isEqualTo("기본 메세지");
    }

    //파라미터 값 입력했을 때
    @Test
    void argumentMessage(){
        String result = messageSource.getMessage("hello.name", new Object[]{"Spring"}, null);

        assertThat(result).isEqualTo("안녕 Spring");
    }

    //국제화
    @Test
    void defaultLang(){
        String result = messageSource.getMessage("hello", null, null); //안녕
        String result2 = messageSource.getMessage("hello", null, Locale.KOREA); //안녕

        assertThat(result).isEqualTo("안녕");
        assertThat(result2).isEqualTo("안녕");

    }
    @Test
    void enLang(){
        String result = messageSource.getMessage("hello", null, Locale.ENGLISH); //hello

        assertThat(result).isEqualTo("hello");

    }
}

package hello.core2.singleton;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: A 사용자가 10000원을 주문
        int userBPrice = statefulService2.order("UserB", 20000);
        int userAPrice = statefulService1.order("UserA", 10000);
        //ThreadB: B 사용자가 20000원을 주문

        //ThreadA: 사용자 A가 주문 금액 조회
        Assertions.assertThat(userAPrice).isEqualTo(10000);
    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}

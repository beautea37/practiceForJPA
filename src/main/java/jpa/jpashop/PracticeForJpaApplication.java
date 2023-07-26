package jpa.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PracticeForJpaApplication {


    public static void main(String[] args) {
        SpringApplication.run(PracticeForJpaApplication.class, args);
    }

    @Bean
    Hibernate5JakartaModule hibernate5JakartaModule() {
        return new Hibernate5JakartaModule();
    }


//    지연로딩도 되게하는 코드
//    @Bean
//    Hibernate5JakartaModule hibernate5JakartaModule() {
//        Hibernate5JakartaModule hibernate5JakartaModule = new Hibernate5JakartaModule();
//        hibernate5JakartaModule.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true);
//        return hibernate5JakartaModule;
//    }

    // 강제 지연 로딩 설정을 하기 위해서는 이와 같이 추가해주면 된다.
//    @Bean
//    Hibernate5JakartaModule hibernate5JakartaModule() {
//        Hibernate5JakartaModule hibernate5JakartaModule = new Hibernate5JakartaModule();
//        hibernate5JakartaModule.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true);
//        return hibernate5JakartaModule;
//    }
}

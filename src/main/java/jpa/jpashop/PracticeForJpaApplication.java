package jpa.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PracticeForJpaApplication {




    public static void main(String[] args) {
        practice p = new practice();
        p.setId("test");
        String id = p.getId();
        System.out.println("id = " + id);


        SpringApplication.run(PracticeForJpaApplication.class, args);
    }

}

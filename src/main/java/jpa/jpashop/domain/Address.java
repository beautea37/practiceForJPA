package jpa.jpashop.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable     //JPA의 내장타입이기 때문에 넣어줘야 함.  #추가  //Member의 address와 연결
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

}

package jpa.jpashop.domain;


import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import lombok.Getter;

@Embeddable     //JPA의 내장타입이기 때문에 넣어줘야 함.  #추가  //Member의 address와 연결
@Getter
//값 타입은 변경 불가능하게 설계해야하기 때문에 setter를 안 넣는게 좋다.
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //주소는 변경되면 안되기 때문에 public은 주로 쓰지 않음.
    protected Address() {

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
    
}

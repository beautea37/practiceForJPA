package jpa.jpashop.domain;


import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import lombok.Getter;

//Not Entity
@Embeddable
//JPA에서 임베디드 타입(Embeddable Type)을 정의할 때 사용됩니다. 임베디드 타입은 재사용 가능한 공통 매핑 정보를 캡슐화하여 여러 엔티티에서 사용할 수 있는 모델입니다.
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

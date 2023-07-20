package jpa.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
@Getter
@Setter
//MemberForm없이 Entity에서도 코드를 작성해도 되지만 Entity는 순수히 Entity만 있게 두는 것이 좋다.
//Entity에다가 이거저거 많이 넣다보면 Entity가 view에 종속적으로 변해 유지보수가 힘들어지기 때문에 dto나 form객체를 쓰는게 좋다.
public class MemberForm  {

    @NotEmpty(message = "회원명은 필수입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}

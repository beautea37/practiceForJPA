package jpa.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
//    @GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    //FK가 중복일 가까운 테이블을 FK메인으로 두면 됨.
    //그래서 여기다가는 MAPPED를 Member라고 선언해준 것.
    //주인관계가 누구인지 명시해주는 것. 넣어주지 않으면 두 개의 단방향이 되어 오류 발생.
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
}

package jpa.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;


    @OneToMany(mappedBy = "member")
    //FK가 중복일 가까운 테이블을 FK메인으로 두면 됨.
    //그래서 여기다가는 MAPPED를 ORDER라고 선언해준 것.
    //쉽게 표현하면 읽기 전용이라 봐라 그냥
    //실제 FK메인은 Order.java의 member
    private List<Order> orders = new ArrayList<>();

















}

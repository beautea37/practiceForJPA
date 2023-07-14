package jpa.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)       //상속관계 전략 지정. Item 안에 album 등등이 있는 것이니 여기다가 전략을 짜는 것임.
@DiscriminatorColumn(name = "dtype")    //상속관계 매핑을 위한 것. 상속되는 자식들에겐 @DiscriminateValue라고 써준다.
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private Integer price;

    private Integer stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}

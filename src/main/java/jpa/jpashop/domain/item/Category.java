package jpa.jpashop.domain.item;

import com.p6spy.engine.logging.P6LogLoadableOptions;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;


    @ManyToMany
    @JoinTable(name = "category_item", // 중간 매핑 테이블의 이름을 지정
            joinColumns = @JoinColumn(name = "category_id"), // 현재 클래스의 연관 필드의 외래 키 컬럼 이름을 지정
            inverseJoinColumns = @JoinColumn(name = "item_id") // 연관된 클래스의 연관 필드의 외래 키 컬럼 이름을 지정
    )
    //객체는 CollectionData때문에 다대다 관계가 가능한데 관계형 DB는 Collection관계를 양 쪽에 못가져서 중간 테이블이 필요하다.
    private List<Item> items = new ArrayList<>();

    @ManyToOne      //부모니까
    @JoinColumn(name="parent_id")
    private  Category parent;

    @OneToMany(mappedBy = "parent")  //자식이니까
    private List<Category> child = new ArrayList<>();



















}

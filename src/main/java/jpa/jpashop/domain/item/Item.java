package jpa.jpashop.domain.item;

import jakarta.persistence.*;
import jpa.jpashop.Exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)       //상속관계 전략 지정. Item 안에 album 등등이 있는 것이니 여기다가 전략을 짜는 것임.
@DiscriminatorColumn(name = "dtype")    //상속관계 매핑을 위한 것. 상속되는 자식들에겐 @DiscriminateValue라고 써준다.
@Getter
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


    //비즈니스 로직 StockQuantity 파트
    //보통 ItemService에서 stock을 넣고 이러는 사람들이 많은데,
    //객체 지향적으로 생각해보면 데이터를 갖고 있는 부분에 비즈니스 메서드가 있는게 가장 이상적이다.
    //재고 증가
    public void addStock(Integer quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(Integer quantity) {
        Integer restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("장바구니에 수량이 없습니다.");
        }
        this.stockQuantity = restStock;
    }
}

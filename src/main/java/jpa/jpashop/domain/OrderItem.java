package jpa.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    private Item item;

    @ManyToOne      //하나의 Order가 여러 orderItem을 가질 수 있기 때문에
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer orderPrice;

    private Integer count;


}

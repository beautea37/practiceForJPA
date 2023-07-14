package jpa.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)      //왜 OTM인지 계속 곱씹어라 #의문
    // cascade = CascadeType.ALL 은 order 객체와 라이프사이클을 공유하는 OrderItem 객체에 대한 처리를 지정
    // 즉, 주문(Order) 객체가 생성, 수정, 삭제 등이 이루어질 때 연관된 주문 항목(OrderItem) 객체들도 같이 처리됨.
    //이거 세팅해놓으면 주문과 주문항목 사이를 따로 처리하는 번거로움을 덜 수 있다.

    private List<OrderItem> orderItems = new ArrayList<>();


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    //주문 시간

    private OrderStatus status; //주문 상태. order, cancel 추가.




    //연관 관계 메서드.
    //오더와 멤버는 양방향 관계인데, 그렇기 때문에 양방향 세팅을 해주는게 좋음.
    //한쪽에다가 적으면 되는데 전반적으로 컨트롤 하는 쪽에다가 넣는 것이 좋다.
    //#의문
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItems(OrderItem orderItem) {
         orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}

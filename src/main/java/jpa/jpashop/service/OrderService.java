package jpa.jpashop.service;

import jpa.jpashop.domain.*;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.repository.ItemRepository;
import jpa.jpashop.repository.MemberRepository;
import jpa.jpashop.repository.MemberRepositoryOld;
import jpa.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
//    private final MemberRepositoryOld memberRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    



    /*비즈니스 로직*/

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //Entity 조회
//        Member member = memberRepository.findOne(memberId);
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
//        delivery.setStatus(DeliveryStatus.READY);       //###

        //주문상품 생성하기. 생성 메소드 이용해서
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        //Order에서 Cascade했기 떄문에 orderItems와 delivery를 따로 저장하지 않아도 orderRepository하나만으로  persist로 저장이 가능하다.
        //Cascade의 범위는 자기 정하기 나름이긴 하나 Order가 oderitem과 delivery를 관리하기 때문에 이 정도에서 끝내는 것이 좋고 더 연장시키면 안 좋음.
        //참조가 많아지면 복잡해지니 계속 생각해 봐야하는 부분이다.
        orderRepository.save(order);

        return order.getId();
    }

    /*주문 취소*/
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 Entity조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();

    }

    /*검색*/

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }
}


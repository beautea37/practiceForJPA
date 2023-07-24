package jpa.jpashop.service;

import jakarta.persistence.EntityManager;
import jpa.jpashop.Exception.NotEnoughStockException;
import jpa.jpashop.domain.Address;
import jpa.jpashop.domain.Member;
import jpa.jpashop.domain.item.Book;
import jpa.jpashop.domain.Order;
import jpa.jpashop.domain.OrderStatus;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품_주문() throws Exception {

        //given
        Member member = createMember();

        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 Order", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문 상품 종류 수가 정확해야 함.", 1, getOrder.getOrderItems().size());
        assertEquals("총액 = 가격 * 수량", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 주는 코드", 8, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("jpaBook", 10000, 10);
        int orderCount = 11;

        //when
        orderService.order(member.getId(), item.getId(), orderCount);

        //then
        fail("재고 초과 주문 ");
    }

    @Test
    public void 주문_취소() throws Exception {

        //given
        Member member = createMember();
        Book item = createBook("jpaBook", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소 시에는 Cancel이 되어야 함.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문 취소 시 재고 증가해야 함", 10, item.getStockQuantity());

    }





    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }


    //ctrl alt p로 추출해온 것.
    private Member createMember() {
        Member member = new Member();
        member.setName("John");
        member.setAddress(new Address("대전", "유성구", "123-123"));
        em.persist(member);
        return member;
    }

}
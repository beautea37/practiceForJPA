package jpa.jpashop.api;

import jpa.jpashop.domain.Address;
import jpa.jpashop.domain.Order;
import jpa.jpashop.domain.OrderSearch;
import jpa.jpashop.domain.OrderStatus;
import jpa.jpashop.repository.OrderRepository;
import jpa.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpa.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne에서의 성능 최적화
 * Order(M) -> Member(O)
 * Order(O) -> Delivery(O)
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;


    //Entity 모두 반환할 경우
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {

        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();        //LAZY강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }

    //EntityToDto
    //Entity를 보호할 수는 있으나 쿼리를 개많이 먹음.
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }


    //fetch join을 통해 집어오는 방법. 5 >>> 1 // 유연성이 높아 다활용 가능
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
//        List<Order> orders = OrderRepository.findAllWithMemberDelivery();
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }
    
    //DTO로 직접 조회. 네트워크 효율은 좋으나 유연성은 떨어짐.
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }


}

package jpa.jpashop.controller;

import jpa.jpashop.domain.Member;
import jpa.jpashop.domain.Order;
import jpa.jpashop.domain.OrderSearch;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.service.ItemService;
import jpa.jpashop.service.MemberService;
import jpa.jpashop.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order") //@requestParam < html에서 id값 매핑
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }
//@GetMapping("/orders")
//public String orderList(OrderSearch orderSearch, Model model) {
//    List<Order> orders = orderService.findOrders(orderSearch);
//    model.addAttribute("orders", orders);
//    model.addAttribute("orderSearch", orderSearch);
//
//    return "order/orderList";
//}

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
}
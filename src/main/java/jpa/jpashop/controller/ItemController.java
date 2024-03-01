package jpa.jpashop.controller;


import jpa.jpashop.domain.item.Album;
import jpa.jpashop.domain.item.Book;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";
    }


//    @PostMapping("/items/new")
//    public String create(ItemForm form) {
//
//        Book book = new Book();
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
//        //##리팩토링. set 갈기는 것보다는 createBook 이런식으로 파라미터를 넘기는게 더 나은 설계. 나중에 수정 요망
//
//
//        itemService.saveItem(book);
//
//        return "redirect:/";
//    }


    @PostMapping("/items/new")
    public String create(ItemForm form, @RequestParam("dtype") String dtype) {
        Item item;

        switch (dtype) {
            case "B":
                Book book = new Book();
                book.setName(form.getName());
                book.setPrice(form.getPrice());
                book.setStockQuantity(form.getStockQuantity());
                if (form.getAuthor() != null) {
                    book.setAuthor(form.getAuthor());
                }
                if (form.getIsbn() != null) {
                    book.setIsbn(form.getIsbn());
                }
                item = book;
                break;
            case "A":
                Album album = new Album();
                album.setName(form.getName());
                album.setPrice(form.getPrice());
                album.setStockQuantity(form.getStockQuantity());
                if (form.getArtist() != null) {
                    album.setArtist(form.getArtist());
                }
                if (form.getEtc() != null) {
                    album.setEtc(form.getEtc());
                }

                // Album 특화 필드 설정
                item = album;
                break;
            // 다른 아이템 유형에 대한 처리
            default:
                throw new IllegalArgumentException("Unsupported item type");
        }

        itemService.saveItem(item);
        return "redirect:/";
    }



    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        Book item = (Book) itemService.findOne(itemId);
        ItemForm form = new ItemForm();

        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    //@ModelAttribute : 해당 객체의 데이터를 모델에 바인딩시키고, JSP 등에서 해당 모델을 참조
    //##혹시 모르는 부분
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") ItemForm form) {

//        Book book = new Book();

//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
//        Entity는 가급적이면 Controller에서 쓰지 않는게 좋아서 아래로 리팩토링 했음.
//        itemService.saveItem(book);

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());



        return "redirect:/items";
    }
}

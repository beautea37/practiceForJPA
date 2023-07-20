package jpa.jpashop.service;

import jpa.jpashop.domain.item.Book;
import jpa.jpashop.domain.item.Item;
import jpa.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional  //변경되는 값이니 readOnly false 줘야
    public void saveItem(Item item) {
        itemRepository.save(item);
    }


    //변경감지 기능
    //영속적인 값은 두고 준영속인 아이템들은 업데이트 해주.
    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(id);      //영속적인 값인 id값 추출
        findItem.setPrice(price);    //비영속적인 애들 추출. 이런 방식으로 jpa를 활용해야 준영속, 영속 구분이 가능하며 이렇게 하면 따로 save를 호출할 필요 없음.
        findItem.setName(name);//transactional을 통해 알아서 jpa가 스위치해줌. 얘가 bestPractice고 'merge'는 후짐. book.>>뭔가 넣다가 null나오면 개노답 됨
        findItem.setStockQuantity(stockQuantity);
        //허나 'change(price, name, stockQuantity)등등으로 의미있는 메서드를 넣어야 더 깔끔한 코드, 유지보수'  ##나중에 할 것
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}

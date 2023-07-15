package jpa.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpa.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        //아이템은 처음엔 id값이 없다. 그래서 jpa의 persist를 쓰고, 그게 아닐 경우엔 merge한다.
         if (item.getId() == null) {
             em.persist(item);      //신규 등록
         } else {
             em.merge(item);        //이미 있으면 update
         }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}

package jpa.jpashop;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

//Repository : Entity같은거 찾아주는 애
@Repository
public class MemberRepository {

    //EntityManager를 빈으로 주입할 때 사용하는 어노테이션. 동시성 문제 해결을 위한 것.
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}

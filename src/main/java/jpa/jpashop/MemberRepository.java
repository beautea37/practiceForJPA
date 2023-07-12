package jpa.jpashop;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

//Repository : Entity같은거 찾아주는 애
@Repository
public class MemberRepository {

    //EntityManager를 빈으로 주입할 때 사용하는 어노테이션. 동시성 문제 해결을 위한 것.
    @PersistenceContext
    EntityManager em;


//    커멘드와 쿼리를 분리해야됨.
//    리턴값을 안 만들되 나중에 ID값을 조회해야될 수도 있기 떄문에 ID값만 넣어준거
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}

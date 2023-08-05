package jpa.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpa.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository     //spring bean으로 자동
@RequiredArgsConstructor
public class MemberRepositoryOld {


    private final EntityManager em;

    //member에 em속성 부여
    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    
    //jpql 사용
    public List<Member> findAll() {

        return em.createQuery("select m from Member m", Member.class)
        .getResultList();
        //sql은 테이블을 대상으로 쿼리를 하지만 위 코드는 entity객체를 가지고 쿼리친다.
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}

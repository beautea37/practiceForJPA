package jpa.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;


    // #오류 있나???
    @Test
    @Transactional      //Transactional을 통해 ~~~  & 롤백하기 때문에 다 삭제된다.
    @Rollback(false)    //Rollback false하면 문제 없어짐.
//    @Rollback(false)
    public void testMember() {

        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername())        ;
        //JPA 엔티티 동일성 보장. 영속성이 같기 때문에
        //findMember == member
        Assertions.assertThat(findMember).isEqualTo(member);
    }


}
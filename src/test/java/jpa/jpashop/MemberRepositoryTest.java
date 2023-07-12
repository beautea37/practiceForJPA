package jpa.jpashop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void saveTest() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("memberA");

        // When
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        // Then
//       Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//       Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

//        Assertions.assertEquals(member.getId(), findMember.getId());
//        Assertions.assertEquals(member.getUsername(), findMember.getUsername());

        org.assertj.core.api.Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    }

    @Test
    public void findTest() {
        // Given
        Member member = new Member();
        member.setUsername("NameTest");
        memberRepository.save(member);

        // When
        Member foundMember = memberRepository.find(member.getId());

        // Then
        assertNotNull(foundMember);
        assertEquals(member.getId(), foundMember.getId());
        assertEquals(member.getUsername(), foundMember.getUsername());
    }

}
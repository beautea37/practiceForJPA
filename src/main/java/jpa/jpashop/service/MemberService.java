package jpa.jpashop.service;

import jpa.jpashop.domain.Member;
import jpa.jpashop.repository.MemberRepository;
import jpa.jpashop.repository.MemberRepositoryOld;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)   //JPA의 데이터 변경 등은 TRANSACTIONAL 안에서 돌아가야 한다.
//Transactional은 디포로트와 리드온리가 있는데 Memberservice는 ReadOnly가 더 많아서 디폴트값을 readOnly로 주었다. 그렇기 때문에 여기는 다 readonly가 default임.
//그리고 lazy로딩 시킬라면 또 필요하긴 함
@RequiredArgsConstructor //final 필드만 가지고 생성자 만들어줌.
public class MemberService {

//    private final MemberRepositoryOld memberRepository;
    private final MemberRepository memberRepository;
    //회원가입
    @Transactional      //얘는 readOnly로 주면 안되기 때문에 Tranactional을 다시 부여해준 것. 즉, readOnly = false인 상황
    public Long join(Member member) {
        validateDuplicateMember(member);        //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    
    //중복 회원 조회
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        //Member를 세서 0보다 크면 빠꾸 방식도 가능. 그리고 Unique제약조건으로 설계해야 동시에 가입 시 문제가 안 생길 수 있음. ##나중에 완성도 있게 다듬기
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}


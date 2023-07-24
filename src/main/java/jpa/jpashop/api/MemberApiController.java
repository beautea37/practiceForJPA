package jpa.jpashop.api;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jpa.jpashop.domain.Member;
import jpa.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.spec.PSSParameterSpec;

@RestController     //@controller, @responseBody. 눌러봐 //데이터 공유할 때 쓰는거
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    //Entity를 직접 파라미터로 받으면 나중에 다른 쪽에서 Entity명을 변경하거나 할 때 큰 장애가 발생하니
    //    DTO를 이용해 받는 것이 좋다.
    @PostMapping("/api/v1/members")
    //@RequestBody : JSON으로 온 body를 member에 넣어줌
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    //이게 안전 버전
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }
    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}

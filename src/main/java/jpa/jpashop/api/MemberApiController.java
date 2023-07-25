package jpa.jpashop.api;

import com.p6spy.engine.logging.P6LogLoadableOptions;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jpa.jpashop.domain.Member;
import jpa.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.spec.PSSParameterSpec;
import java.util.List;
import java.util.stream.Collectors;

@RestController     //@controller, @responseBody. 눌러봐 //데이터 공유할 때 쓰는거
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    //이렇게 하지 말라고 쫌!!! Entity 노출은 하면 안된다!
    //JsonIgnore쓰면 Entity가 퓨어한 상태가 되지 않기 때문에 그 것도 문제이고
//    Entity가 변경되면 api스펙 자체가 변하기 때문에 하면 안됨
    @GetMapping("api/{id}/members")
    public List<Member> memberV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream().map(m -> new MemberDto(m.getName()))
                .toList();

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
//        private int count; 이런식으로 추가해서 내가 api스펙 변경 없이 원하는 것들만 추출할 수 있음
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    ////////////////////////////////


    //Entity를 직접 파라미터로 받으면 나중에 다른 쪽에서 Entity명을 변경하거나 할 때 큰 장애가 발생하니
    //DTO를 이용해 받는 것이 좋다.
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

    @PutMapping("api/v2/members/{id}")
    //Request, Response DTO를 별도로 만든다 이렇게
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }




    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
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

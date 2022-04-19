package com.jpabook.jpashop.api

import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

@RestController
class MemberApiController(val memberService: MemberService) {

    @PostMapping("/api/v1/members")
    fun saveMemberV1(@RequestBody @Valid member: Member): CreateMemberResponse {
        val id = memberService.join(member)
        return CreateMemberResponse(id = id)
    }

    @PostMapping("/api/v2/members")
    fun saveMemberV2(@RequestBody @Valid createMemberRequest: CreateMemberRequest): CreateMemberResponse {

        val member = Member()
        member.name = createMemberRequest.name

        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    @PutMapping("/api/v2/members/{id}")
    fun updateMemberV2(@PathVariable("id") id: Long, @RequestBody @Valid updateMemberRequest: UpdateMemberRequest): UpdateMemberResponse {
        memberService.update(id, updateMemberRequest.name)

        val findMember = memberService.findMember(id)

        return UpdateMemberResponse(findMember?.id, findMember?.name)
    }

    @GetMapping("/api/v1/members")
    fun memberV1(): List<Member> {
        return memberService.findMembers()
    }

    @GetMapping("/api/v2/members")
    fun memberV2(): Result<MutableList<MemberDto>> {
        val findMembers = memberService.findMembers()
        val collect = findMembers.stream().map { MemberDto(it.name) }.collect(Collectors.toList())

        return Result(collect)
    }

    data class Result<T>(
        val data: T
    )

    data class MemberDto(
        val name: String?
    )

    data class CreateMemberRequest(
        @NotEmpty
        val name: String
    )

    data class UpdateMemberRequest(
        val name: String
    )

    data class UpdateMemberResponse(
        val id: Long?,
        val name: String?
    )

    data class CreateMemberResponse(
        val id: Long
    )


}

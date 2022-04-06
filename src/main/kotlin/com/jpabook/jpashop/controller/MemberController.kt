package com.jpabook.jpashop.controller

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class MemberController(private val memberService: MemberService) {

    @GetMapping("/members/new")
    fun createForm(model: Model): String {
        model.addAttribute("memberForm", MemberForm());
        return "members/createMemberForm"
    }

    @PostMapping("/members/new")
    fun create(@Valid memberForm: MemberForm, bindingResult: BindingResult): String {

        if (bindingResult.hasErrors()) {
            return "members/createMemberForm"
        }

        val address = Address(city = memberForm.city, street = memberForm.street, zipcode = memberForm.zipcode)

        val member = Member()
        member.name = memberForm.name
        member.address = address

        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model): String {
        val members = memberService.findMembers()
        model.addAttribute("members", members)
        return "members/memberList"
    }

}

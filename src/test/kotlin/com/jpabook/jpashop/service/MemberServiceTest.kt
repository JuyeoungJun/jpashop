package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.repository.MemberRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class MemberServiceTest {

    @Autowired lateinit var memberService: MemberService
    @Autowired lateinit var memberRepository: MemberRepository

    @Transactional
    @Test
    fun `회원가입`() {
        // given
        val member = Member()
        member.name = "kim"

        // when
        val savedId = memberService.join(member)

        // then
        Assertions.assertEquals(member, memberRepository.findOne(savedId))
    }

    @Test
    @Transactional
    fun `중복 회원 예외`() {
        // given
        val member1 = Member()
        member1.name = "kim"

        val member2 = Member()
        member2.name = "kim"

        val saveId = memberService.join(member1)

        // when // then
        Assertions.assertThrows(IllegalStateException::class.java) {
            memberService.join(member2)
        }
    }
}

package com.jpabook.jpashop

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class HelloControllerTest(@Autowired var memberRepository: MemberRepository) {

    @Test
    @Transactional
    fun testMember() {
        // given
        val member = Member()
        member.userName = "memberA"

        // when
        var saveId = memberRepository.save(member)
        val find = memberRepository.find(saveId)

        // then
        Assertions.assertEquals(find?.userName, member.userName)
        Assertions.assertEquals(saveId, find?.id)

    }
}

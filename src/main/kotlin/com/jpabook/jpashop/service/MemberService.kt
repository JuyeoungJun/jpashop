package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(private val memberRepository: MemberRepository) {

    // 회원 가입
    @Transactional
    fun join(member: Member): Long {
        validateDuplicateMember(member)
        memberRepository.save(member)
        return member.id!!
    }

    private fun validateDuplicateMember(member: Member) {
        if (memberRepository.findByName(member.name).isNotEmpty()) throw IllegalStateException("이미 존재하는 회원입니다.")
    }

    // 회원 전체 조회
    fun findMembers(): List<Member> {
        return memberRepository.findAll();
    }

    fun findMember(memberId: Long): Member? {
        return memberRepository.findOne(memberId)
    }
}
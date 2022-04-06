package com.jpabook.jpashop.controller

import javax.validation.constraints.NotEmpty

data class MemberForm(
    @field:NotEmpty(message = "회원 이름은 필수 입니다.")
    var name: String? = null,
    var city: String? = null,
    var street: String? = null,
    var zipcode: String? = null
)

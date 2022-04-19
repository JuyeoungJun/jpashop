package com.jpabook.jpashop.domain

import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
class Member(
    name: String? = null,
    address: Address? = null,
) {
    @Id @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null

    @NotEmpty
    var name: String? = name

    @Embedded
    var address: Address? = address

    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = mutableListOf()


}

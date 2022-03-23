package com.jpabook.jpashop.domain

import javax.persistence.*

@Entity
class Member(
    name: String? = null,
    address: Address? = null,
) {
    @Id @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null

    var name: String? = name

    @Embedded
    var address: Address? = address

    @OneToMany(mappedBy = "member")
    val orders: MutableList<Order> = mutableListOf()


}

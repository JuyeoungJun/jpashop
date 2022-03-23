package com.jpabook.jpashop.domain

import javax.persistence.*
import javax.persistence.FetchType.*

@Entity
class Delivery(
    order: Order? = null,
    address: Address? = null,
    status: DeliveryStatus? = null
) {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    var id: Long? = null

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    var order: Order? = order

    @Embedded
    var address: Address? = address

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus? = status

}

package com.jpabook.jpashop.domain

import javax.persistence.*
import javax.persistence.FetchType.*

@Entity
class OrderItem(
    item: Item,
    order: Order,
    orderPrice: Int? = null,
    count: Int? = null
) {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    var id: Long? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    var item: Item = item

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    var order: Order = order

    var orderPrice: Int? = orderPrice
    var count: Int? = count
}

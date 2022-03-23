package com.jpabook.jpashop.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.*

@Entity
@Table(name = "orders")
class Order(
    member: Member? = null,
    delivery: Delivery? = null,
    orderDate: LocalDateTime? = null,
    status: OrderStatus? = null
) {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    var id: Long? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = member
        set(member) {
            field = member
            field?.orders?.add(this)
        }

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: MutableList<OrderItem> = mutableListOf()

    @OneToOne(fetch = LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery? = delivery
        set(delivery) {
            field = delivery
            field?.order = this
        }


    var orderDate: LocalDateTime? = orderDate

    @Enumerated(EnumType.STRING)
    var status: OrderStatus? = status

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }
}

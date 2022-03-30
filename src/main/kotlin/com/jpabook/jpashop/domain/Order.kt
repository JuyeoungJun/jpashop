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

    fun cancel() {
        if (delivery?.status == DeliveryStatus.COMP) {
            throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
        }

        this.status = OrderStatus.CANCEL
        orderItems.forEach {
            it.cancel()
        }
    }

    fun getTotalPrice(): Int = orderItems.sumOf { it.getTotalPrice() }

    companion object {
        fun createOrder(member: Member?, delivery: Delivery?, vararg orderItems: OrderItem): Order =
            with(Order()) {
                this.member = member
                this.delivery = delivery
                orderItems.forEach {
                    this.addOrderItem(it)
                }
                this.status = OrderStatus.ORDER
                this.orderDate = LocalDateTime.now()
                return this
            }
    }
}

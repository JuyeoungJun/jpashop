package com.jpabook.jpashop.domain

import lombok.AccessLevel
import lombok.NoArgsConstructor
import javax.persistence.*
import javax.persistence.FetchType.*

@Entity
class OrderItem(
    item: Item? = null,
    order: Order? = null,
    orderPrice: Int = 0,
    count: Int = 0
) {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    var id: Long? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = item

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = order

    var orderPrice: Int = orderPrice
    var count: Int = count

    fun cancel() {
        item?.addStock(count)
    }

    fun getTotalPrice(): Int {
        return orderPrice * count
    }

    companion object {

        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem = with(OrderItem()) {
            this.item = item
            this.orderPrice = orderPrice
            this.count = count

            item.removeStock(count)
            return this
        }
    }
}

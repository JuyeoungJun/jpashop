package com.jpabook.jpashop.repository

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.domain.OrderStatus
import java.time.LocalDateTime

data class OrderSimpleQueryDto(
    var orderId: Long?,
    var name: String?,
    var orderDate: LocalDateTime?,
    var orderStatus: OrderStatus?,
    var address: Address?
) {
    constructor(order: Order): this(orderId = order.id, name = order.member?.name, orderDate = order.orderDate, orderStatus = order.status, address = order.delivery?.address)

}

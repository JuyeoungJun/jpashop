package com.jpabook.jpashop.domain

class OrderSearch(
    memberName: String? = null,
    orderStatus: OrderStatus? = null
) {

    var memberName: String? = memberName
    var orderStatus: OrderStatus? = orderStatus
}

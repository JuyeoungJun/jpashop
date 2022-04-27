package com.jpabook.jpashop.api

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.domain.OrderSearch
import com.jpabook.jpashop.domain.OrderStatus
import com.jpabook.jpashop.repository.OrderRepository
import com.jpabook.jpashop.repository.OrderSimpleQueryDto
import com.jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.stream.Collectors

@RestController
class OrderSimpleApiController(
    private val orderRepository: OrderRepository,
    private val orderSimpleQueryRepository: OrderSimpleQueryRepository
) {

    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Order>? {
        val all = orderRepository.findAllByString(OrderSearch())
        for (order in all) {
            order.member?.name
            order.delivery?.address?.city
        }
        return all
    }

    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): List<SimpleOrderDto> {
        val all = orderRepository.findAllByString(OrderSearch())
        val result = all.stream().map { SimpleOrderDto(it) }.collect(Collectors.toList())

        return result
    }

    @GetMapping("/api/v3/simple-orders")
    fun ordersV3(): List<SimpleOrderDto> {
        val all = orderRepository.findAllWithMemberDelivery()
        val result = all.stream().map { SimpleOrderDto(it) }.collect(Collectors.toList())

        return result
    }

    @GetMapping("/api/v4/simple-orders")
    fun ordersV4(): List<OrderSimpleQueryDto> {

        return orderSimpleQueryRepository.findOrderDtos()
    }

    data class SimpleOrderDto(
        var orderId: Long?,
        var name: String?,
        var orderDate: LocalDateTime?,
        var orderStatus: OrderStatus?,
        var address: Address?
    ) {
        constructor(order: Order): this(orderId = order.id, name = order.member?.name, orderDate = order.orderDate, orderStatus = order.status, address = order.delivery?.address)

    }
}

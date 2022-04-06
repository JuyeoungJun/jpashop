package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Delivery
import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.domain.OrderItem
import com.jpabook.jpashop.domain.OrderSearch
import com.jpabook.jpashop.repository.ItemRepository
import com.jpabook.jpashop.repository.MemberRepository
import com.jpabook.jpashop.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService(private val orderRepository: OrderRepository, private val memberRepository: MemberRepository, private val itemRepository: ItemRepository) {

    /**
     * 주문
     */
    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long? {

        // 엔티티 조회
        val member = memberRepository.findOne(memberId) ?: throw IllegalStateException()
        val item = itemRepository.findOne(itemId) ?: throw IllegalStateException()

        // 배송정보 생성
        val delivery = Delivery()
        delivery.address = member.address

        // 주문상품 생성
        val orderItem = OrderItem.createOrderItem(item, item.price, count)

        // 주문 생성
        val order = Order.createOrder(member, delivery, orderItem)

        // 주문 저장
        orderRepository.save(order)
        return order.id
    }

    /**
     * 주문 취소
     */
    @Transactional
    fun cancelOrder(orderId: Long) {
        // 주문 엔티티 조회
        val order = orderRepository.findOne(orderId)
        // 주문 취소
        order.cancel()
    }

    /**
     * 주문 검색
     */
    fun findOrders(orderSearch: OrderSearch): List<Order> = orderRepository.findAllByCriteria(orderSearch)
}

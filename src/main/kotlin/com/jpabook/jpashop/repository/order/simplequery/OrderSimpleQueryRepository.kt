package com.jpabook.jpashop.repository.order.simplequery

import com.jpabook.jpashop.repository.OrderSimpleQueryDto
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderSimpleQueryRepository(private val entityManager: EntityManager) {

    fun findOrderDtos(): List<OrderSimpleQueryDto> {
        return entityManager.createQuery(
            """
                select new com.jpabook.jpashop.repository.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)
                from Order o
                join o.member m
                join o.delivery d
            """.trimIndent(), OrderSimpleQueryDto::class.java
        ).resultList
    }
}

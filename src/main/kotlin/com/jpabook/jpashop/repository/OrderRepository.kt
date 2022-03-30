package com.jpabook.jpashop.repository

import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.domain.OrderSearch
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Predicate
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Repository
class OrderRepository(private val entityManager: EntityManager) {

    fun save(order: Order) = entityManager.persist(order)


    fun findOne(id: Long): Order = entityManager.find(Order::class.java, id)

    fun findAll(orderSearch: OrderSearch): List<Order> = entityManager.createQuery(
        """
            |select o from Order o join o.member m
            |where o.status = :status 
            |and m.name like :name
        """.trimMargin(), Order::class.java
    )
        .setParameter("status", orderSearch.orderStatus)
        .setParameter("name", orderSearch.memberName)
        .setMaxResults(1000)
        .resultList


    fun findAllByCriteria(orderSearch: OrderSearch): MutableList<Order>? {
        val criteriaBuilder = entityManager.criteriaBuilder
        val createQuery = criteriaBuilder.createQuery(Order::class.java)
        val o = createQuery.from(Order::class.java)
        val m = o.join<JvmType.Object, JvmType.Object>("member", JoinType.INNER)

        val criteria = mutableListOf<Predicate>()

        orderSearch.orderStatus?.let {
            val status = criteriaBuilder.equal(o.get<String>("status"), orderSearch.orderStatus)
            criteria.add(status)
        }

        if (!orderSearch.memberName.isNullOrEmpty()) {
            val name = criteriaBuilder.like(m.get<String>("name"), "%${orderSearch.memberName}%")
            criteria.add(name)
        }

        createQuery.where(criteriaBuilder.and(*criteria.toTypedArray()))
        val query = entityManager.createQuery(createQuery).setMaxResults(1000)
        return query.resultList
    }
}

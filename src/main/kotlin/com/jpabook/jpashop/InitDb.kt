package com.jpabook.jpashop

import com.jpabook.jpashop.domain.*
import com.jpabook.jpashop.domain.item.Book
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@Component
class InitDb(
    private val initService: InitService
) {
    @PostConstruct
    fun init() {
        initService.dbInit1()
        initService.dbInit2()
    }

}

@Component
@Transactional
class InitService(
    private val entityManager: EntityManager
) {
    fun dbInit1() {
        val member = createMember("userA", Address("서울", "1", "1111"))
        entityManager.persist(member)

        val book1 = createBook("JPA1 BOOK", 10000, 100)
        entityManager.persist(book1)

        val book2 = createBook("JPA2 BOOK", 10000, 100)
        entityManager.persist(book2)

        val orderItem1 = OrderItem.createOrderItem(book1, 10000, 1)
        val orderItem2 = OrderItem.createOrderItem(book2, 20000, 2)

        val delivery = Delivery()
        delivery.address = member.address

        val order = Order.createOrder(member, delivery, orderItem1, orderItem2)
        entityManager.persist(order)
    }

    private fun createMember(name: String, address: Address): Member {
        val member = Member()
        member.name = name
        member.address = address
        return member
    }

    fun dbInit2() {
        val member = createMember("userB", Address("진주", "2", "2222"))
        entityManager.persist(member)

        val book1 = createBook("SPRING1 BOOK", 10000, 100)
        entityManager.persist(book1)

        val book2 = createBook("SPRING2 BOOK", 10000, 100)
        entityManager.persist(book2)

        val orderItem1 = OrderItem.createOrderItem(book1, 10000, 1)
        val orderItem2 = OrderItem.createOrderItem(book2, 20000, 2)

        val delivery = Delivery()
        delivery.address = member.address

        val order = Order.createOrder(member, delivery, orderItem1, orderItem2)
        entityManager.persist(order)
    }

    private fun createBook(name: String, price: Int, stockQuantity: Int): Book {
        val book1 = Book()
        book1.name = name
        book1.price = price
        book1.stockQuantity = stockQuantity
        return book1
    }
}

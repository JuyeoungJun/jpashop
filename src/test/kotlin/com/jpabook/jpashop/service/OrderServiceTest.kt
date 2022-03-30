package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Address
import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.domain.OrderStatus
import com.jpabook.jpashop.domain.item.Book
import com.jpabook.jpashop.excetpion.NotEnoughStockException
import com.jpabook.jpashop.repository.OrderRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Test
    fun `상품주문`() {
        // given
        val member = createMember()

        val book = createBook("시골 JPA", 10000, 10)

        val orderCount = 2

        // when
        val orderId = orderService.order(member.id!!, book.id!!, orderCount)

        // then
        val getOrder = orderRepository.findOne(orderId!!)

        assertEquals(OrderStatus.ORDER, getOrder.status)
        assertEquals(1, getOrder.orderItems.size)
        assertEquals(10000 * orderCount, getOrder.getTotalPrice())
        assertEquals(8, book.stockQuantity)
    }


    @Test
    fun `상품주문 재고수량초과`() {
        // given
        val member = createMember()
        val item = createBook("시골 JPA", 10000, 10)

        val orderCount = 11


        // when // then
        Assertions.assertThrows(NotEnoughStockException::class.java) {
            orderService.order(member.id!!, item.id!!, orderCount)
        }
    }

    @Test
    fun `주문취소`() {
        // given
        val member = createMember()
        val item = createBook("시골 JPA", 10000, 10)

        val orderCount = 2;

        val orderId = orderService.order(member.id!!, item.id!!, orderCount)

        // when
        orderService.cancelOrder(orderId!!)

        // then
        val getOrder = orderRepository.findOne(orderId!!)

        assertEquals(OrderStatus.CANCEL, getOrder.status)
        assertEquals(10, item.stockQuantity)

    }

    private fun createMember(): Member {
        val member = Member()
        member.name = "회원1"
        member.address = Address("서울", "강가", "123-123")
        entityManager.persist(member)
        return member
    }

    private fun createBook(name: String, price: Int, stockQuantity: Int): Book {
        val book = Book()
        book.name = name
        book.price = price
        book.stockQuantity = stockQuantity
        entityManager.persist(book)
        return book
    }

}


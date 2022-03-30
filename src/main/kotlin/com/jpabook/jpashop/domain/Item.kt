package com.jpabook.jpashop.domain

import com.jpabook.jpashop.excetpion.NotEnoughStockException
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
open abstract class Item(
    name: String? = null,
    price: Int = 0,
    stockQuantity: Int = 0
) {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    var id: Long? = null

    var name: String? = name
    var price: Int = price
    var stockQuantity: Int = stockQuantity

    @ManyToMany(mappedBy = "items")
    val categories: MutableList<Category> = mutableListOf()

    fun addStock(quantity: Int) {
        stockQuantity = stockQuantity.plus(quantity)
    }

    fun removeStock(quantity: Int) {
        val restStock = stockQuantity - quantity
        if (restStock < 0) {
            throw NotEnoughStockException("need more stock");
        }

        stockQuantity = restStock
    }
}

package com.jpabook.jpashop.domain

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
open class Item(
    name: String? = null,
    price: Int? = null,
    stockQuantity: Int? = null
) {
    @Id @GeneratedValue
    @Column(name = "item_id")
    var id: Long? = null

    var name: String? = name
    var price: Int? = price
    var stockQuantity: Int? = stockQuantity

    @ManyToMany(mappedBy = "items")
    val categories: MutableList<Category> = mutableListOf()

}

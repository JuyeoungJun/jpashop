package com.jpabook.jpashop.domain

import javax.persistence.*
import javax.persistence.FetchType.*

@Entity
class Category(
    name: String? = null,
    parent: Category? = null,
) {

    @Id @GeneratedValue
    @Column(name = "category_id")
    var id: Long? = null

    var name: String? = name

    @ManyToMany
    @JoinTable(name = "category_item", joinColumns = [JoinColumn(name = "category_id")], inverseJoinColumns = [JoinColumn(name = "item_id")])
    val items: MutableList<Item> = mutableListOf()

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category? = parent

    @OneToMany(mappedBy = "parent")
    var child: MutableList<Category> = mutableListOf()

    fun addChildCategory(child: Category) {
        this.child.add(child)
        child.parent = this
    }
}




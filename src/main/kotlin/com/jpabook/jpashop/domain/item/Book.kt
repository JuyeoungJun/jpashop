package com.jpabook.jpashop.domain.item

import com.jpabook.jpashop.domain.Item
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("B")
class Book(
    var author: String? = null,
    var isbn: String? = null
) : Item() {
}

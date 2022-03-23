package com.jpabook.jpashop.domain.item

import com.jpabook.jpashop.domain.Item
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("A")
class Album(
    var artist: String? = null,
    var etc: String? = null
) : Item() {
}

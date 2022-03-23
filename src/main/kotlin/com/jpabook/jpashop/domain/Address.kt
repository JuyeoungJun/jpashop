package com.jpabook.jpashop.domain

import javax.persistence.Embeddable

@Embeddable
class Address(
    city: String? = null,
    street: String? = null,
    zipcode: String? = null,
) {
    var city: String? = city
    var street: String? = street
    var zipcode: String? = zipcode
}

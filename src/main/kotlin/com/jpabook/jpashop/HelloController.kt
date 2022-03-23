package com.jpabook.jpashop

import com.jpabook.jpashop.domain.Item
import com.jpabook.jpashop.domain.Member
import com.jpabook.jpashop.domain.Order
import com.jpabook.jpashop.domain.OrderItem
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HelloController() {

    @GetMapping("hello")
    fun hello(model: Model): String {

        model.addAttribute("data", "hello!!!")
        return "hello"
    }

}

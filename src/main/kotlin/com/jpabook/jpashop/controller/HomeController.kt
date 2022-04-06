package com.jpabook.jpashop.controller

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController {

    private val log = LoggerFactory.getLogger("HomeController")

    @RequestMapping("/")
    fun home(): String {
        log.info("home controller")
        return "home"
    }
}

package com.jpabook.jpashop.controller

import com.jpabook.jpashop.domain.item.Book
import com.jpabook.jpashop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class ItemController(val itemService: ItemService) {

    @GetMapping("/items/new")
    fun createForm(model: Model): String {
        model.addAttribute("form", BookForm())
        return "items/createItemForm"
    }

    @PostMapping("/items/new")
    fun create(bookForm: BookForm): String {
        val book = Book().apply {
            name = bookForm.name
            price = bookForm.price ?: 0
            author = bookForm.author
            isbn = bookForm.isbn
            stockQuantity = bookForm.stockQuantity ?: 0
        }


        itemService.saveItem(book)
        return "redirect:/items"
    }

    @GetMapping("/items")
    fun list(model: Model): String {
        val items = itemService.findItems()
        model.addAttribute("items", items)
        return "items/itemList"
    }

    @GetMapping("/items/{itemId}/edit")
    fun updateItemForm(@PathVariable("itemId") itemId: Long, model: Model): String {
        val item = itemService.findOne(itemId) as Book

        val bookForm = BookForm().apply {
            id = item.id
            isbn = item.isbn
            name = item.name
            price = item.price
            stockQuantity = item.price
            author = item.author
        }

        model.addAttribute("form", bookForm)
        return "items/updateItemForm"
    }

    @PostMapping("/items/{itemId}/edit")
    fun updateItem(@PathVariable itemId: Long, @ModelAttribute("form") bookForm: BookForm): String {
//        val item = itemService.findOne(bookForm.id ?: throw Exception())
//
//        val book = Book().apply {
//            id = bookForm.id
//            name = bookForm.name
//            price = bookForm.price ?: throw Exception()
//            stockQuantity = bookForm.stockQuantity ?: throw Exception()
//            author = bookForm.author
//            isbn = bookForm.isbn
//        }

//        itemService.saveItem(book)
        itemService.updateItem(itemId, bookForm)
        return "redirect:/items"

    }

}

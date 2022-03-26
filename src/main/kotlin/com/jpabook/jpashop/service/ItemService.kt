package com.jpabook.jpashop.service

import com.jpabook.jpashop.domain.Item
import com.jpabook.jpashop.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(private val itemRepository: ItemRepository) {

    @Transactional
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    fun findItems(): List<Item> = itemRepository.findAll()

    fun findOne(itemId: Long): Item? = itemRepository.findOne(itemId)
}
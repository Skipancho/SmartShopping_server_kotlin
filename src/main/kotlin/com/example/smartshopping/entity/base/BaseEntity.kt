package com.example.smartshopping.entity.base

import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null

    var createdAt: Date? = null
    var updatedAt: Date? = null

    @PrePersist
    fun prePersist(){
        createdAt = Date()
        updatedAt = Date()
    }

    @PreUpdate
    fun preUpdate(){
        updatedAt = Date()
    }
}
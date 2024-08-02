package com.example.blogmultiplatform.data

import com.example.blogmultiplatform.models.User

interface MongoRepository {
    fun checkUserExistence(): User?
}
package com.example.blogmultiplatform.models

expect enum class Category: CategoryColor {
    Technology,
    Programming,
    Design
}

interface CategoryColor {
    val color: String
}
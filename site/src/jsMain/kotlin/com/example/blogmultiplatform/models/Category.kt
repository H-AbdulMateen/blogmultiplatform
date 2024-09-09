package com.example.blogmultiplatform.models

import kotlinx.serialization.Serializable

@Serializable
actual enum class Category(override val color: String): CategoryColor {
    Technology(color = Theme.Green.hex),
    Programming(color = Theme.Yellow.hex),
    Design(color = Theme.Purple.hex)
}
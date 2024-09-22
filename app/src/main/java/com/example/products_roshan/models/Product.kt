
package com.example.products_roshan.models

import kotlinx.serialization.Serializable

@Serializable
class Product(
    val id: Int,
    val title: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val brand: String = "", // doing this because brand attributes is not available in groceries category
    val thumbnail: String
)


@Serializable
data class Category(
    val products:List<Product>,
    val total:Int,
    val skip:Int,
    val limit:Int
)

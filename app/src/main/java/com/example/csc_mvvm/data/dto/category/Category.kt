package com.example.csc_mvvm.data.dto.category

import com.example.csc_mvvm.base.BaseModel

open class CategoryModel(
    var id: Int = -1,
    var name: String = "",
    val avatar: String = "",
    val category: CategoryModel? = null,
    var selected: Boolean = false,
) : BaseModel()

data class CategoryResponse(
    val id: Int?,
    val name: String?,
    val avatar: String?,
    val category: CategoryModel?
) : BaseModel() {
    fun toCategoryModel() = CategoryModel(
        id = id ?: -1,
        name = name.orEmpty(),
        avatar = avatar.orEmpty(),
        category = category,
        selected = false
    )
}


fun List<CategoryResponse>.toCategories() = map { it.toCategoryModel() }

fun List<*>.toCategoriesResponse(): List<CategoryResponse> {
    val categories = mutableListOf<CategoryResponse>()
    forEach {
        if (it is CategoryResponse) categories.add(it)
    }
    return categories
}



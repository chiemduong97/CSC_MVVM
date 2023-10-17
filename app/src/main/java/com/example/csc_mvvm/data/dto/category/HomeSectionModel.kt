package com.example.csc_mvvm.data.dto.category

class HomeSectionModel(
    val title: String,
    val url: String
) : CategoryModel() {
    companion object {
        val HOME_SECTION = arrayListOf(
            HomeSectionModel("Sản phẩm mới", "api/product/product_getNew.php").apply { id = 1000 },
            HomeSectionModel("Sản phẩm nổi bậc", "api/product/product_getHighLight.php").apply { id = 2000 },
            HomeSectionModel("Vừa đặt gần đây", "api/product/product_getRecent.php").apply { id = 3000 }
        ).map { it.apply { name = title } }
    }

}
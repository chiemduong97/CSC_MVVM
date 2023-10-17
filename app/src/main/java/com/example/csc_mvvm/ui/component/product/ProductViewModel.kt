package com.example.csc_mvvm.ui.component.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.dto.category.HomeSectionModel
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.ui.base.BaseCollectionViewModel
import com.example.csc_mvvm.usecase.ProductUseCase
import kotlinx.coroutines.launch

class ProductViewModel(private val productUseCase: ProductUseCase) : BaseCollectionViewModel() {

    private val _sectionsLiveData = MutableLiveData<List<HomeSectionModel>>()
    val sectionsLiveData: LiveData<List<HomeSectionModel>> get() = _sectionsLiveData

    private val _productsLiveData = MutableLiveData<Pair<Resource<List<ProductModel>>, Int>>()
    val productsLiveData: LiveData<Pair<Resource<List<ProductModel>>, Int>> get() = _productsLiveData

    private val _moreProductsLiveData = MutableLiveData<Pair<Resource<List<ProductModel>>, Int>>()
    val moreProductsLiveData: LiveData<Pair<Resource<List<ProductModel>>, Int>> = _moreProductsLiveData

    fun getSections() {
        _sectionsLiveData.value = HomeSectionModel.HOME_SECTION
    }

    fun getProductsByCategory(
        categoryId: Int,
        page: Int,
        limit: Int
    ) {
        viewModelScope.launch {
            _productsLiveData.value = Pair(Resource.Loading(), categoryId)
            productUseCase.getByCategory(categoryId, page, limit).collect {
                _productsLiveData.value = Pair(it, categoryId)
            }
        }
    }

    fun getProductsByUrl(
        categoryId: Int,
        url: String,
        page: Int,
        limit: Int
    ) {
        viewModelScope.launch {
            _productsLiveData.value = Pair(Resource.Loading(), categoryId)
            productUseCase.getByUrl(url, page, limit).collect {
                _productsLiveData.value = Pair(it, categoryId)
            }
        }
    }

    fun addToCart(productModel: ProductModel) {

    }

    fun openProductDetail(productModel: ProductModel) {

    }

    fun seeAllProducts(category: CategoryModel) {

    }
}
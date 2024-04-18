package com.example.csc_mvvm.ui.component.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.csc_mvvm.app.Preferences
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.cart.CartProductModel
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.dto.category.HomeSectionModel
import com.example.csc_mvvm.data.dto.loading.LoadingMode
import com.example.csc_mvvm.data.dto.product.ProductModel
import com.example.csc_mvvm.ui.base.BaseCollectionViewModel
import com.example.csc_mvvm.usecase.product.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val preferences: Preferences
) : BaseCollectionViewModel() {

    private val _sectionsLiveData = MutableLiveData<List<HomeSectionModel>>()
    val sectionsLiveData: LiveData<List<HomeSectionModel>> get() = _sectionsLiveData

    private val _productsLiveData = MutableLiveData<Pair<Resource<List<ProductModel>>, Int>>()
    val productsLiveData: LiveData<Pair<Resource<List<ProductModel>>, Int>> get() = _productsLiveData

    private val _moreProductsLiveData = MutableLiveData<Pair<Resource<List<ProductModel>>, Int>>()
    val moreProductsLiveData: LiveData<Pair<Resource<List<ProductModel>>, Int>> =
        _moreProductsLiveData

    private val _showDialogAddToCartLiveData = MutableLiveData<ProductModel>()
    val showDialogAddToCartLiveData: LiveData<ProductModel> get() = _showDialogAddToCartLiveData

    private val _cartLiveData = MutableLiveData<List<CartProductModel>>()
    val cartLiveData: LiveData<List<CartProductModel>> get() = _cartLiveData

    private val _openProductsLiveData = MutableLiveData<CategoryModel>()
    val openProductsLiveData: LiveData<CategoryModel> get() = _openProductsLiveData

    private val _openProductDetailLiveData = MutableLiveData<ProductModel>()
    val openProductDetailLiveData: LiveData<ProductModel> get() = _openProductDetailLiveData

    private var mCategory: CategoryModel? = null

    fun getSections() {
        _sectionsLiveData.value = HomeSectionModel.HOME_SECTION
    }

    fun getProducts(category: CategoryModel) {
        mCategory = category
        if (category is HomeSectionModel) getProductsByUrl(
            category.id,
            category.url,
            page,
            limit,
            LoadingMode.LOAD
        )
        else getProductsByCategory(category.id, page, limit, LoadingMode.LOAD)
    }

    fun getProductsByCategory(
        categoryId: Int,
        page: Int,
        limit: Int,
        loadingMode: LoadingMode
    ) {
        viewModelScope.launch {
            when (loadingMode) {
                LoadingMode.LOAD -> {
                    _productsLiveData.value = Pair(Resource.Loading(), categoryId)
                    productUseCase.getByCategory(categoryId, page, limit).collect {
                        _productsLiveData.value = Pair(it, categoryId)
                    }
                }

                LoadingMode.LOAD_MORE -> {
                    _moreProductsLiveData.value = Pair(Resource.Loading(), categoryId)
                    productUseCase.getByCategory(categoryId, page, limit).collect {
                        _moreProductsLiveData.value = Pair(it, categoryId)
                    }
                }
            }
        }
    }

    fun getProductsByUrl(
        categoryId: Int,
        url: String,
        page: Int,
        limit: Int,
        loadingMode: LoadingMode
    ) {
        viewModelScope.launch {
            when (loadingMode) {
                LoadingMode.LOAD -> {
                    _productsLiveData.value = Pair(Resource.Loading(), categoryId)
                    productUseCase.getByUrl(url, page, limit).collect {
                        _productsLiveData.value = Pair(it, categoryId)
                    }
                }

                LoadingMode.LOAD_MORE -> {
                    _moreProductsLiveData.value = Pair(Resource.Loading(), categoryId)
                    productUseCase.getByUrl(url, page, limit).collect {
                        _moreProductsLiveData.value = Pair(it, categoryId)
                        onLoadMoreComplete()
                    }
                }
            }
        }
    }

    override fun invokeLoadMore(page: Int) {
        super.invokeLoadMore(page)
        mCategory?.let {
            if (it is HomeSectionModel) getProductsByUrl(
                it.id,
                it.url,
                page,
                limit,
                LoadingMode.LOAD_MORE
            )
            else getProductsByCategory(it.id, page, limit, LoadingMode.LOAD_MORE)
        }
    }

    fun showDialogAddToCart(productModel: ProductModel) {
        _showDialogAddToCartLiveData.value = productModel
    }

    fun addToCart(cartProductModel: CartProductModel) {
        preferences.cart = preferences.cart.apply {
            cartProducts = cartProducts.apply here@{
                map {
                    if (it.product.id == cartProductModel.product.id) {
                        it.quantity += cartProductModel.quantity
                        if (it.quantity > it.product.quantity) it.quantity = it.product.quantity
                        return@here
                    }
                }
                if (cartProductModel.quantity > cartProductModel.product.quantity)
                    cartProductModel.quantity = cartProductModel.product.quantity
                add(cartProductModel)
            }
        }
        getCart()
    }

    fun getCart() {
        preferences.cart = preferences.cart.apply {
            cartProducts =
                cartProducts.filter { cartProductModel -> cartProductModel.quantity > 0 } as ArrayList<CartProductModel>
        }
        _cartLiveData.value = preferences.cart.cartProducts
    }


    fun openProductDetail(product: ProductModel) {
        _openProductDetailLiveData.value = product
    }

    fun seeAllProducts(category: CategoryModel) {
        _openProductsLiveData.value = category
    }
}
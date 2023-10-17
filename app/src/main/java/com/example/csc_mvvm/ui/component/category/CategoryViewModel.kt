package com.example.csc_mvvm.ui.component.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.app.Preferences
import com.example.csc_mvvm.ui.base.BaseViewModel
import com.example.csc_mvvm.usecase.CategoryUseCase
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryUseCase: CategoryUseCase) : BaseViewModel() {
    private val preferences by lazy { Preferences.newInstance() }
    private val _superCategoryLiveData = MutableLiveData<Resource<List<CategoryModel>>>()
    val superCategoryLiveData : LiveData<Resource<List<CategoryModel>>> get() = _superCategoryLiveData

    private val _categoryLiveData = MutableLiveData<Resource<List<CategoryModel>>>()
    val categoryLiveData : LiveData<Resource<List<CategoryModel>>> get() = _categoryLiveData

    fun getSuperCategories() {
        viewModelScope.launch {
            _superCategoryLiveData.value = Resource.Loading()
            categoryUseCase.getSuperCategories().collect {
                _superCategoryLiveData.value = it
            }
        }
    }

    fun getCategories(categoryId: Int) {
        viewModelScope.launch {
            _superCategoryLiveData.value = Resource.Loading()
            categoryUseCase.getCategories(categoryId).collect {
                _categoryLiveData.value = it
            }
        }
    }

}
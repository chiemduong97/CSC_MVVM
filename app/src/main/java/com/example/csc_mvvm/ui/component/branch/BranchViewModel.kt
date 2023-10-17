package com.example.csc_mvvm.ui.component.branch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.csc_mvvm.app.EventKey
import com.example.csc_mvvm.app.Preferences
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.example.csc_mvvm.data.dto.event.Event
import com.example.csc_mvvm.data.dto.event.ValueEvent
import com.example.csc_mvvm.ui.base.BaseCollectionViewModel
import com.example.csc_mvvm.usecase.BranchUseCase
import kotlinx.coroutines.launch

class BranchViewModel(private val branchUseCase: BranchUseCase) : BaseCollectionViewModel() {

    private val preferences by lazy { Preferences.newInstance() }

    private val _branchesLiveData = MutableLiveData<Resource<Pair<List<BranchModel>, Int>>>()
    val branchesLiveData: LiveData<Resource<Pair<List<BranchModel>, Int>>> get() = _branchesLiveData

    private val _branchLiveData = MutableLiveData<Resource<BranchModel>>()
    val branchLiveData: LiveData<Resource<BranchModel>> get() = _branchLiveData

    private val _selectBranchLiveData = MutableLiveData<ValueEvent<BranchModel>>()
    val selectBranchLiveData: LiveData<ValueEvent<BranchModel>> get() = _selectBranchLiveData

    fun getBranches() {
        viewModelScope.launch {
            _branchesLiveData.value = Resource.Loading()
            branchUseCase.getBranches().collect {
                _branchesLiveData.value = it
            }
        }
    }

    fun getBranchFromLocal() {
        viewModelScope.launch {
            branchUseCase.getBranchFromLocal().collect {
                _branchLiveData.value = it
            }
        }
    }

    fun selectBranch(branch: BranchModel) {
        preferences.branch = branch
        preferences.cart = preferences.cart.apply {
            branch_lat = branch.lat
            branch_lng = branch.lng
            branch_address = branch.address
        }
        _selectBranchLiveData.value = ValueEvent(EventKey.CHANGE_BRANCH, branch)
    }

}
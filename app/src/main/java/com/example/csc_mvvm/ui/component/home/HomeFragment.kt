package com.example.csc_mvvm.ui.component.home

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.csc_mvvm.R
import com.example.csc_mvvm.app.EventKey
import com.example.csc_mvvm.app.MAX_ITEM_CATEGORY
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.data.dto.category.HomeSectionModel
import com.example.csc_mvvm.data.dto.profile.ProfileModel
import com.example.csc_mvvm.databinding.FragmentHomeBinding
import com.example.csc_mvvm.ui.base.BaseFragment
import com.example.csc_mvvm.ui.base.ViewModelFactory
import com.example.csc_mvvm.ui.component.branch.BranchActivity
import com.example.csc_mvvm.ui.component.branch.BranchViewModel
import com.example.csc_mvvm.ui.component.category.CategoryViewModel
import com.example.csc_mvvm.ui.component.category.adapter.CategoriesAdapter
import com.example.csc_mvvm.ui.component.product.ProductViewModel
import com.example.csc_mvvm.ui.component.product.adapter.ProductSectionsAdapter
import com.example.csc_mvvm.ui.component.profile.UserViewModel
import com.example.csc_mvvm.utils.gone
import com.example.csc_mvvm.utils.show
import com.google.gson.Gson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : BaseFragment() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentHomeBinding::inflate
    private val binding by lazy { binding<FragmentHomeBinding>() }
    private val userViewModel: UserViewModel by activityViewModels { ViewModelFactory() }
    private val branchViewModel: BranchViewModel by activityViewModels { ViewModelFactory() }
    private val categoryViewModel: CategoryViewModel by activityViewModels { ViewModelFactory() }
    private val productViewModel: ProductViewModel by activityViewModels { ViewModelFactory() }
    private lateinit var activityResultLauncherBranch: ActivityResultLauncher<Intent>

    override fun observeViewModel() {
        userViewModel.userLiveData.observe(viewLifecycleOwner) { status ->
            showProfile(status)
        }
        branchViewModel.branchLiveData.observe(viewLifecycleOwner) { status ->
            showBranch(status)
        }
        categoryViewModel.superCategoryLiveData.observe(viewLifecycleOwner) { status ->
            showSuperCategories(status)
        }
        observeErrorMessage(categoryViewModel.errorLiveData)
        productViewModel.sectionsLiveData.observe(viewLifecycleOwner) { sections ->
            showHomeSections(sections)
        }
    }

    override fun bindData() {
        userViewModel.getUserFromLocal()
        branchViewModel.getBranchFromLocal()
        categoryViewModel.getSuperCategories()
        productViewModel.getSections()
    }

    override fun bindEvent() {
        activityResultLauncherBranch =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    result.data?.let {
                        val branchResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            it.getSerializableExtra(EventKey.CHANGE_BRANCH, BranchModel::class.java)
                        } else {
                            it.getSerializableExtra(EventKey.CHANGE_BRANCH) as? BranchModel
                        }
                        branchResult?.run { showBranch(Resource.Success(this)) }
                    }
                }
            }
        binding.apply {
            activity?.let { activity ->
                rllChangeBranch.setOnClickListener {
                    activityResultLauncherBranch.launch(BranchActivity.newInstance(activity))
                }
            }

        }
    }

    private fun showProfile(status: Resource<ProfileModel>) {
        when (status) {
            is Resource.Success -> {
                binding.apply {
                    status.data?.let {
                        val format: DateFormat = SimpleDateFormat("HH", Locale.getDefault())
                        val greet =
                            when(format.format(Calendar.getInstance().time).toInt()) {
                                in 4..10 -> getString(R.string.greet_morning)
                                in 11..12 -> getString(R.string.greet_lunch)
                                in 13..17 -> getString(R.string.greet_evening)
                                else -> getString(R.string.greet_night)
                            }
                        tvGreet.text = getString(R.string.greet_description, greet, it.fullname)
                        Glide.with(this@HomeFragment)
                            .asBitmap()
                            .placeholder(R.drawable.avatar_default)
                            .load(it.avatar)
                            .into(viewIcon)
                    }
                }
            }

            else -> {}
        }
    }

    private fun showBranch(status: Resource<BranchModel>) {
        when (status) {
            is Resource.Success -> {
                binding.apply {
                    status.data?.let {
                        tvBranchName.text = it.name
                        tvBranchAddress.text = it.address
                    }
                }
            }
        }
    }

    private fun showSuperCategories(status: Resource<List<CategoryModel>>) {
        when (status) {
            is Resource.Loading -> showLoading()
            is Resource.Success -> {
                hideLoading()
                binding.apply {
                    status.data?.let { categories ->
                        recyclerViewCategory.show()
                        recyclerViewCategory.layoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
                        val adapter = CategoriesAdapter(categories.take(MAX_ITEM_CATEGORY), {

                        }, {

                        })
                        recyclerViewCategory.adapter = adapter
                    }
                }
            }
            is Resource.DataError -> {
                hideLoading()
                binding.recyclerViewCategory.gone()
                status.errorCode?.let { categoryViewModel.showError(it) }
            }
        }
    }

    private fun showHomeSections(sections: List<HomeSectionModel>) {
        binding.apply {
            recyclerViewSection.show()
            val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerViewSection.layoutManager = manager
            recyclerViewSection.adapter = ProductSectionsAdapter(viewLifecycleOwner, sections, productViewModel)
        }
    }

    private fun showLoading() {
        binding.swipeRefresh.isRefreshing = true
    }

    private fun hideLoading() {
        binding.swipeRefresh.apply {
            if (isRefreshing) isRefreshing = false
        }
    }

    private fun observeErrorMessage(message: LiveData<String>) {
        message.observe(this) {
            showToastMessage(it)
        }
    }
}
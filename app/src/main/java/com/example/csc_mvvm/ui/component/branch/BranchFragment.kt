package com.example.csc_mvvm.ui.component.branch

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.csc_mvvm.data.Resource
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.example.csc_mvvm.databinding.FragmentBranchBinding
import com.example.csc_mvvm.ui.base.BaseCollectionFragment
import com.example.csc_mvvm.ui.base.ViewModelFactory
import com.example.csc_mvvm.ui.component.branch.adapter.BranchesAdapter
import com.example.csc_mvvm.utils.gone
import com.example.csc_mvvm.utils.show

class BranchFragment: BaseCollectionFragment<BranchViewModel>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentBranchBinding::inflate
    private val binding by lazy { binding<FragmentBranchBinding>() }
    override val viewModel: BranchViewModel by activityViewModels { ViewModelFactory() }
    private val mLayoutManager by lazy { LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) }
    private val mBranches = mutableListOf<BranchModel>()

    override fun observeViewModel() {
        viewModel.branchesLiveData.observe(viewLifecycleOwner) {
            showBranches(it)
        }
        viewModel.selectBranchLiveData.observe(viewLifecycleOwner) {
            activity?.run {
                setResult(Activity.RESULT_OK, Intent().apply { putExtra(it.key, it.value) })
                finish()
            }
        }
    }

    override fun onRefresh() {
        viewModel.getBranches()
    }

    override fun bindData() {
        viewModel.getBranches()
    }

    override fun bindEvent() {
        binding.apply {
            imvBack.setOnClickListener { activity?.finish() }
        }
    }

    private fun showBranches(status: Resource<Pair<List<BranchModel>, Int>>) {
        when (status) {
            is Resource.Loading -> showLoading()
            is Resource.Success -> {
                hideLoading()
                binding.apply {
                    status.data?.let { branches ->
                        mBranches.addAll(branches.first)
                        imvEmpty.gone()
                        recyclerView.run {
                            show()
                            layoutManager = mLayoutManager
                            adapter = BranchesAdapter(mBranches, branches.second, viewModel)
                        }
                    }

                }
            }
            is Resource.DataError -> {
                hideLoading()
                status.errorCode?.let { viewModel.showError(it) }
            }
        }

    }

    override fun shouldLoadMore(): Boolean {
        return super.shouldLoadMore()
    }

}
package com.example.csc_mvvm.ui.component.branch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.csc_mvvm.R
import com.example.csc_mvvm.data.dto.branch.BranchModel
import com.example.csc_mvvm.databinding.ItemBranchBinding
import com.example.csc_mvvm.ui.component.branch.BranchViewModel

class BranchesAdapter(
    private val branches: List<BranchModel>,
    private val selected: Int,
    private val branchViewModel: BranchViewModel
) : RecyclerView.Adapter<BranchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BranchViewHolder(
        ItemBranchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = branches.size

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.bind(branches[position], selected, branchViewModel)
    }
}


class BranchViewHolder(private val binding: ItemBranchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(branch: BranchModel, selected: Int, branchViewModel: BranchViewModel) {
        binding.apply {
            tvName.text = branch.name
            tvAddress.text = branch.address
            tvDistance.text = root.context.getString(R.string.branch_distance, branch.distance)
            itemView.setOnClickListener {
                checkBox.run {
                    if (!isChecked) {
                        branchViewModel.selectBranch(branch)
                        isChecked = true
                    }
                }
            }
            checkBox.isChecked = selected == branch.id
        }
    }
}
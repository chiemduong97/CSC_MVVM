package com.example.csc_mvvm.ui.component.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.csc_mvvm.R
import com.example.csc_mvvm.app.MAX_ITEM_CATEGORY
import com.example.csc_mvvm.app.TYPE_ITEM
import com.example.csc_mvvm.app.TYPE_MORE
import com.example.csc_mvvm.data.dto.category.CategoryModel
import com.example.csc_mvvm.databinding.ItemCategoryBinding
import com.example.csc_mvvm.databinding.ItemCategorySeeMoreBinding

class CategoriesAdapter(
    private val categories: List<CategoryModel>,
    private val onClick: (category: CategoryModel) -> Unit,
    private val onClickSeeMore: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        else SeeMoreCategoryViewHolder(
            ItemCategorySeeMoreBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> {
                holder.bind(categories[position], onClick)
            }

            is SeeMoreCategoryViewHolder -> {
                holder.bind(onClickSeeMore)
            }
        }
    }

    override fun getItemCount() = categories.size

    override fun getItemViewType(position: Int): Int {
        return if (position == MAX_ITEM_CATEGORY - 1 && categories.size == MAX_ITEM_CATEGORY) {
            TYPE_MORE
        } else {
            TYPE_ITEM
        }
    }
}

class CategoryViewHolder(private val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(category: CategoryModel, onClick: (category: CategoryModel) -> Unit) {
        binding.apply {
            root.setOnClickListener { onClick.invoke(category) }
            Glide.with(root.context).asBitmap().placeholder(R.drawable.ic_category_default)
                .load(category.avatar).into(viewIcon)
            tvName.text = category.name
        }
    }
}

class SeeMoreCategoryViewHolder(private val binding: ItemCategorySeeMoreBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(onClick: () -> Unit) {
        binding.root.setOnClickListener { onClick.invoke() }
    }
}
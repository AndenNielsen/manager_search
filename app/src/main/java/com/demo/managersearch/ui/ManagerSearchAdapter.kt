package com.demo.managersearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.managersearch.databinding.ManagerSearchListItemBinding

class ManagerSearchAdapter :
    ListAdapter<ManagerSearchListItemViewModel, ManagerSearchAdapter.ManagerSearchListItemBindingViewHolder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ManagerSearchListItemBindingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: ManagerSearchListItemBinding =
            ManagerSearchListItemBinding.inflate(layoutInflater, parent, false)
        return ManagerSearchListItemBindingViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ManagerSearchListItemBindingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<ManagerSearchListItemViewModel>() {
        override fun areItemsTheSame(
            oldItem: ManagerSearchListItemViewModel,
            newItem: ManagerSearchListItemViewModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ManagerSearchListItemViewModel,
            newItem: ManagerSearchListItemViewModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class ManagerSearchListItemBindingViewHolder(private val binding: ManagerSearchListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ManagerSearchListItemViewModel) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}


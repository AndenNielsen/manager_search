package com.demo.managersearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.demo.managersearch.R
import com.demo.managersearch.databinding.ManagerSearchFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@FlowPreview
class ManagerSearchFragment : Fragment() {

    companion object {
        fun newInstance() = ManagerSearchFragment()
    }

    private lateinit var adapter: ManagerSearchAdapter
    private val viewModel: ManagerSearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: ManagerSearchFragmentBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.manager_search_fragment, container, false
            )
        binding.viewModel = viewModel

        adapter = ManagerSearchAdapter()
        binding.managerList.adapter = adapter
        binding.managerList.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(context, VERTICAL)
        binding.managerList.addItemDecoration(dividerItemDecoration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.items.observe(viewLifecycleOwner, { adapter.submitList(it) })
    }

}
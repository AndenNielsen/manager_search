package com.demo.managersearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.demo.managersearch.R
import com.demo.managersearch.databinding.MainFragmentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@FlowPreview
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var adapter: ManagerSearchAdapter
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: MainFragmentBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.main_fragment, container, false
            )
        binding.viewModel = viewModel

        adapter = ManagerSearchAdapter()
        binding.managerList.adapter = adapter
        binding.managerList.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        binding.managerList.addItemDecoration(dividerItemDecoration)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.items.observe(viewLifecycleOwner, {
            it.let {
                adapter.submitList(it)
            }
        })
    }

}
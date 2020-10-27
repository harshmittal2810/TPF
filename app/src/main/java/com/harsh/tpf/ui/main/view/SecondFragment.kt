package com.harsh.tpf.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.harsh.tpf.R
import com.harsh.tpf.databinding.FragmentSecondBinding
import com.harsh.tpf.model.User
import com.harsh.tpf.ui.main.adapter.SecondAdapter
import com.harsh.tpf.ui.main.viewmodel.MainViewModel
import com.harsh.tpf.utils.VerticalSpacingItemDecoration

class SecondFragment : Fragment() {

    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var secondAdapter: SecondAdapter
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_second,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupUI()
        setupObserver()

        binding.btnBack.setOnClickListener {
            navController.popBackStack()
        }
    }

    private fun setupUI() {
        secondAdapter = SecondAdapter(arrayListOf())

        secondAdapter.onItemClicked = {
            binding.tvChooseSubTitle.text = it.name
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalSpacingItemDecoration(5))
            adapter = secondAdapter
        }
    }

    private fun setupObserver() {
        mainViewModel.selectedLiveData.observe(viewLifecycleOwner, {
            it?.let { users ->
                binding.progressBar.visibility = View.GONE
                renderList(users)
                binding.recyclerView.visibility = View.VISIBLE
            }
        })
    }

    private fun renderList(users: List<User>) {
        secondAdapter.addData(users)
    }
}
package com.harsh.tpf.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.harsh.tpf.R
import com.harsh.tpf.databinding.FragmentMainBinding
import com.harsh.tpf.model.User
import com.harsh.tpf.ui.main.adapter.MainAdapter
import com.harsh.tpf.ui.main.viewmodel.MainViewModel
import com.harsh.tpf.utils.Status
import com.harsh.tpf.utils.VerticalSpacingItemDecoration
import com.harsh.tpf.utils.navigateSafe

class MainFragment : Fragment() {

    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mainAdapter: MainAdapter
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
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
    }

    private fun setupUI() {
        mainAdapter = MainAdapter(arrayListOf())

        mainAdapter.onItemClicked = {
            mainViewModel.validateForm()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalSpacingItemDecoration(5))
            adapter = mainAdapter
        }

        binding.btnNext.setOnClickListener {
            mainViewModel.fetchUsers()
            navController.navigateSafe(R.id.fragment_second)
        }
    }

    private fun setupObserver() {
        mainViewModel.users.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        mainViewModel.isFormValid().observe(viewLifecycleOwner, {
            binding.btnNext.isEnabled = it
            binding.tvValid.isVisible = !it
        })
    }

    private fun renderList(users: List<User>) {
        mainAdapter.addData(users)
    }
}
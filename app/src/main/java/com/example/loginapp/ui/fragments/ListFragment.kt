package com.example.loginapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentListBinding
import com.example.loginapp.shareViewModel.LoginViewModel
import com.example.loginapp.utils.navigation


class ListFragment : Fragment() {
    private val binding: FragmentListBinding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }
    private var userViewModel: LoginViewModel? = null
    private var listAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = UserAdapter(onDeleteClick = { user ->
            userViewModel?.deleteUser(user)
        })
        binding.viewlist.adapter = listAdapter
        binding.viewlist.layoutManager = LinearLayoutManager(requireContext())
        userViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        userViewModel?.allUser?.observe(viewLifecycleOwner){ user->
            listAdapter?.submitList(user)

        }
    }
}
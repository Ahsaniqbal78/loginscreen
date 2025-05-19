package com.example.loginapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentLoginBinding
import com.example.loginapp.db.User
import com.example.loginapp.shareViewModel.LoginViewModel
import com.example.loginapp.utils.navigation
import com.example.loginapp.utils.toast


class LoginFragment : Fragment() {
    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }
    private var userViewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveSata()
        binding.showList.setOnClickListener {
            navigation(R.id.loginFragment, R.id.action_loginFragment_to_listFragment)
        }
        binding.mediaPlayer.setOnClickListener {
            navigation(R.id.loginFragment, R.id.action_loginFragment_to_mediaFragment)

        }
    }

    private fun saveSata() {
        binding.login.setOnClickListener {
            val username = binding.userName.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                saveUser(username, password)
                binding.userName.text?.clear()
                binding.password.text?.clear()
                toast(getString(R.string.user_saved))


            } else {
                toast(getString(R.string.please_filled_the_field))

            }
        }
    }

    private fun saveUser(username: String, password: String) {
        val newUser = User(username = username, password = password)
        userViewModel?.insertUser(newUser)
    }
}
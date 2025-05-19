package com.example.loginapp.utils

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


    fun Fragment.toast(message: String) {
        try {
            context?.let {
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        } catch (_: Exception) {}
    }


    fun Fragment.navigation(currentDestination:Int,actionId: Int) {
        try {
            if ( findNavController().currentDestination?.id == currentDestination) {
                findNavController().navigate(actionId)
            }
        } catch (_: Exception){}
    }


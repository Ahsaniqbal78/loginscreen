package com.example.loginapp.ui.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentMediaBinding
import com.example.loginapp.ui.service.PlayerService


class MediaFragment : Fragment() {
    private val binding:FragmentMediaBinding by lazy {
        FragmentMediaBinding.inflate(layoutInflater)
    }

    private fun sendCommand(action:String){
        val intent = Intent(requireContext(),PlayerService::class.java).apply {
            this.action = action
        }
        requireContext().startService(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity?:return, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity?:return,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        binding.play.setOnClickListener {

            sendCommand(PlayerService.ACTION_PLAY)
        }
        binding.pause.setOnClickListener {
            sendCommand(PlayerService.ACTION_PAUSE)
        }
        binding.stop.setOnClickListener {
            sendCommand(PlayerService.ACTION_STOP)
        }

    }
}
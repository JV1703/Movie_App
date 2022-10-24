package com.example.details.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.details.databinding.FragmentVideoDialogBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideoDialogFragment(private val videoKey: String) : DialogFragment() {

    companion object {
        const val TAG = "VideoDialog"
    }

    private var _binding: FragmentVideoDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoDialogBinding.inflate(inflater, container, false)
        playVideo(videoKey)
        return binding.root
    }

    private fun playVideo(videoKey: String) {
        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.translationZ = 100f

        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoKey, 0F)
//                youTubePlayer.loadVideo(videoKey, 0F)
            }
        })
    }

    override fun onDestroy() {
        lifecycle.removeObserver(binding.youtubePlayerView)
        _binding = null
        super.onDestroy()
    }

}
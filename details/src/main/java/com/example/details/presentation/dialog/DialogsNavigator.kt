package com.example.details.presentation.dialog

import androidx.fragment.app.FragmentManager

class DialogsNavigator(private val fragmentManager: FragmentManager) {

    fun showVideoDialog(videoKey: String) {
        val videoDialogFragment = VideoDialogFragment(videoKey)
        videoDialogFragment.show(fragmentManager, VideoDialogFragment.TAG)
    }

}
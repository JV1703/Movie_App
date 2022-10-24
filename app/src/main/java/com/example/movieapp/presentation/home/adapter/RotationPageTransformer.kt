package com.example.movieapp.presentation.home.adapter

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.tan


class RotationPageTransformer @JvmOverloads constructor(
    private val degrees: Int, minAlpha: Float = 0.7f
) : ViewPager2.PageTransformer {
    private val minAlpha: Float
    private val distanceToCentreFactor: Float
    /**
     * Creates a RotationPageTransformer
     * @param degrees the inner angle between two edges in the "polygon" that the pages are on.
     * Note, this will only work with an obtuse angle
     * @param minAlpha the least faded out that the side
     */
    /**
     * Creates a RotationPageTransformer
     * @param degrees the inner angle between two edges in the "polygon" that the pages are on.
     * Note, this will only work with an obtuse angle
     */
    init {
        distanceToCentreFactor = tan(Math.toRadians((degrees / 2).toDouble())).toFloat() / 2
        this.minAlpha = minAlpha
    }

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height
        view.pivotX = pageWidth.toFloat() / 2
        view.pivotY = (pageHeight + pageWidth * distanceToCentreFactor)
        if (position < -1) { //[-infinity,1)
            //off to the left by a lot
            view.rotation = 0f
            view.alpha = 0f
        } else if (position <= 1) { //[-1,1]
            view.translationX = -position * pageWidth //shift the view over
            view.rotation = position * (180 - degrees) //rotate it
            // Fade the page relative to its distance from the center
            view.alpha = max(minAlpha, 1 - abs(position) / 3)
        } else { //(1, +infinity]
            //off to the right by a lot
            view.rotation = 0f
            view.alpha = 0f
        }
    }
}
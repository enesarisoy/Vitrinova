package com.ns.vitrinova.ui.custom

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class FeaturedViewPagerTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        if (position <= 0) {
            val pageWidth: Int = page.width
            val translateValue = position * -pageWidth
            if (translateValue > -pageWidth)
                page.translationX = translateValue
            else
                page.translationX = 0f
        }
    }
}
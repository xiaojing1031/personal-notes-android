package com.chenxi.scroll

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.Scroller
import androidx.appcompat.widget.AppCompatTextView

class ScrollerLayout: FrameLayout {
    constructor(context: Context): super(context, null)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int): super(
        context,
        attributeSet,
        defStyle
    ) {
        init()
    }

    private lateinit var scrollText: AppCompatTextView
    private fun init() {
        View.inflate(context, R.layout.scroller_layout, this)
        scrollText = findViewById(R.id.scrollerText)
        scrollText.text = "scroll me"
    }

    private val mScroller = Scroller(this.context)
    fun smoothScrollTo(destX: Int, destY: Int) {
        val scrollX = scrollText.scrollX
        val deltaX = destX - scrollX
        mScroller.startScroll(scrollX, 0, deltaX, 0, 1000)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            postInvalidate()
        }

    }
}

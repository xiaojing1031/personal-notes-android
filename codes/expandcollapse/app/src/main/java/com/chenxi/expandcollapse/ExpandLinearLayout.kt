package com.chenxi.expandcollapse

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginTop

class ExpandLinearLayout : LinearLayout {

    private var isOpen = true;

    private var firstChildHeight = 0

    private var allChildHeight = 0

    private var animPercent: Float = 0f
        set(value) {
            field = value
            requestLayout()
        }

    constructor(context: Context): super(context) {
        initView()
    }
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        initView()
    }
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr) {
        initView()
    }

    private fun initView() {
        orientation = VERTICAL
        animPercent = 1f
        isOpen = true

    }

    fun toggle() : Boolean {
        isOpen = !isOpen
        startAnim()
        return isOpen
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun startAnim() {

        val animator = ObjectAnimator.ofFloat(this, "animPercent", 0f, 1f)
        animator.duration = 1000
        animator.start()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 重置高度
        firstChildHeight = 0
        allChildHeight = 0

        if (childCount > 0) {
            for (index in 0 until childCount) {
                if (index == 0) {
                    firstChildHeight = getChildAt(index).measuredHeight +
                            getChildAt(index).marginTop +
                            getChildAt(index).marginBottom +
                            this.paddingTop +
                            this.paddingBottom
                }

                allChildHeight += getChildAt(index).measuredHeight +
                        getChildAt(index).marginTop +
                        getChildAt(index).marginBottom

                if (index == childCount - 1) {
                    allChildHeight += this.paddingTop + this.paddingBottom
                }

            }
        }

        if (isOpen) {
            setMeasuredDimension(widthMeasureSpec,
                firstChildHeight + ((allChildHeight - firstChildHeight) * animPercent).toInt()
            )
        } else {
            setMeasuredDimension(widthMeasureSpec,
                allChildHeight - ((allChildHeight - firstChildHeight) * animPercent).toInt()
            )
        }

    }

}
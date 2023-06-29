package com.example.stackview

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.contains

class CustomStackView(
    private val frameLayout: FrameLayout,
    private val screenHeight: Int,
    private val list: MutableList<Triple<ToggleView, Float, Float>>
) : StackFramework {
    private var currentIndex = 0
    private var nextIndex = 0

    init {
        val firstView = list.first().first
        performToggle(firstView, firstView.getCurrentStackState())
    }

    override fun changeStackState(view: ToggleView) {
        currentIndex = list.indexOf(list.find { it.first == view })
        if (currentIndex == list.size - 1) {
            val currentState = view.changeStackState()
            performToggle(view, currentState)
        } else {
            nextIndex = currentIndex + 1
            val currentState = view.changeStackState() //toggles the state of the current view
            performToggle(
                view,
                currentState
            ) //animations for the current view based on its toggled state
            val nextView = list[nextIndex].first
            val nextState = nextView.getCurrentStackState()
            performToggle(nextView, nextState)
        }
    }

    private fun performToggle(view: ToggleView, currentStackState: StackState) {
        when (currentStackState) {
            StackState.EXPANDED -> expand(view)
            StackState.COLLAPSED -> collapse(view)
        }
    }

    private fun expand(view: ToggleView) {
        val expandRatio = list.find { it.first == view }?.second ?: 0.75f
        performAnimation(view, (screenHeight * expandRatio).toInt())
        addViewToFrameLayout(view)
    }

    private fun collapse(view: ToggleView) {
        val collapseRatio = list.find { it.first == view }?.third?: 0.25f
        performAnimation(view,(screenHeight*collapseRatio).toInt())
        addViewToFrameLayout(view)

    }

    private fun performAnimation(view: View, height: Int) {
        valueAnimateForView(view,height, view.height)
    }

    private fun valueAnimateForView(
        view: View,
        endHeight: Int,
        startHeight: Int,
        onAnimationComplete: () -> Unit = {}
    ) {
        val layoutParams = view.layoutParams as FrameLayout.LayoutParams
        view.layoutParams = layoutParams
        (view.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.BOTTOM


        val valueAnimator = ValueAnimator.ofInt(startHeight, endHeight)
        valueAnimator.duration = 200
        valueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            layoutParams.height = value
            view.layoutParams = layoutParams
        }
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                onAnimationComplete()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }

        })
        valueAnimator.start()
    }

    private fun addViewToFrameLayout(view: View) {
        if (!frameLayout.contains(view)) {
            frameLayout.addView(view)
        }
    }


}
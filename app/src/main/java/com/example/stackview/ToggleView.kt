package com.example.stackview

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class ToggleView(context: Context, attributeSet: AttributeSet) : CardView(context, attributeSet) {
    private var stackState: StackState = StackState.COLLAPSED

    fun changeStackState(): StackState {
        stackState = if (stackState == StackState.COLLAPSED) {
            StackState.EXPANDED
        } else {
            StackState.COLLAPSED
        }
        return stackState
    }

    fun getCurrentStackState() = stackState
}

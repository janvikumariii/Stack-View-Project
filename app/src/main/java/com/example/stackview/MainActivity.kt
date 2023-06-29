package com.example.stackview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import com.example.stackview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var height:Int?=null
    private var screenHeight:Int?=null
    private lateinit var stackFramework: StackFramework
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Getting current screen height
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels

        height=(screenHeight!! /2.0).toInt()

        val firstView=LayoutInflater.from(this).inflate(R.layout.firstview,binding.stackContainer,false) as ToggleView
        val secondView=LayoutInflater.from(this).inflate(R.layout.secondview,binding.stackContainer,false) as ToggleView
        val thirdView=LayoutInflater.from(this).inflate(R.layout.thirdview,binding.stackContainer,false) as ToggleView
        val fourthView=LayoutInflater.from(this).inflate(R.layout.fourthview,binding.stackContainer,false) as ToggleView
        val fifthView=LayoutInflater.from(this).inflate(R.layout.fifthview,binding.stackContainer,false) as ToggleView

        val list  = mutableListOf<Triple<ToggleView,Float,Float>>().apply {
            add(Triple(firstView,0.9f,0.26f))
            add(Triple(secondView,0.8f,0.21f))
            add(Triple(thirdView,0.7f,0.16f))
            add(Triple(fourthView,0.6f,0.11f))
            add(Triple(fifthView,0.5f,0.06f))
        }

        stackFramework=CustomStackView(binding.stackContainer, screenHeight!!,list)

        firstView.setOnClickListener {
            stackFramework.changeStackState(firstView)
        }
        secondView.setOnClickListener {
            stackFramework.changeStackState(secondView)
        }

        thirdView.setOnClickListener {
            stackFramework.changeStackState(thirdView)
        }

        fourthView.setOnClickListener {
            stackFramework.changeStackState(fourthView)
        }
        fifthView.setOnClickListener {
            stackFramework.changeStackState(fifthView)
        }
    }
}
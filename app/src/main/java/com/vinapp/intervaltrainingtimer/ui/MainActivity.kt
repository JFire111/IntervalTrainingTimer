package com.vinapp.intervaltrainingtimer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vinapp.intervaltrainingtimer.R
import com.vinapp.intervaltrainingtimer.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainPresenter = MainPresenter()
    private val fragment = MainView(mainPresenter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, fragment).commit()
    }
}
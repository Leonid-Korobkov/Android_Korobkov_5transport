package com.example.android_korobkov.l.android_korobkov_5transport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_korobkov.l.android_korobkov_5transport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        // setContentView(R.layout.activity_main)
        setContentView(binding.root)
    }
}
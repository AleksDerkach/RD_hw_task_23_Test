package com.example.task_23_hometask_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val result: TextView = findViewById(R.id.result)
        val button: Button = findViewById(R.id.button)
        //5. viewModel
        val viewModel:MyViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        button.setOnClickListener {
            viewModel.getData()
        }
        viewModel.uiState.observe(this){uiState ->
            when(uiState) {
                is MyViewModel.UiState.Empty -> result.text = ""
                is MyViewModel.UiState.Processing -> result.text = "Processing..."
                is MyViewModel.UiState.Result -> result.text = uiState.title
                is MyViewModel.UiState.Error -> result.text = "Error"
            }
        }
    }
}
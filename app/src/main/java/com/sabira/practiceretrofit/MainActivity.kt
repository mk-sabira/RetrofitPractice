package com.sabira.practiceretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sabira.practiceretrofit.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException


const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoAdapter: ToDoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()

        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {

                RetrofitInstance.api.getToDos()

            } catch (e: IOException){
                Log.e(TAG, "No internet connection")
                binding.progressBar.isVisible  = false
                return@launchWhenCreated

            } catch (e: HttpException) {
                Log.e(TAG, "Http exception, unexpected response")
                binding.progressBar.isVisible  = false
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                todoAdapter.todos = response.body()!!
            }else {
                Log.e(TAG, "Response not successful ")
            }
            binding.progressBar.isVisible  = false
        }

    }

    private fun setUpRecyclerView() = binding.rvToDo.apply {
        todoAdapter = ToDoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}
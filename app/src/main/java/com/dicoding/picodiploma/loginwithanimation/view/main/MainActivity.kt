package com.dicoding.picodiploma.loginwithanimation.view.main

import com.dicoding.picodiploma.loginwithanimation.view.detail.DetailActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.adapter.StoryAdapter
import com.dicoding.picodiploma.loginwithanimation.data.adapter.storyClick
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.di.Injection
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.maps.MapsActivity
import com.dicoding.picodiploma.loginwithanimation.view.upStory.UploadStory
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.clearInstance()
        ViewModelFactory(Injection.provideRepository(this@MainActivity),Injection.provideStoryRep(this@MainActivity))
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
            token= user.token


        }

        setupView()

        supportActionBar?.title = "Dicoding"

        storyAdapter.setStoryClickListener(object : storyClick{
            override fun onStoryClick(story: ListStoryItem) {
                onClickDetail(story)
            }
        })

        binding.fabAddStory.setOnClickListener{
            onClickUpload()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu -> {
                logOut()
                true
            }

            R.id.map ->{
                onClickMap()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
    private fun setupView() {
        storyAdapter = StoryAdapter()
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.rvStory.adapter = storyAdapter
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun getData(){
        viewModel.story.observe(
            this,
        ) { pagingData ->
            storyAdapter.submitData(lifecycle, pagingData)
        }
    }

     fun onClickDetail(storyList: ListStoryItem){
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("id", storyList.id.toString())
         Log.d("id di main", storyList.id.toString())
        intent.putExtra("token", token)
         Log.d("token main", token)
        startActivity(intent)
    }

    fun onClickUpload(){
        val intent = Intent(this@MainActivity, UploadStory::class.java)
        intent.putExtra("token", token)
        startActivity(intent)
    }

    fun onClickMap(){
        val intent = Intent(this@MainActivity, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun logOut(){
        viewModel.viewModelScope.launch {
            viewModel.logout()
            val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}



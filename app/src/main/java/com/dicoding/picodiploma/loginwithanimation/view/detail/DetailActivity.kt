package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailBinding
import com.dicoding.picodiploma.loginwithanimation.view.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Dicoding"

        val id: String = intent.getStringExtra("id").toString()
        val token: String = intent.getStringExtra("token").toString()

        Log.d("token hasil:", token.toString())
        Log.d("id hasil:", id.toString())

        if (id != null) {
            viewModel.getStoryDetail(id, token)
        }

        viewModel.detailStory.observe(this){
            Log.d("DetailActivity", "Observed detailStory: $it")
            showLoading(false)
            binding.tvDicoding.text = it.name
            binding.tvDesc.text = it.description

            Glide.with(this)
                .load(it.photoUrl)
                .into(binding.ivDetail)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}

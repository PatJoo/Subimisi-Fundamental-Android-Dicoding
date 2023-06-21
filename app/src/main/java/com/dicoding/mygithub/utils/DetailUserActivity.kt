package com.dicoding.mygithub.utils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.mygithub.SettingPreferences
import com.dicoding.mygithub.ViewModelFactory
import com.dicoding.mygithub.adapter.SectionPagerAdapter
import com.dicoding.mygithub.dataStore
import com.dicoding.mygithub.database.UserConfig
import com.dicoding.mygithub.databinding.ActivityDetailUserBinding
import com.dicoding.mygithub.model.User

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }
    private var data:User? = null
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val pref = SettingPreferences.getInstance(application.dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory(pref, this.application, UserConfig(this))).get(
            DetailUserViewModel::class.java)

        binding.btnFav.setOnClickListener {
            viewModel.setFavorite(data)
        }
        viewModel.setUserDetail(username)
        showLoading(true)
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    data=User(it.login,it.avatar_url, it.id)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity).load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade()).centerCrop()
                        .into(ivProfile)
                    showLoading(false)
                }
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
    binding.apply {
        viewPager.adapter =sectionPagerAdapter
        tablayout.setupWithViewPager(viewPager)
    }
    }


    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar2.visibility = View.VISIBLE
        }else{
            binding.progressBar2.visibility = View.GONE
        }
    }
}
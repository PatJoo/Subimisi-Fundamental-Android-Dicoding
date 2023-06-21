package com.dicoding.mygithub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.mygithub.R
import com.dicoding.mygithub.SettingPreferences
import com.dicoding.mygithub.ViewModelFactory
import com.dicoding.mygithub.adapter.UserAdapter
import com.dicoding.mygithub.dataStore
import com.dicoding.mygithub.database.UserConfig
import com.dicoding.mygithub.databinding.ActivityFavoriteBinding
import com.dicoding.mygithub.model.User
import com.dicoding.mygithub.utils.DetailUserActivity


class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref, this.application, UserConfig(this))
        ).get(FavoriteViewModel::class.java)

        adapter = UserAdapter()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                startActivity(intent)
            }

        })
        binding.rvUser.layoutManager=LinearLayoutManager(this)
        binding.rvUser.adapter = adapter
        showLoading(true)
        viewModel.getUserFavorite().observe(this, Observer {
            adapter.setList(it)
            showLoading(false)
6
        })

    }

    override fun onResume() {
        super.onResume()
        binding.rvUser.layoutManager=LinearLayoutManager(this)
        binding.rvUser.adapter = adapter

        viewModel.getUserFavorite().observe(this, Observer {
            adapter.setList(it)
        })
    }

    fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}

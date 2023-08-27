package com.example.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.ViewModelFactory
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.data.FavoriteEntity
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.model.UserDetailResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator



class DetailActivity : AppCompatActivity(), OnClickListener{

    private lateinit var userBinding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>(){
        ViewModelFactory.getInstance(this)
    }

    private lateinit var favUser :FavoriteEntity
    var isFavorite :Boolean = false

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val TAG = "DetaiActivity"
        const val KEY = "Data"

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        userBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(userBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        sectionsPagerAdapter.username = intent.getStringExtra(KEY).toString()

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra(KEY)
        if(username !=null){
            detailViewModel.findData(username)
        }

        detailViewModel.detailUser.observe(this, { detail ->
            detailData(detail)
            favUser = FavoriteEntity(username.toString(),detail.avatarUrl)
        })

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailViewModel.FindUserById(username.toString()).observe(this){user : FavoriteEntity?->
            if(user !=null){
                isFavorite = true
                userBinding.favoriteBtn.setImageDrawable(
                    ContextCompat.getDrawable(this,
                    R.drawable.favorite
                ))
            }else{
                isFavorite = false
                userBinding.favoriteBtn.setImageDrawable(
                    ContextCompat.getDrawable(this,
                    R.drawable.favorite_border
                ))
            }
        }
        userBinding.favoriteBtn.setOnClickListener(this)


    }

    private fun detailData(detail : UserDetailResponse){
        userBinding.apply{
            tvUsername.text = detail.login
            tvName.text = detail.name
            tvFollower.text = resources.getString(R.string.follower_count, detail.followers)
            tvFollowing.text = resources.getString(R.string.following_count, detail.following)
            Glide.with(this@DetailActivity)
                .load(detail.avatarUrl)
                .into(photoDetail)

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            userBinding.progressBar2.visibility = View.VISIBLE
        } else {
            userBinding.progressBar2.visibility = View.GONE
        }
    }

    override fun onClick (view: View){
        if (view.id == R.id.favorite_btn){
            if(isFavorite){
                detailViewModel.delete(favUser)
            }else{
                detailViewModel.Insert(favUser)
            }
        }
    }

}
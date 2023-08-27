package com.example.githubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.ItemsItem
import com.example.githubuser.ViewModelFactory
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.data.FavoriteEntity
import com.example.githubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Favorite User"

        val layoutManager = LinearLayoutManager(this)
        favoriteBinding.rvFavUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        favoriteBinding.rvFavUser.addItemDecoration((itemDecoration))

        favoriteViewModel.getUserFavorite().observe(this) {user : List<FavoriteEntity> ->
            val items = arrayListOf<ItemsItem>()
            user.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
                favoriteBinding.rvFavUser.adapter = UserAdapter(items)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
package com.example.githubuser.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.ui.detail.DetailActivity
import com.example.githubuser.ItemsItem
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.FragmentFollowersBinding
import com.example.githubuser.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowersFragment : Fragment() {

    private lateinit var fragmentBinding : FragmentFollowersBinding
    private val adapterBinding get() = fragmentBinding

    private var position: Int? = null
    private var username: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentFollowersBinding.inflate(inflater, container,false )
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        adapterBinding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        adapterBinding.rvFollowers.addItemDecoration(itemDecoration)


        if (position ==1){
            fragmentLoading(true)
            val client = username?.let { ApiConfig.getApiService().getFollowers(it)}
            if(client !=null){
                client.enqueue(object : Callback<List<ItemsItem>> {
                    override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                        fragmentLoading(false)
                        if (response.isSuccessful){
                            val responseBody = response.body()
                            if (responseBody !=null){
                                followersData(responseBody)
                            }
                        }else{
                            Log.e(DetailActivity.TAG,"onFaliure : ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                        fragmentLoading(false)
                        Log.e(DetailActivity.TAG,"onFaliure : ${t.message}")
                    }
                })
            }
        }

        if (position ==2){
            fragmentLoading(true)
            val client = username?.let {ApiConfig.getApiService().getFollowing(it)}
            if(client !=null){
                client.enqueue(object : Callback<List<ItemsItem>> {
                    override fun onResponse(call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>) {
                        fragmentLoading(false)
                        if (response.isSuccessful){
                            val responseBody = response.body()
                            if (responseBody !=null){
                               followersData(responseBody)
                            }
                        }else{
                            Log.e(DetailActivity.TAG,"onFaliure : ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                        fragmentLoading(false)
                        Log.e(DetailActivity.TAG,"onFaliure : ${t.message}")
                    }
                })
            }
        }

    }
    private fun followersData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter(listUser)
        fragmentBinding.rvFollowers.adapter = adapter

    }

    private fun fragmentLoading(isLoading: Boolean) {
        if (isLoading) {
            fragmentBinding.progressBar3.visibility = View.VISIBLE
        } else {
            fragmentBinding.progressBar3.visibility = View.GONE
        }
    }
    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "name"
    }
}
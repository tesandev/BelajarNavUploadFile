package com.tesan.belajarnavigationbuttomfloating

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tesan.belajarnavigationbuttomfloating.Adapter.ProfilesAdapter
import com.tesan.belajarnavigationbuttomfloating.ResponseModel.DataItem
import com.tesan.belajarnavigationbuttomfloating.ResponseModel.ResponseProfiles
import com.tesan.belajarnavigationbuttomfloating.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment


        ApiConfig.instanceRetrofit.profiles().enqueue(object : Callback<ResponseProfiles>{
            override fun onResponse(
                call: Call<ResponseProfiles>,
                response: Response<ResponseProfiles>
            ) {
                val resp = response.body()?.data
                if(response.body()!!.status == "00"){
                    binding.rvProfiles.setHasFixedSize(true)
                    binding.rvProfiles.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = ProfilesAdapter(resp as List<DataItem>)
                    }
                }else{
                    Toast.makeText(context,response.body()!!.message,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseProfiles>, t: Throwable) {
                Log.e("ERR",t.message.toString())
            }

        })
        return binding.root
    }
}
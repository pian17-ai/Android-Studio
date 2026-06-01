package com.example.kopinusantara

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.kopinusantara.api.ApiClient
import com.example.kopinusantara.model.ProductResponse
import android.content.Intent

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerProducts)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        getProducts()
    }

    private fun getProducts() {
        ApiClient.instance.getProducts().enqueue(object : Callback<ProductResponse> {

            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()?.data ?: emptyList()
                    recyclerView.adapter = ProductAdapter(list) { product ->
                        val intent = Intent(requireContext(), DetailActivity::class.java)
                        intent.putExtra("PRODUCT_ID", product.id)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}
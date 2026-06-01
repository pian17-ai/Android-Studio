package com.example.kopinusantara

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

import com.bumptech.glide.Glide

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.kopinusantara.api.ApiClient
import com.example.kopinusantara.model.ProductDetailResponse
import com.example.kopinusantara.session.SessionManager

class DetailActivity : AppCompatActivity() {

    private lateinit var img: ImageView
    private lateinit var name: TextView
    private lateinit var price: TextView
    private lateinit var desc: TextView

    private var quantity = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val btnPlus = findViewById<Button>(R.id.btnPlus)
        val btnMinus = findViewById<Button>(R.id.btnMinus)
        val txtQty = findViewById<TextView>(R.id.txtQty)
        val btnAddCart = findViewById<Button>(R.id.btnAddCart)

        txtQty.text = quantity.toString()

        btnPlus.setOnClickListener {
            quantity++
            txtQty.text = quantity.toString()
        }

        btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                txtQty.text = quantity.toString()
            }
        }

        img = findViewById(R.id.imgDetail)
        name = findViewById(R.id.txtName)
        price = findViewById(R.id.txtPrice)
        desc = findViewById(R.id.txtDesc)

        val productId = intent.getIntExtra("PRODUCT_ID", 0)

        getDetail(productId)

        btnAddCart.setOnClickListener {
            addToCart(productId)
        }

    }

    private fun getDetail(id: Int) {
        ApiClient.instance.getProductDetail(id)
            .enqueue(object : Callback<ProductDetailResponse> {

                override fun onResponse(
                    call: Call<ProductDetailResponse>,
                    response: Response<ProductDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.data

                        name.text = data?.name
                        price.text = "Rp ${data?.price}"
                        desc.text = data?.description

                        val imageUrl = "http://192.168.18.12:8000/storage/${data?.image_url}"

                        Glide.with(this@DetailActivity)
                            .load(imageUrl)
                            .into(img)
                    }
                }

                override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }


    private fun addToCart(productId: Int) {
        val session = SessionManager(this)
        val token = session.getToken()
        val bearer = "Bearer $token"

        val body = mapOf(
            "product_id" to productId,
            "quantity" to quantity
        )

        ApiClient.instance.createOrder(bearer, body)
            .enqueue(object : Callback<Any> {

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetailActivity, "Berhasil tambah ke cart", Toast.LENGTH_SHORT).show()
                    } else {
                        val error = response.errorBody()?.string()
//                        Toast.makeText(this@DetailActivity, error, Toast.LENGTH_LONG).show()
                        Toast.makeText(this@DetailActivity, "Gagal", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}
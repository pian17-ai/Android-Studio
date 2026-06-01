package com.example.kopinusantara

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kopinusantara.model.OrderItem

class CartAdapter(private val list: List<OrderItem>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img = view.findViewById<ImageView>(R.id.imgCart)
        val name = view.findViewById<TextView>(R.id.txtName)
        val price = view.findViewById<TextView>(R.id.txtPrice)
        val qty = view.findViewById<TextView>(R.id.txtQty)
        val subtotal = view.findViewById<TextView>(R.id.txtSubtotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.name.text = item.product_name
        holder.price.text = "Rp ${item.price}"
        holder.qty.text = "Qty: ${item.quantity}"
        holder.subtotal.text = "Subtotal: Rp ${item.subtotal}"

        val imageUrl = "http://192.168.18.12:8000/storage/${item.image}"

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.img)
    }
}
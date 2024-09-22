package com.example.products_roshan

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.products_roshan.databinding.CustomRowLayoutBinding
import com.example.products_roshan.models.Product

class MyAdapter(var yourData:MutableList<Product>, val myClickInterface: ClickRowInterface) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CustomRowLayoutBinding) : RecyclerView.ViewHolder (binding.root) {
    }


    // tell the function which layout file to use for each row of the recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    // how many items are in the list
    override fun getItemCount(): Int {
        return yourData.size
    }


    // In a single row, what data goes in the <TextView>?
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct: Product = yourData.get(position)

        Log.d("Test get", currentProduct.title)
//        Log.d("Test get1", currentProduct.brand)
        // put the item into the row_layout.xml UI elements
        val tvRow1 = holder.binding.tvRowLine1
        tvRow1.text = currentProduct.title

        holder.binding.tvRowLine2.text = "$ " + currentProduct.price.toString()


        //if clicked in main row line
        holder.binding.line1.setOnClickListener {
            myClickInterface.productRowClicked(position)
        }
    }

}
package com.example.products_roshan

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.products_roshan.api.ApiInterface
import com.example.products_roshan.api.RetrofitInstance
import com.example.products_roshan.databinding.ActivityProductsDetailsBinding
import com.example.products_roshan.models.Product
import kotlinx.coroutines.launch

class ProductsDetails : AppCompatActivity() {

    lateinit var binding: ActivityProductsDetailsBinding

    // retrofit instance
    var api: ApiInterface = RetrofitInstance.retrofitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent != null){
            val productId = intent.getStringExtra("PRODUCT_ID")

            lifecycleScope.launch {
                // gets the response from the api
                // gets the response from the api
                var productDetail: Product = api.getProductDetail(productId.toString())
                Log.d("Test11", productDetail.toString())

                binding.brandView.text = if (productDetail.brand == "")  "" else "BRAND"
                binding.brandName.text = productDetail.brand
                binding.title.text = productDetail.title
                binding.price.text = "$ " + productDetail.price
                binding.txtDescription.text = productDetail.description

                Glide.with(this@ProductsDetails)
                    .load(productDetail.thumbnail)
                    .into(binding.thumbnails)
            }

        }
    }
}

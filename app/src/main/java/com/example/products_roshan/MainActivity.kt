package com.example.products_roshan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.products_roshan.api.ApiInterface
import com.example.products_roshan.api.RetrofitInstance
import com.example.products_roshan.databinding.ActivityMainBinding
import com.example.products_roshan.models.Category
import com.example.products_roshan.models.Product
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ClickRowInterface {

    lateinit var binding: ActivityMainBinding

    // retrofit instance
    var api: ApiInterface = RetrofitInstance.retrofitService

    // adapter and data source
    lateinit var adapter: MyAdapter
    var dataToDisplay:MutableList<Product> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category: MutableList<String> = mutableListOf("Smartphones", "Laptops", "Skincare", "Groceries")
        //create  an instance of an adapter
        val nameAdapter: ArrayAdapter<String>
                = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, category)

        //link the  data using adapter into spinner
        binding.mySpinner.adapter = nameAdapter


        // initialize the adapter
        adapter = MyAdapter(dataToDisplay, this)

        // recyclerview configuration
        binding.myRecyclerView.adapter = adapter
        binding.myRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.myRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        binding.btnSearch.setOnClickListener {

            lifecycleScope.launch {
                Log.d("Tests", binding.mySpinner.selectedItem.toString())

                val selectedItem:String = binding.mySpinner.selectedItem.toString()

                // gets the category response from the api
                var categoryResult:Category = api.getCategory(selectedItem.toLowerCase())

                // inside the response, get the "products" property
                val productsList:List<Product> = categoryResult.products
                // loop through each product and output the description and discount percentage
                for (x: Product in productsList) {
                    Log.d("TEST", "Product description: ${x.description}")
                    Log.d("TEST", "Product Name: ${x.title}")
                }

                Log.d("TESTING", "Results from API")
                Log.d("TESTING", categoryResult.total.toString())


                if (categoryResult.total > 0){
                    // update the recyclerview and list as well
                    dataToDisplay.clear()
                    dataToDisplay.addAll(categoryResult.products)
                    adapter.notifyDataSetChanged()

                }else{

                    // if not data found show error message and clear the list
                    dataToDisplay.clear()
                    adapter.notifyDataSetChanged()

                    val snackbar = Snackbar.make(binding.root, "There are no products for ${selectedItem}!", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            }
        }
    }

    // when clicked on text view of recycler view go to the  product detail screen
    override fun productRowClicked(position: Int) {

        val productId = dataToDisplay[position].id.toString()
        val intent: Intent = Intent(this@MainActivity, ProductsDetails::class.java)

        Log.d("roshan", productId)

        intent.putExtra("PRODUCT_ID", productId)
        startActivity(intent)
    }
}

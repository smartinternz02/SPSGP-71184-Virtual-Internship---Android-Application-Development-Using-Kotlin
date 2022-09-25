package com.jones.groceryapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jones.groceryapp.adapter.GroceryRVAdapter
import com.jones.groceryapp.data.GroceryDatabase
import com.jones.groceryapp.data.GroceryItem
import com.jones.groceryapp.data.GroceryRepository
import com.jones.groceryapp.model.GroceryViewModel
import com.jones.groceryapp.model.GroceryViewModelFactory

class MainActivity : AppCompatActivity(), GroceryRVAdapter.GroceryItemClickInterface {

    private lateinit var itemRV: RecyclerView
    private lateinit var addFAB: FloatingActionButton
    private lateinit var list: List<GroceryItem>
    private lateinit var groceryRVAdapter: GroceryRVAdapter
    private lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemRV = findViewById(R.id.grocery_rv)
        addFAB = findViewById(R.id.add_fab)

        list = ArrayList<GroceryItem>()
        groceryRVAdapter = GroceryRVAdapter(list, this)
        itemRV.layoutManager = LinearLayoutManager(this)
        itemRV.adapter = groceryRVAdapter
        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]
        groceryViewModel.getAllItems().observe(this, Observer {
            groceryRVAdapter.list = it
            groceryRVAdapter.notifyDataSetChanged()
        })

        addFAB.setOnClickListener {
            openDialog()
        }
    }

    fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelBt = dialog.findViewById<Button>(R.id.cancel_button)
        val addBt = dialog.findViewById<Button>(R.id.add_button)
        val itemEdt = dialog.findViewById<EditText>(R.id.edit_itemName)
        val itemQuantityEdt = dialog.findViewById<EditText>(R.id.edit_itemQuantity)
        val itemPriceEdt = dialog.findViewById<EditText>(R.id.edit_itemPrice)

        cancelBt.setOnClickListener {
            dialog.dismiss()
        }

        addBt.setOnClickListener {
            val itemName: String = itemEdt.text.toString()
            val itemQnty: String = itemQuantityEdt.text.toString()
            val itemPrice: String = itemPriceEdt.text.toString()

            if (itemName.isNotEmpty() && itemQnty.isNotEmpty() && itemPrice.isNotEmpty() ) {
                val item = GroceryItem(itemName, itemQnty.toInt(), itemPrice.toDouble())
                groceryViewModel.insert(item)
                Toast.makeText(applicationContext, "Item Added...", Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onItemClick(groceryItem: GroceryItem) {
        groceryViewModel.delete(groceryItem)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "Item Deleted...", Toast.LENGTH_SHORT).show()
    }


}
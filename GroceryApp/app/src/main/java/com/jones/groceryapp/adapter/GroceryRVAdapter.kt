package com.jones.groceryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jones.groceryapp.R
import com.jones.groceryapp.data.GroceryItem

class GroceryRVAdapter(
    var list: List<GroceryItem>,
    var groceryItemClickInterface: GroceryItemClickInterface
) : RecyclerView.Adapter<GroceryRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grocery_rv_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.nameTV.text = item.itemName
        holder.quantityTV.text = item.itemQuantity.toString()
        holder.rateTV.text = item.itemPrice.toString()
        holder.amountTV.text = (item.itemPrice * item.itemQuantity).toString()
        holder.deleteIV.setOnClickListener {
            groceryItemClickInterface.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTV = itemView.findViewById<TextView>(R.id.item_name_tv)
        val quantityTV = itemView.findViewById<TextView>(R.id.quantity_tv)
        val rateTV = itemView.findViewById<TextView>(R.id.rate_tv)
        val amountTV = itemView.findViewById<TextView>(R.id.total_amt)
        val deleteIV = itemView.findViewById<ImageView>(R.id.delete_image)
    }

    interface GroceryItemClickInterface {
        fun onItemClick(groceryItem: GroceryItem)
    }

}
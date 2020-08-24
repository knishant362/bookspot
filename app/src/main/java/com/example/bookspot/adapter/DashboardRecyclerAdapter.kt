package com.example.bookspot.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookspot.R
import com.example.bookspot.activity.DescriptionActivity
import com.example.bookspot.model.Book
import com.squareup.picasso.Picasso


class DashboardRecyclerAdapter(val context:Context , val itemList : ArrayList<Book>)  : RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row, parent, false)

        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]

        holder.txtBookName.text = book.bookName
        holder.txtBookAuthor.text = book.bookAuthor
        holder.txtBookPrice.text = book.bookPrice
        holder.txtBookRating.text = book.bookRating
//        holder.txtBookCover.setBackgroundResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBookCover)

        holder.RlContext.setOnClickListener {
            val intent = Intent(context, DescriptionActivity::class.java)
            intent.putExtra("book_id", book.bookId)
            context.startActivity(intent)
        }

    }

    class DashboardViewHolder(view:View) : RecyclerView.ViewHolder(view){
        var txtBookName : TextView = view.findViewById(R.id.txtBookName)
        var txtBookAuthor : TextView = view.findViewById(R.id.txtBookAuthor)
        var txtBookPrice : TextView = view.findViewById(R.id.txtBookPrice)
        var txtBookRating : TextView = view.findViewById(R.id.txtBookRating)
        var imgBookCover : ImageView = view.findViewById(R.id.imgBookCover)
        val RlContext : RelativeLayout = view.findViewById(R.id.RlContext)

    }

}

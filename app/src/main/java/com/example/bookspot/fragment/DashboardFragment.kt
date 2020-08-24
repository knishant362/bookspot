package com.example.bookspot.fragment

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import android.view.*
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import org.json.JSONException
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookspot.R
import com.example.bookspot.adapter.DashboardRecyclerAdapter
import com.example.bookspot.model.Book
import com.example.bookspot.util.ConnectionManager


class DashboardFragment : Fragment() {

    lateinit var recyclerDashboard : RecyclerView

    lateinit var layoutManager : RecyclerView.LayoutManager

    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    lateinit var progressLayout: RelativeLayout

    lateinit var progressBar: ProgressBar

    val bookInfoList = arrayListOf<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        progressLayout = view.findViewById(R.id.progressLayout)

        progressBar = view.findViewById(R.id.progressBar)

        progressLayout.visibility = View.VISIBLE

        layoutManager = LinearLayoutManager(activity)

        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v1/book/fetch_book/"

        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                // code to handle the response
            try{
                progressLayout.visibility = View.GONE
                val success = it.getBoolean("success")

                if(success){

                    val data = it.getJSONArray("data")
                    for(i in 0 until data.length()){
                        val bookJsonObject = data.getJSONObject(i)
                        val bookObject = Book(
                            bookJsonObject.getString("book_id"),
                            bookJsonObject.getString("name"),
                            bookJsonObject.getString("author"),
                            bookJsonObject.getString("rating"),
                            bookJsonObject.getString("price"),
                            bookJsonObject.getString("image")

                        )
                        bookInfoList.add(bookObject)
                        recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)

                        recyclerDashboard.adapter = recyclerAdapter

                        recyclerDashboard.layoutManager = layoutManager

                    }
                }else{
                    Toast.makeText(activity as Context ,"Some Error Occured !!" , Toast.LENGTH_SHORT).show()
                }

            }catch (e: JSONException){
                Toast.makeText(activity as Context , "Unexpected Error Occurred !!" , Toast.LENGTH_SHORT).show()
            }

            }, Response.ErrorListener {

                // code to handle the error
                Toast.makeText(activity as Context, "Volley Error Occured", Toast.LENGTH_SHORT).show()

            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
//                    headers["token"] = "64ce7cd37cffa3"
                    headers["token"] = "9bf534118365f1"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        }else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not found")
            dialog.setPositiveButton("Open Settings"){text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }

            dialog.setNegativeButton("Exit") {text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()

        }




        return view
    }

     }

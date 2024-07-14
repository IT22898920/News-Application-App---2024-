package com.example.newsapplication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.database.NewsDatabase
import com.example.newsapplication.repository.newsRepositary
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Adapter(items:MutableList<NewsDatabase> , repositary:newsRepositary , viewModel: MainActivityData):RecyclerView.Adapter<viewNewsHolder>(){

    var context : Context? = null
    val items = items
    val repositary= repositary

    val viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewNewsHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_news,parent,false)

        context = parent.context

        return viewNewsHolder(view)
    }
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: viewNewsHolder, position: Int) {
        holder.newsTitle.text = items.get(position).Title

        holder.newsDescription.text = items.get(position).Description

        holder.newsThumbnail.setImageResource(R.drawable.mike)

        holder.bookmarkIcon.setOnClickListener {

            val id = items.get(position).id
            Toast.makeText(context, "$id", Toast.LENGTH_SHORT).show()

            val news = items[position]
            val fragment = NewsDetailFragment.newInstance(news)
            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer2, fragment) // Assuming 'fragmentContainer' is the ID of your FrameLayout in the activity
                .addToBackStack(null) // This will allow you to navigate back to the list
                .commit()
        }

        holder.Deletebtn.setOnClickListener{

            AlertDialog.Builder(context)
                .setTitle("Delete News")
                .setMessage("Are you sure you want to delete this news?")
                .setPositiveButton("OK") { _, _ ->
                    // Using lifecycleScope to launch the coroutine
                    (context as? AppCompatActivity)?.lifecycleScope?.launch {
                        // Delete the news
                        val newsToDelete = items.get(position)
                        repositary.delete(newsToDelete)

                        items.removeAt(position)
                        // Ensure this is a suspend function
                        withContext(Dispatchers.Main) {

                            notifyItemRemoved(position)
                            Toast.makeText(context, "News deleted successfully", Toast.LENGTH_SHORT).show()

                        }

                    }


                }

                .setNegativeButton("Cancel", null)
                .show()
        }
    }

}
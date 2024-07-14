package com.example.newsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.database.NewsDatabase
import com.example.newsapplication.database.newsDetails
import com.example.newsapplication.repository.newsRepositary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private lateinit var viewModel:MainActivityData


class MainActivity : AppCompatActivity() {

    private var allNews: List<NewsDatabase> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val recyclerView: RecyclerView = findViewById(R.id.rvToDoList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[MainActivityData::class.java]
        val repository = newsRepositary(newsDetails.getInstance(this))

          viewModel.data.observe(this){tados ->
            val adapter = Adapter(tados,repository,viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllnews()
            runOnUiThread{
                viewModel.setData(data)
            }
        }

        val btnadd : Button = findViewById(R.id.btnadd)


        btnadd.setOnClickListener {
            displayDialog(repository)
        }

        val btnWorld : Button = findViewById(R.id.btnWorld)
        val btnBusiness : Button = findViewById(R.id.btnBusiness)
        val btnTech : Button = findViewById(R.id.btnTech)
        val btnSports : Button =findViewById(R.id.btnSports)
        val refresh : ImageView = findViewById(R.id.refresh)
        val search : androidx.appcompat.widget.SearchView = findViewById(R.id.searchView)
        val bgColor : ImageView = findViewById(R.id.icon)


        bgColor.setOnClickListener{
            loadChangeColor()
        }





        search.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterNewsByTitle(newText ?: "")
                return true
            }
        })

        btnWorld.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                // Fetch news items with category "world"
                val worldNewsList = repository.getCetogary("World")

                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    viewModel.setData(worldNewsList)
                    Toast.makeText(this@MainActivity, "World News Fetched", Toast.LENGTH_SHORT).show()
                }
            }

        }
        btnSports.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                // Fetch news items with category "world"
                val SportsNewsList = repository.getCetogary("Sports")

                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    viewModel.setData(SportsNewsList)
                    Toast.makeText(this@MainActivity, "Sports News Fetched", Toast.LENGTH_SHORT).show()
                }
            }

        }
        refresh.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                // Fetch news items with category "world"
                val AllNewsList = repository.getAllnews()

                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    viewModel.setData(AllNewsList)
                    Toast.makeText(this@MainActivity, "All News Fetched", Toast.LENGTH_SHORT).show()
                }
            }

        }

        btnBusiness.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                // Fetch news items with category "world"
                val BusinessNewsList = repository.getCetogary("Business")

                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    viewModel.setData(BusinessNewsList)
                    Toast.makeText(this@MainActivity, "Business News Fetched", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnTech.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                // Fetch news items with category "world"
                val TechNewsList = repository.getCetogary("Tech")

                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    viewModel.setData(TechNewsList)
                    Toast.makeText(this@MainActivity, "Techie News Fetched", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
    private fun loadChangeColor() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val colorFragmentInstance = ColorFragment()

        if (fragment == null) {
            // If the fragment is not added, add it
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, colorFragmentInstance)
                .commit()
        } else {
            // If the fragment is already added, toggle its visibility
            if (fragment.isVisible) {
                supportFragmentManager.beginTransaction()
                    .hide(fragment)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .show(fragment)
                    .commit()
            }
        }
    }


    private fun filterNewsByTitle(query: String) {
        val filteredNews = allNews.filter { it.Title?.contains(query, ignoreCase = true) == true }
        viewModel.setData(filteredNews.toMutableList())
    }

    private fun displayDialog(repository: newsRepositary) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Details:")

        // Inflate the custom layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input, null)

        val normalInput = dialogView.findViewById<EditText>(R.id.normalInput)
        val optionInput = dialogView.findViewById<Spinner>(R.id.optionInput)
        val textAreaInput = dialogView.findViewById<EditText>(R.id.textAreaInput)

        // For demonstration, I'm adding sample options to the Spinner
        val options = arrayOf("Sports", "World", "Business","Tech" , "Animal")
        optionInput.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)

        builder.setView(dialogView)

        // Set the positive button action
        builder.setPositiveButton("OK") { dialog, which ->
            val normalText = normalInput.text.toString()
            val selectedOption = optionInput.selectedItem.toString()
            val textAreaText = textAreaInput.text.toString()

            // Assuming NewsDatabase has fields for normalText, selectedOption, and textAreaText
            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(NewsDatabase(normalText, selectedOption, textAreaText))

                val data = repository.getAllnews()

                runOnUiThread {
                    viewModel.setData(data)
                    allNews = data
                    Toast.makeText(this@MainActivity, "News added successfully", Toast.LENGTH_SHORT).show()


                }
            }
        }

        // Set the negative button action
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        // Create and show the alert dialog
        val alertDialog = builder.create()
        alertDialog.show()
    }

}


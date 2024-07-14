package com.example.newsapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.database.NewsDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetailFragment : Fragment() {

    private lateinit var news: NewsDatabase

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_news_detail,container,false)

        val viewModel = ViewModelProvider(requireActivity()).get(MainActivityData::class.java)

        viewModel.getBackgroundColor().observe(viewLifecycleOwner, { color ->
            rootView.setBackgroundColor(color)
        })

        return rootView
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        news = arguments?.getSerializable("newsData") as NewsDatabase

        view.findViewById<ImageView>(R.id.closeview).apply {
            setImageResource(R.drawable.close_24)
            setOnClickListener {
                // Close the fragment
                fragmentManager?.beginTransaction()?.remove(this@NewsDetailFragment)?.commit()
            }
        }
        view.findViewById<TextView>(R.id.newsTitle).text = news.Title
        view.findViewById<TextView>(R.id.newsDescription).text = news.Description
        view.findViewById<TextView>(R.id.newsCetogary).text = news.cetogary
        view.findViewById<ImageView>(R.id.newsImage).setImageResource(R.drawable.worldcup)
        // Set the image, description, date, and source similarly
    }

    companion object {
        fun newInstance(news: NewsDatabase): NewsDetailFragment {
            val fragment = NewsDetailFragment()
            val args = Bundle()
            args.putSerializable("newsData", news)
            fragment.arguments = args
            return fragment
        }
    }
}
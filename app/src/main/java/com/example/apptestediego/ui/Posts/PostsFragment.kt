package com.example.apptestediego.ui.Posts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptestediego.R
import com.example.apptestediego.adapter.PostsAdapter
import com.example.apptestediego.database.DataBasePosts
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment : Fragment() {

    private lateinit var postsViewModel: PostsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        postsViewModel =
                ViewModelProvider(this).get(PostsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_posts, container, false)

        postsViewModel.text.observe(viewLifecycleOwner, Observer {
            var database = DataBasePosts(activity as Context)
            var data = database.listaItens()

            rvPosts.layoutManager = LinearLayoutManager(
                    activity as Context,
                    LinearLayoutManager.VERTICAL,
                    false
            )
            rvPosts.adapter = PostsAdapter(data)
        })
        return root
    }
}
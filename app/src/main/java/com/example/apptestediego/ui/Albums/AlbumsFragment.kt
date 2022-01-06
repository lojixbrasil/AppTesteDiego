package com.example.apptestediego.ui.Albums

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
import com.example.apptestediego.adapter.AlbumsAdapter
import com.example.apptestediego.database.DataBaseAlbums
import kotlinx.android.synthetic.main.fragment_albums.*


class AlbumsFragment : Fragment() {

    private lateinit var albumsViewModel: AlbumsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        albumsViewModel =
                ViewModelProvider(this).get(AlbumsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_albums, container, false)
        albumsViewModel.text.observe(viewLifecycleOwner, Observer {
            var database = DataBaseAlbums(activity as Context)
            var data = database.listaItens()

            rvAlbums.layoutManager = LinearLayoutManager(
                    activity as Context,
                    LinearLayoutManager.VERTICAL,
                    false
            )
            rvAlbums.adapter = AlbumsAdapter(data)
        })
        return root
    }
}
package com.example.apptestediego.ui.Todos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptestediego.R
import com.example.apptestediego.adapter.AlbumsAdapter
import com.example.apptestediego.adapter.TodosAdapter
import com.example.apptestediego.database.DataBaseAlbums
import com.example.apptestediego.database.DataBaseTodos
import kotlinx.android.synthetic.main.fragment_albums.*
import kotlinx.android.synthetic.main.fragment_todos.*

class TodosFragment : Fragment() {

    private lateinit var todosViewModel: TodosViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        todosViewModel =
                ViewModelProvider(this).get(TodosViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_todos, container, false)
        todosViewModel.text.observe(viewLifecycleOwner, Observer {

            var database = DataBaseTodos(activity as Context)
            var data = database.listaItens()

            rvTodos.layoutManager = LinearLayoutManager(
                    activity as Context,
                    LinearLayoutManager.VERTICAL,
                    false
            )
            rvTodos.adapter = TodosAdapter(data)

        })
        return root
    }
}
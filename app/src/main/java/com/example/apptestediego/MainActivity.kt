package com.example.apptestediego

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptestediego.adapter.PostsAdapter
import com.example.apptestediego.database.DataBaseAlbums
import com.example.apptestediego.database.DataBasePosts
import com.example.apptestediego.database.DataBaseTodos
import com.example.apptestediego.model.AlbumsResponseModel
import com.example.apptestediego.model.PostsResponseModel
import com.example.apptestediego.model.TodosResponseModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_posts.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    var databasePosts = DataBasePosts(this)
    var databaseAlbums = DataBaseAlbums(this)
    var databaseTodos = DataBaseTodos(this)
    lateinit var dialog: Dialog

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        dialog = Dialog(this)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(
                view,
                "Desenvolvido por Diego Simões\ndiegoresolvet@gmail.com",
                Snackbar.LENGTH_LONG
            )
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        getData(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun getData(c: Context) {
        exibirDialog(c, false)
        pbCarregar?.visibility = View.VISIBLE
        pbCarregar?.isIndeterminate = true

        var conclusaoChamada1 = false
        var conclusaoChamada2 = false
        var conclusaoChamada3 = false

        val retrofitClient1 = NetworkUtils
            .getRetrofitInstance("https://jsonplaceholder.typicode.com")
        val endpoint1 = retrofitClient1.create(NetworkUtils.EndpointPosts::class.java)
        val callback1 = endpoint1.getPosts()
        callback1.enqueue(object : Callback<List<PostsResponseModel>> {
            override fun onFailure(call: Call<List<PostsResponseModel>>, t: Throwable) {
                Toast.makeText(c, "Falha no recebimento dos dados: ${t.message}", Toast.LENGTH_LONG)
                    .show()
                conclusaoChamada1 = true
                fecharProgressBar(conclusaoChamada1, conclusaoChamada2, conclusaoChamada3)
            }

            override fun onResponse(
                call: Call<List<PostsResponseModel>>,
                response: Response<List<PostsResponseModel>>
            ) {
                if (response.isSuccessful) {
                    var listPosts = response.body()
                    if (listPosts != null) {
                        databasePosts.deleteAll()
                        for (item in listPosts) {
                            databasePosts.addItem(item)
                        }
                        Toast.makeText(c, "Dados recebidos com sucesso!", Toast.LENGTH_LONG).show()

                        rvPosts.layoutManager = LinearLayoutManager(
                            c,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        rvPosts.adapter = PostsAdapter(listPosts)
                    }
                }
                conclusaoChamada1 = true
                fecharProgressBar(conclusaoChamada1, conclusaoChamada2, conclusaoChamada3)
            }
        })

        val retrofitClient2 = NetworkUtils
            .getRetrofitInstance("https://jsonplaceholder.typicode.com")
        val endpoint2 = retrofitClient2.create(NetworkUtils.EndpointAlbums::class.java)
        val callback2 = endpoint2.getAlbums()
        callback2.enqueue(object : Callback<List<AlbumsResponseModel>> {
            override fun onFailure(call: Call<List<AlbumsResponseModel>>, t: Throwable) {
                Toast.makeText(c, "Falha no recebimento dos dados: ${t.message}", Toast.LENGTH_LONG)
                    .show()
                conclusaoChamada2 = true
                fecharProgressBar(conclusaoChamada1, conclusaoChamada2, conclusaoChamada3)
            }

            override fun onResponse(
                call: Call<List<AlbumsResponseModel>>,
                response: Response<List<AlbumsResponseModel>>
            ) {
                if (response.isSuccessful) {
                    var listAlbums = response.body()
                    if (listAlbums != null) {
                        databaseAlbums.deleteAll()
                        for (item in listAlbums) {
                            databaseAlbums.addItem(item)
                            Toast.makeText(c, "Dados recebidos com sucesso!", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
                conclusaoChamada2 = true
                fecharProgressBar(conclusaoChamada1, conclusaoChamada2, conclusaoChamada3)
            }
        })

        val retrofitClient3 = NetworkUtils
            .getRetrofitInstance("https://jsonplaceholder.typicode.com")
        val endpoint3 = retrofitClient3.create(NetworkUtils.EndpointTodos::class.java)
        val callback3 = endpoint3.getTodos()

        callback3.enqueue(object : Callback<List<TodosResponseModel>> {
            override fun onFailure(call: Call<List<TodosResponseModel>>, t: Throwable) {
                Toast.makeText(c, "Falha no recebimento dos dados: ${t.message}", Toast.LENGTH_LONG)
                    .show()
                conclusaoChamada3 = true
                fecharProgressBar(conclusaoChamada1, conclusaoChamada2, conclusaoChamada3)
            }

            override fun onResponse(
                call: Call<List<TodosResponseModel>>,
                response: Response<List<TodosResponseModel>>
            ) {
                if (response.isSuccessful) {
                    var listTodos = response.body()
                    if (listTodos != null) {
                        databaseTodos.deleteAll()
                        for (item in listTodos) {
                            databaseTodos.addItem(item)
                            Toast.makeText(c, "Dados recebidos com sucesso!", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
                conclusaoChamada3 = true
                fecharProgressBar(conclusaoChamada1, conclusaoChamada2, conclusaoChamada3)
            }
        })
    }
    //barra teste
    fun fecharProgressBar(b1: Boolean, b2: Boolean, b3: Boolean) {
        if (b1 && b2 && b3) {
            pbCarregar?.visibility = View.GONE
            exibirDialog(this, true)
        }
    }
    fun exibirDialog(context: Context, fechar: Boolean) {
        val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        if (fechar)
            dialog.dismiss()
        else
            dialog.show()
    }
}
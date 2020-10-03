package com.example.pruebasithec

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    var listaPost: ArrayList<Post> = ArrayList()
    var listaUsuarios: ArrayList<Usuario> = ArrayList()
    var arrayAdapter: PostAdapter? = null
    var flagLoading: Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayAdapter = PostAdapter(this, listaPost)

        val list = findViewById<ListView>(R.id.listPost)
        list.adapter = arrayAdapter

        this.getUsuarios()
        this.getPost()

        list.setOnItemClickListener{ patern, view, position, id ->
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("post", listaPost[position])
            intent.putExtra("usuario", listaUsuarios[listaPost[position].userId-1])
            startActivity(intent)
        }
    }

    fun getPost(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val postService: GetService = retrofit.create<GetService>(GetService::class.java)
        val call: Call<List<Post?>?>? = postService.getPost()

        call?.enqueue(object : Callback<List<Post?>?>{
            @Override
            override fun onResponse(call: Call<List<Post?>?>?, response: Response<List<Post?>?>?) {
                var i = 0
                for ( post in response?.body()!!) {
                    if (post != null) {
                        val p = Post(post.userId, post.id, post.title, post.body)
                        p.nombreUsuario = listaUsuarios[post.userId-1].name
                        p.username = listaUsuarios[post.userId-1].username
                        listaPost.add(p)
                    };
                }
                arrayAdapter?.notifyDataSetChanged();
            }
            @Override
            override fun onFailure(call: Call<List<Post?>?>?, t: Throwable){

            }
        })
    }

    fun getUsuarios(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val postService: GetService = retrofit.create<GetService>(GetService::class.java)
        val call: Call<List<Usuario?>?>? = postService.getUsuarios()

        call?.enqueue(object : Callback<List<Usuario?>?>{
            @Override
            override fun onResponse(call: Call<List<Usuario?>?>?, response: Response<List<Usuario?>?>?) {
                for ( usuario in response?.body()!!) {
                    if (usuario != null) {
                        listaUsuarios.add(Usuario(usuario.id,usuario.name,usuario.username, usuario.email,usuario.address,usuario.phone,usuario.website,usuario.company))
                    };
                }
                flagLoading = true;
                arrayAdapter?.notifyDataSetChanged();
            }
            @Override
            override fun onFailure(call: Call<List<Usuario?>?>?, t: Throwable){

            }
        })
    }
}
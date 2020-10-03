package com.example.pruebasithec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_perfil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PerfilActivity : AppCompatActivity() {
    var listaPost: ArrayList<Post> = ArrayList()
    var arrayAdapter: PostAdapter? = null
    var usuario: Usuario? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        arrayAdapter = PostAdapter(this, listaPost)

        usuario = intent.getSerializableExtra("usuario") as Usuario

        val list = findViewById<ListView>(R.id.list)
        list.adapter = arrayAdapter

        nombre.text = usuario?.name
        correo.text = usuario?.email
//        calle.text = usuario?.address as CharSequence?
        telefono.text = usuario?.phone
        sitioweb.text = usuario?.website

        this.getPost()

    }

    fun getPost(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/users/${usuario?.id}/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val postService: GetService = retrofit.create<GetService>(GetService::class.java)
        val call: Call<List<Post?>?>? = postService.getPostByIdUser()

        call?.enqueue(object : Callback<List<Post?>?> {
            @Override
            override fun onResponse(call: Call<List<Post?>?>?, response: Response<List<Post?>?>?) {
                var i = 0
                for ( post in response?.body()!!) {
                    if (post != null) {
                        val p = Post(post.userId, post.id, post.title, post.body)
                        p.nombreUsuario = usuario!!.name
                        p.username = usuario!!.username
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
}
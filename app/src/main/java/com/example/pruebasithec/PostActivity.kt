package com.example.pruebasithec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostActivity : AppCompatActivity() {
    var listaComentarios: ArrayList<Comentario> = ArrayList()
    var arrayAdapter: ComentarioAdapter? = null
    var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        arrayAdapter = ComentarioAdapter(this, listaComentarios)
        findViewById<ListView>(R.id.list_comentarios).adapter = arrayAdapter

        val newPost = intent.getSerializableExtra("post") as Post
        val user = intent.getSerializableExtra("usuario") as Usuario

        post = newPost
        post_title.text = post?.title
        post_body.text = post?.body
        post_by.text = "By: ${post?.nombreUsuario}"
        post_id.text = "Id post: ${post?.id}"
        username.text = "Username: ${post?.username}"

        this.getComentarios()

        val handler = View.OnClickListener {view ->
            if(view.id == R.id.post_by || view.id == R.id.username){
                val intent = Intent(this, PerfilActivity::class.java)
                intent.putExtra("usuario", user)
                startActivity(intent)
            }
        }

        post_by.setOnClickListener (handler)
        username.setOnClickListener(handler)
    }

    fun getComentarios(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/posts/${post?.id}/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val postService: GetService = retrofit.create<GetService>(GetService::class.java)
        val call: Call<List<Comentario?>?>? = postService.getComentarios()

        call?.enqueue(object : Callback<List<Comentario?>?> {
            @Override
            override fun onResponse(call: Call<List<Comentario?>?>?, response: Response<List<Comentario?>?>?) {
                for ( comentario in response?.body()!!) {
                    if (comentario != null) {
                        listaComentarios.add(Comentario(comentario.postId, comentario.id, comentario.name, comentario.email, comentario.body))
                    };
                }
                arrayAdapter?.notifyDataSetChanged();
            }
            @Override
            override fun onFailure(call: Call<List<Comentario?>?>?, t: Throwable){

            }
        })
    }
}
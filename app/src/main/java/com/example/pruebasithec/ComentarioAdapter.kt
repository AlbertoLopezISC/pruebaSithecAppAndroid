package com.example.pruebasithec

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_comentario.view.*

class ComentarioAdapter(private val mContext: Context, private val listaComentarios: List<Comentario>): ArrayAdapter<Comentario>(mContext, 0, listaComentarios) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_comentario, parent, false)

        val comentario = listaComentarios[position]

        layout.comentario_name.text = comentario.name
        layout.comentario_body.text = comentario.body
        layout.comentario_email.text = comentario.email
        layout.comentario_id.text = "Id comentario: ${comentario.id}"

        return layout
    }
}
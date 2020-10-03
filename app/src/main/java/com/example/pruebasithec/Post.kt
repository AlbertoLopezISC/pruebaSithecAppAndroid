package com.example.pruebasithec

import java.io.Serializable

class Post(val userId: Int, val id: Int, val title: String, val body: String): Serializable{
    var nombreUsuario: String = ""
        set(value) {
            field = value
        }
    var username: String = ""
        set(value) {
            field = value
        }

}
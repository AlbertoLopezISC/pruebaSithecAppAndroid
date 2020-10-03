package com.example.pruebasithec

import java.io.Serializable

class Usuario(val id: Int, val name: String, val username: String, val email: String,
              val address: Any,  val phone: String, val website: String,  val company: Any): Serializable
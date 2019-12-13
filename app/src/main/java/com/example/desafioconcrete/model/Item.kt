package com.example.desafioconcrete.model

import java.io.Serializable

class Item(

    var id: Int? = null,
    var name:String = " ",
    var description:String = " ",
    var owner: Owner? = null,
    var stargazers_count:Int = 0,
    var forks_count:Int = 0

): Serializable
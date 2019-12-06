package com.example.desafioconcrete.model

class Item(

    var id: Int? = null,
    var name:String = " ",
    var full_name:String = " ",
    var description:String = " ",
    var owner: Owner? = null,
    var stargazers_count:Int = 0,
    var forks_count:Int = 0

)
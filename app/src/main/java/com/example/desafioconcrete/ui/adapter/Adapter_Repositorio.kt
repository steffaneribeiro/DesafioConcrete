package com.example.desafioconcrete.ui.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioconcrete.R
import com.example.desafioconcrete.model.Item
import com.example.desafioconcrete.ui.activity.Activity_pull_request
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_adapter__repositorio.view.*

class Adapter_Repositorio(private val context: Context, var pessoaList: MutableList<Item>? = null):

    RecyclerView.Adapter<Adapter_Repositorio.RepositorioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositorioViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.activity_adapter__repositorio, parent, false)
        return RepositorioViewHolder(view)
    }

    override fun getItemCount() = pessoaList!!.size

    override fun onBindViewHolder(holder: RepositorioViewHolder, position: Int) {
        pessoaList?.get(position)?.let { holder.bindView(it) }

        holder.itemView.setOnClickListener{
            var repositorio = pessoaList?.get(position)
            val intent = Intent(context, Activity_pull_request::class.java)
            val extras = Bundle()
            extras.putSerializable("repositorio",repositorio)
            intent.putExtras(extras)
            context.startActivity(intent)
        }
    }

    class RepositorioViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView_User = itemView.imageView_user
        val textView_nome_repositorio = itemView.textView_nome_repositorio
        val textView_descricao = itemView.textView_descricao
        val textView_stars = itemView.textView_stars
        val textView_forks = itemView.textView_forks
        val textView_user_name = itemView.textView_user_name
        val textView_nome_sobrenome = itemView.textView_nome_sobrenome



        fun bindView(pessoa: Item) {
            textView_nome_repositorio.text = pessoa.name
            textView_descricao.text = pessoa.description
            textView_stars.text = pessoa.stargazers_count.toString()
            textView_forks.text = pessoa.forks_count.toString()
            textView_user_name.text = pessoa.owner?.login
            Picasso.get().load(pessoa.owner?.avatar_url).into(imageView_User)

        }
    }
}

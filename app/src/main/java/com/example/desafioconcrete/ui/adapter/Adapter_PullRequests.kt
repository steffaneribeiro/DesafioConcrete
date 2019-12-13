package com.example.desafioconcrete.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioconcrete.R
import com.example.desafioconcrete.model.PullRequest
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_adapter_pull_request.view.*

class Adapter_PullRequests (private val context: Context, var pessoaList: MutableList<PullRequest>):

    RecyclerView.Adapter<Adapter_PullRequests.PullRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.activity_adapter_pull_request, parent, false)
        return PullRequestViewHolder(view)
    }

    override fun getItemCount() = pessoaList.size

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bindView(pessoaList[position])

        holder.itemView.setOnClickListener{
            var pull = pessoaList.get(position)

            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(pull.html_url)
            context.startActivity(openURL)
        }
    }

    class PullRequestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val pull_nome_repositorio = itemView.pull_nome_repositorio
        val pull_description = itemView.pull_description
        val pull_contact_icon = itemView.pull_contact_icon
        val pull_title = itemView.pull_title
        val pull_user_name = itemView.pull_user_name
        val pull_date = itemView.pull_date

        fun bindView(pessoa: PullRequest) {
            pull_nome_repositorio.text = pessoa.title
            pull_description.text = pessoa.body
            pull_title.text = pessoa.user.login
            pull_date.text = pessoa.created_at
            Picasso.get().load(pessoa.user.avatar_url).into(pull_contact_icon)

        }
    }
}

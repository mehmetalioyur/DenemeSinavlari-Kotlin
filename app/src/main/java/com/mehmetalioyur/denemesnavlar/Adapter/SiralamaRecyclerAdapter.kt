package com.mehmetalioyur.denemesnavlar.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalioyur.denemesnavlar.Model.Siralama
import com.mehmetalioyur.denemesnavlar.R

import kotlinx.android.synthetic.main.siralama_recycler_row.view.*

class  SiralamaRecyclerAdapter(val siralamaList : ArrayList<Siralama>): RecyclerView.Adapter<SiralamaRecyclerAdapter.PostHolder>() {

    class PostHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater =LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.siralama_recycler_row,parent,false)
        return PostHolder(view)



    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.sira_no.text = siralamaList[position].siraNo.toString()
        holder.itemView.sira_isim.text = siralamaList[position].kullaniciAdi
        holder.itemView.sira_net.text = siralamaList[position].netSayisi.toString()

    }

    override fun getItemCount(): Int {
        return siralamaList.size



    }
}

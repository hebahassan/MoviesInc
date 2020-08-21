package com.example.moviesinc.ui.movie_details_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.moviesinc.R
import com.example.moviesinc.model.Cast
import kotlinx.android.synthetic.main.row_actor.view.*
import javax.inject.Inject

class CastAdapter @Inject constructor(private val requestManager: RequestManager): RecyclerView.Adapter<CastAdapter.CastItemView>() {

    private val castList = ArrayList<Cast>()
    private var imagePath = ""

    inner class CastItemView(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(castItem: Cast) {
            requestManager.load("$imagePath${castItem.profilePath}").into(itemView.actorIV)
            itemView.actorName.text = castItem.name
            itemView.characterName.text = castItem.character
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastItemView {
        return CastItemView(LayoutInflater.from(parent.context).inflate(R.layout.row_actor, parent, false))
    }

    override fun getItemCount(): Int = castList.count()

    override fun onBindViewHolder(holder: CastItemView, position: Int) {
        holder.bind(castList[position])
    }

    fun updateCastList(updatedList: List<Cast>) {
        castList.clear()
        castList.addAll(updatedList)
        notifyDataSetChanged()
    }

    fun setImagePath(imagePath: String) {
        this.imagePath = imagePath
    }
}
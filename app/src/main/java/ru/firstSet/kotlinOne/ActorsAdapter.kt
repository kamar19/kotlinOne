package ru.firstSet.kotlinOne

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.firstSet.kotlinOne.Data.Actor

class ActorsAdapter(val actorList: List<Actor>) :
    RecyclerView.Adapter<ActorsAdapter.ActorHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder =
        ActorHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        )

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bind(getItem(position));
    }

    override fun getItemCount(): Int = actorList.size

    private fun getItem(position: Int): Actor = actorList[position]

    class ActorHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val actorName: TextView = itemView.findViewById(R.id.viewHolderActorName)
        private val actorFoto: ImageView = itemView.findViewById(R.id.viewHolderActorFoto)
        fun bind(actor: Actor) {
                Glide
                    .with(itemView)
                    .load(actor.picture)
                    .into(actorFoto)
            actorName.text = actor.name
        }
    }
}



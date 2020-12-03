package ru.firstSet.kotlinOne
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActorsAdapter (context: Context, var actorList: List<Actor>) :
    RecyclerView.Adapter<ActorsAdapter.ActorHolder>() {
    private val inflater: LayoutInflater
    private val actorsLists: List<Actor>
    init {
        inflater = LayoutInflater.from(context)
        actorsLists = actorList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        val view: View = inflater.inflate(R.layout.view_holder_actor, parent, false)
        return ActorHolder(view)
    }


    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bind(getItem(position));
    }

    override fun getItemCount()
            : Int = actorsLists.size

    private fun getItem(position: Int): Actor = actorsLists[position]

    class ActorHolder (itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val actorName: TextView
        private val actorFoto: ImageView

        init {
            this.actorName = itemView.findViewById(R.id.viewHolderActorName)
            this.actorFoto = itemView.findViewById(R.id.viewHolderActorFoto)
        }

        fun bind(actor: Actor) {
            this.actorFoto.setImageResource(actor.actorFoto!!)
            this.actorName.text = actor.actorName
        }
    }

}



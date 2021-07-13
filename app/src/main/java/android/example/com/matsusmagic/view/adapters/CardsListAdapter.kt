package android.example.com.matsusmagic.view.adapters


import android.example.com.matsusmagic.databinding.ItemCardBinding
import android.example.com.matsusmagic.model.Card
import android.example.com.matsusmagic.view.OnCardListener
import androidx.recyclerview.widget.RecyclerView


abstract class CardsListAdapter(val cardsList: ArrayList<Card>) :
    RecyclerView.Adapter<CardsListAdapter.CardViewHolder>(), OnCardListener {


    class CardViewHolder(var view: ItemCardBinding) : RecyclerView.ViewHolder(view.root) {
    }

    fun updateCardList(newCardsList: List<Card>) {
        cardsList.clear()
        cardsList.addAll(newCardsList)
        notifyDataSetChanged()
    }



    override fun getItemCount() = cardsList.size


}
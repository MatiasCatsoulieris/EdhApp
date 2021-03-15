package android.example.com.matsusmagic.view

import android.example.com.matsusmagic.R
import android.example.com.matsusmagic.databinding.ItemCardBinding
import android.example.com.matsusmagic.model.Card
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*

class PrimaryAdapter(cardsList: ArrayList<Card>) : CardsListAdapter(cardsList) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemCardBinding>(inflater, R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.view.card = cardsList[position]
        holder.view.listener = this
    }

    override fun onCardClicked(v: View) {
        val cardId = v.cardId.text.toString()
        val action = SearchFragmentDirections.actionSearchToCard()
        action.cardId = cardId
        Navigation.findNavController(v).navigate(action)
        super.onCardClicked(v)
    }
}
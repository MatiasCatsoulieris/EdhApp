package android.example.com.matsusmagic.view

import android.example.com.matsusmagic.R

import android.example.com.matsusmagic.databinding.ItemDeckBinding
import android.example.com.matsusmagic.model.Card

import android.example.com.matsusmagic.model.Decks
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_deck.view.*


class DecksListAdapter(val decksList: ArrayList<Decks>, val buttonlistener: OnDeleteDeckListener) :
    RecyclerView.Adapter<DecksListAdapter.DeckViewHolder>(), OnCardListener {

    class DeckViewHolder(var view: ItemDeckBinding) : RecyclerView.ViewHolder(view.root) {
    }

    fun updateDeckList(newDecksList: List<Decks>) {
        decksList.clear()
        decksList.addAll(newDecksList)
        notifyDataSetChanged()
    }

    fun insertDeck(deck: Decks) {
        decksList.add(deck)
        notifyDataSetChanged()
    }
    fun deleteDeck(deck: Decks) {
        decksList.remove(deck)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemDeckBinding>(inflater, R.layout.item_deck, parent, false)
        return DeckViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        holder.view.deck = decksList[position]
        holder.view.buttonlistener = buttonlistener
        holder.view.listener = this

    }


    override fun getItemCount() = decksList.size

    override fun onDeckClicked(v: View) {
        val deckId = v.deckIdTextView.text.toString().toInt()
        val action = DecksFragmentDirections.actionDeckToDeckList()
        action.deckId = deckId
        Navigation.findNavController(v).navigate(action)
        super.onDeckClicked(v)
    }

    class OnDeleteDeckListener (val deleteListener: (deck: Decks) -> Unit) {
        fun onDeleteClicked(deck: Decks) = deleteListener(deck)
    }

}
package android.example.com.matsusmagic.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.matsusmagic.R
import android.example.com.matsusmagic.databinding.FragmentDecksBinding
import android.example.com.matsusmagic.model.Decks
import android.example.com.matsusmagic.viewmodel.DeckViewModel
import android.view.Window
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_create_deck.view.*

class DecksFragment : Fragment() {

    private lateinit var viewmodel: DeckViewModel
    private var _binding: FragmentDecksBinding? = null
    private val binding get() = _binding!!
    private val decksListAdapter = DecksListAdapter(arrayListOf(), DecksListAdapter.OnDeleteDeckListener {
        deleteDeck(it)
    })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDecksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(DeckViewModel::class.java)
        binding.deckslist.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = decksListAdapter
        }
        viewmodel.retrieveDecks()
        observeViewModel()
        binding.newdeckbutton.setOnClickListener {
            newDeckPopUp(it)
        }
    }

    private fun observeViewModel() {
        viewmodel.decksLiveData.observe(viewLifecycleOwner, { Decks ->
            Decks?.let {
                decksListAdapter.updateDeckList(Decks)
            }
        })
    }

    private fun newDeckPopUp(v: View) {
        val customDialog = Dialog(v.context)
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = layoutInflater.inflate(R.layout.dialog_create_deck, null, false)
        customDialog.addContentView(
            view,
            RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        )
        view.createButton.setOnClickListener {
            if (view.deckNameEdit.text.toString().isNotEmpty()) {
                val deckName = view.deckNameEdit.text.toString()
                val deck = Decks(0, deckName,0,0.0, 0.0)
                viewmodel.createDeck(deck)
                decksListAdapter.insertDeck(deck)
                Toast.makeText(context, "Deck created", Toast.LENGTH_SHORT).show()
                customDialog.dismiss()
            } else {
                Toast.makeText(context, "You must enter a name for your deck", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        view.cancelButton.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()

    }

    private fun deleteDeck(deck: Decks) {
        viewmodel.deleteDeck(deck)
        decksListAdapter.deleteDeck(deck)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
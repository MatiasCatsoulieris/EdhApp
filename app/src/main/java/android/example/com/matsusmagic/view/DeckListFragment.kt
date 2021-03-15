package android.example.com.matsusmagic.view

import android.example.com.matsusmagic.databinding.FragmentDeckListBinding
import android.example.com.matsusmagic.model.Card
import android.example.com.matsusmagic.viewmodel.DeckListViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager


class DeckListFragment : Fragment() {

    private var deckId: Int = 0
    private lateinit var viewmodel: DeckListViewModel
    private var _binding: FragmentDeckListBinding? = null
    private val binding get() = _binding!!
    private val cardsListAdapter = SecondaryAdapter(arrayListOf(), SecondaryAdapter.OnDeleteCardListener { card: Card ->
        deleteCardFromDeck(card, deckId)
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeckListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProviders.of(this).get(DeckListViewModel::class.java)
        arguments?.let {
            deckId = DeckListFragmentArgs.fromBundle(it).deckId
        }
        binding.cardsList2.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cardsListAdapter
        }

        viewmodel.refresh(deckId)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewmodel.cardList.observe(viewLifecycleOwner, { cards ->
            cards?.let {
                cardsListAdapter.updateCardList(cards)
                binding.cardsList2.visibility = View.VISIBLE
            }
        })
        viewmodel.cardsLoadError.observe(viewLifecycleOwner, { isError ->
            isError?.let {
                binding.listError2.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewmodel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                binding.loadingView2.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError2.visibility = View.GONE
                    binding.cardsList2.visibility = View.GONE
                }
            }
        })
    }

    private fun deleteCardFromDeck(card: Card, deckId: Int) {
        viewmodel.deleteCardFromDeck(card, deckId)
        cardsListAdapter.removeItem(card)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
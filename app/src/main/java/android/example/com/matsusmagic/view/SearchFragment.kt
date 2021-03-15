package android.example.com.matsusmagic.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.matsusmagic.databinding.FragmentSearchBinding
import android.example.com.matsusmagic.model.Card
import android.example.com.matsusmagic.viewmodel.SearchViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val cardsListAdapter = PrimaryAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        binding.cardsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cardsListAdapter
        }
        binding.searchbutton.setOnClickListener {
            val cardname = cardNameEditText.text.toString()
            viewModel.refresh(cardname)

        }
        binding.refreshlayout.setOnRefreshListener {
            cardsList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            val cardname = cardNameEditText.text.toString()
            viewModel.refresh(cardname)
            refreshlayout.isRefreshing = false
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.cards.observe(viewLifecycleOwner, { cards: List<Card> ->
            cards.let {
                binding.cardsList.visibility = View.VISIBLE
                cardsListAdapter.updateCardList(cards)
            }
        })
        viewModel.cardsLoadError.observe(viewLifecycleOwner, { isError ->
            isError?.let {
                binding.listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listError.visibility = View.GONE
                    binding.cardsList.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
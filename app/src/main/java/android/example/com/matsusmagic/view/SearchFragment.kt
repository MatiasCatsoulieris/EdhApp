package android.example.com.matsusmagic.view


import android.example.com.matsusmagic.R
import android.example.com.matsusmagic.databinding.FragmentSearchBinding
import android.example.com.matsusmagic.model.Card
import android.example.com.matsusmagic.util.hideKeyboard
import android.example.com.matsusmagic.view.adapters.PrimaryAdapter
import android.example.com.matsusmagic.viewmodel.SearchViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel<SearchViewModel>()
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

        binding.cardsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cardsListAdapter
        }
        binding.cardNameEditText.doAfterTextChanged { text ->
            if (text.toString().length >= 3) {
                viewModel.getCardNamesForAutoComplete(text.toString().toLowerCase(Locale.ROOT))
            }
        }
        binding.cardNameEditText.setOnEditorActionListener { v, actionId, _ ->
            var action = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                v?.hideKeyboard()
                val cardname = binding.cardNameEditText.text.toString()
                viewModel.fuzzyCardSearch(cardname)
                action = true
            }
            action
        }


        binding.searchbutton.setOnClickListener {
            val cardname = binding.cardNameEditText.text.toString()
            it.hideKeyboard()
            viewModel.refresh(cardname)

        }
        binding.refreshlayout.setOnRefreshListener {
            binding.cardsList.visibility = View.GONE
            binding.listError.visibility = View.GONE
            binding.loadingView.visibility = View.VISIBLE
            val cardname = binding.cardNameEditText.text.toString()
            viewModel.refresh(cardname)
            binding.refreshlayout.isRefreshing = false
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.cardNames.observe(viewLifecycleOwner, { cardnames ->
            cardnames.let {
                val arrayAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_list,
                    cardnames.distinct().toList().sorted()
                )
                binding.cardNameEditText.setAdapter(arrayAdapter)
                binding.cardNameEditText.setOnItemClickListener { parent, view, position, id ->
                    val name = arrayAdapter.getItem(position).toString()
                    viewModel.refresh(name)
                }
            }
        })
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
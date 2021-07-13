package android.example.com.matsusmagic.view



import android.app.Dialog
import android.content.Intent
import android.example.com.chatapp.util.CharsToIconUtil
import android.example.com.matsusmagic.R
import android.example.com.matsusmagic.databinding.FragmentCardBinding
import android.example.com.matsusmagic.model.Card
import android.example.com.matsusmagic.util.getProgressDrawable
import android.example.com.matsusmagic.util.loadImageBig
import android.example.com.matsusmagic.viewmodel.CardViewModel
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_card.*
import kotlinx.android.synthetic.main.dialog_create_deck.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardFragment : Fragment() {

    private var cardId: String = "0"
    private val viewmodel: CardViewModel by viewModel()
    private lateinit var currentcard: Card
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            cardId = CardFragmentArgs.fromBundle(it).cardId
        }
        viewmodel.fetchCard(cardId)
        observeViewModel()
        setListeners()


    }

    private fun setListeners() {
        binding.addToDeckButton.setOnClickListener {
            viewmodel.getDecks()
        }
        binding.imageView.setOnClickListener {
            createImageDialog(it)
        }
        binding.gathererButton.setOnClickListener {
            callRelatedPage("gatherer")
        }
        binding.edhrecButton.setOnClickListener {
            callRelatedPage("edhrec")
        }
    }

    private fun observeViewModel() {
        binding.loadingView2.visibility = View.VISIBLE
        viewmodel.cardLiveData.observe(viewLifecycleOwner, { card ->
            card?.let {
                binding.card = card
                currentcard = card
                CharsToIconUtil.setIconInTxtView(binding.oracleTv, card.oracle_text!!,requireContext())
                checkLegalities()
                binding.loadingView2.visibility = View.GONE
                binding.constraintlayout.visibility = View.VISIBLE
            }
        })
        viewmodel.cardRulingsData.observe(viewLifecycleOwner, { rulings ->
            rulings?.let {
                for (rule in rulings.rulingData) {
                    val newRow = TableRow(context)
                    newRow.layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f
                    )
                    val tvrule = TextView(context)
                    tvrule.layoutParams = TableRow.LayoutParams(MATCH_PARENT, MATCH_PARENT, 13.0F)
                    tvrule.setBackgroundResource(R.drawable.table_row_border)
                    tvrule.text = (rule["published_at"] + ": " + rule["comment"])
                    newRow.addView(tvrule)
                    tableLayout.addView(newRow)

                }
            }
        })
        viewmodel.decksLiveData.observe(viewLifecycleOwner, { decks ->
            decks?.let { deckList ->
                val menu = PopupMenu(context, addToDeckButton)
                menu.setOnMenuItemClickListener { item ->
                    menu.dismiss()
                    onItemClicked(item)
                }
                menu.menu.clear()
                menu.menu.add("New Deck")
                for (deck in deckList) {
                    menu.menu.add(deck.deckName)
                }
                menu.show()
            }
        })
    }

    private fun callRelatedPage(page: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentcard.related_uris?.get(page)))
        startActivity(intent)

    }

    // onClick Event for the card image

        private fun createImageDialog(v: View) {
        val customDialog = Dialog(v.context)
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        customDialog.window?.setBackgroundDrawableResource(R.drawable.popupbackground)
        val view = layoutInflater.inflate(R.layout.dialog_card_image, null, false)
        view.findViewById<ImageView>(R.id.imageView2)
            .loadImageBig(binding.card?.image_uris?.large, getProgressDrawable(view.context))
        customDialog.addContentView(
            view,
            RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        )
        customDialog.show()
    }



    private fun checkLegalities() {

        if (currentcard.legalities?.get("standard") == "legal") {
            binding.standardTV.setBackgroundColor(Color.parseColor("#859b66"))
        } else {
            binding.standardTV.setBackgroundColor(Color.parseColor("#cc6d72"))
        }
        if (currentcard.legalities?.get("historic") == "legal") {
            binding.historicTV.setBackgroundColor(Color.parseColor("#859b66"))
        } else {
            binding.historicTV.setBackgroundColor(Color.parseColor("#cc6d72"))
        }
        if (currentcard.legalities?.get("pioneer") == "legal") {
            binding.pioneerTV.setBackgroundColor(Color.parseColor("#859b66"))
        } else {
            binding.pioneerTV.setBackgroundColor(Color.parseColor("#cc6d72"))
        }
        if (currentcard.legalities?.get("modern") == "legal") {
            binding.modernTV.setBackgroundColor(Color.parseColor("#859b66"))
        } else {
            binding.modernTV.setBackgroundColor(Color.parseColor("#cc6d72"))
        }
        if (currentcard.legalities?.get("legacy") == "legal") {
            binding.legacyTV.setBackgroundColor(Color.parseColor("#859b66"))
        } else {
            binding.legacyTV.setBackgroundColor(Color.parseColor("#cc6d72"))
        }
        if (currentcard.legalities?.get("pauper") == "legal") {
            binding.pauperTV.setBackgroundColor(Color.parseColor("#859b66"))
        } else {
            binding.pauperTV.setBackgroundColor(Color.parseColor("#cc6d72"))
        }
        if (currentcard.legalities?.get("vintage") == "legal") {
            binding.vintageTV.setBackgroundColor(Color.parseColor("#859b66"))
        } else {
            binding.vintageTV.setBackgroundColor(Color.parseColor("#cc6d72"))
        }
        if (currentcard.legalities?.get("commander") == "legal") {
            binding.commanderTV.setBackgroundColor(Color.parseColor("#859b66"))
        } else {
            binding.commanderTV.setBackgroundColor(Color.parseColor("#cc6d72"))
        }
    }

    // onClick Event for the new deck menu

    private fun onItemClicked(item: MenuItem): Boolean {
        return when (item.toString()) {
            "New Deck" -> {
                newDeckPopUp()

                true
            }
            else -> {
                val deck = item.title.toString()
                viewmodel.addCardToDeck(currentcard, deck)
                Toast.makeText(context, "Card added to deck", Toast.LENGTH_SHORT).show()
                true
            }
        }
    }



    private fun newDeckPopUp() {
        val customDialog = Dialog(addToDeckButton.context)
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
                viewmodel.createDeck(deckName)
                Toast.makeText(context, "Deck created", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "Card was added to the new deck", Toast.LENGTH_SHORT).show()
                viewmodel.addCardToDeck(currentcard, deckName)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


package android.example.com.matsusmagic.view


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.matsusmagic.R
import android.example.com.matsusmagic.databinding.FragmentMenuBinding
import android.example.com.matsusmagic.util.getProgressDrawable
import android.example.com.matsusmagic.util.loadImageBig
import android.example.com.matsusmagic.viewmodel.MenuViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_menu.*
import java.util.*


class MenuFragment : Fragment() {

    private lateinit var viewmodel: MenuViewModel
    private val MY_PREF_NAME = "My Preferences"
    private var _sharedPreferences: SharedPreferences? = null
    private val sharedPreferences get() = _sharedPreferences!!
    private val DATE: String = "Date"
    private val CARD_ID: String = "Card Id"
    private var youtubeadapter = YouTubeListAdapter(arrayListOf())
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _sharedPreferences = this.context?.getSharedPreferences(MY_PREF_NAME, 0x0000)
        viewmodel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        binding.swiperefreshlayout.setOnRefreshListener {
            viewmodel.getYouTubePlaylist()
            getCommanderOfTheDay()
            binding.swiperefreshlayout.isRefreshing = false
        }
        binding.youtubeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = youtubeadapter
        }
        binding.morecontenttextview.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(MenuFragmentDirections.actionMenuFragmentToCommunityContentFragment())
        }
        binding.searchbutton.setOnClickListener {
            Navigation.findNavController(it).navigate(MenuFragmentDirections.actionMenutoSearch())
        }

        binding.decksbutton.setOnClickListener {
            Navigation.findNavController(it).navigate(MenuFragmentDirections.actionMenuToDecks())
        }
        viewmodel.getYouTubePlaylist()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        getCommanderOfTheDay()

    }
    /* Find a new card for the opening fragment if
    *  day changes
    */
    private fun getCommanderOfTheDay() {

        val editor = sharedPreferences.edit()
        val lastTimeStarted = sharedPreferences.getInt(DATE, -1)
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_YEAR + 1)
        if (today != lastTimeStarted) {
            viewmodel.getCommander()
            editor?.putInt(DATE, today)
            editor?.apply()
        } else {
            if (commandercardimage.drawable == null) {
                val commanderUri = sharedPreferences.getString(
                    CARD_ID,
                    "3a1d0dad-18a8-489e-ac11-08f64b72fda4"
                ) ?: "ce4ec853-411d-40a3-84a7-a62b3cb57cb3"
                viewmodel.getCommanderByCachedId(commanderUri)
            }
        }

    }
    private fun observeViewModel() {
        viewmodel.cardLiveData.observe(viewLifecycleOwner, { card ->
            card?.let {
                binding.loadingViewMenu.visibility = View.GONE
                binding.listErrorMenu.visibility = View.GONE
                binding.commandercardimage.loadImageBig(
                    card.image_uris.large,
                    getProgressDrawable(commandercardimage.context)
                )
                binding.commandercardimage.visibility = View.VISIBLE
                binding.usdTextView.text = getString(R.string.usdPrice, card.prices?.usd)
                binding.tixTextView.text = getString(R.string.tixPrice, card.prices?.tix)
                val editor = sharedPreferences.edit()
                editor?.putString(CARD_ID, card.card_id)
                editor?.apply()
                binding.commandercardimage.setOnClickListener { view ->
                    val cardID = card.card_id
                    val action = MenuFragmentDirections.actionMenuToCard()
                    action.cardId = cardID
                    Navigation.findNavController(view).navigate(action)
                }
            }
        })
        viewmodel.playListData.observe(viewLifecycleOwner, { playlist ->
            playlist?.let { channelList ->
                youtubeadapter.updateCardList(channelList)
                binding.youtubeList.visibility = View.VISIBLE

            }
        })
        viewmodel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                binding.loadingViewMenu.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.listErrorMenu.visibility = View.GONE
                    binding.youtubeList.visibility = View.GONE
                }
            }
        })
        viewmodel.isError.observe(viewLifecycleOwner, { isError ->
            isError?.let {
                binding.listErrorMenu.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}



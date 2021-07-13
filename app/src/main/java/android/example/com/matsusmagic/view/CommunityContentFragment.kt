package android.example.com.matsusmagic.view

import android.example.com.matsusmagic.databinding.FragmentCommunityContentBinding
import android.example.com.matsusmagic.view.adapters.YouTubeListAdapter
import android.example.com.matsusmagic.viewmodel.CommunityContentViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_community_content.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CommunityContentFragment : Fragment() {

    private val yTAdapter = YouTubeListAdapter(arrayListOf())
    private val viewmodel : CommunityContentViewModel by viewModel()
    private var _binding: FragmentCommunityContentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = yTAdapter
            }

        viewmodel.refresh()
        observeViewModel()

    }
    private fun observeViewModel() {
        viewmodel.playListData.observe(viewLifecycleOwner, { playlist ->
            playlist?.let { channelList ->

                yTAdapter.updateCardList(channelList)
                contentRecyclerView.visibility = View.VISIBLE
                loadingViewContent.visibility = View.GONE
                listErrorContent.visibility = View.GONE
            }
        })
        viewmodel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                loadingViewContent.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listErrorContent.visibility = View.GONE
                    contentRecyclerView.visibility = View.GONE
                }
            }
        })
        viewmodel.error.observe(viewLifecycleOwner, { isError ->
            isError?.let {
                listErrorContent.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
package android.example.com.matsusmagic.view

import android.example.com.matsusmagic.R
import android.example.com.matsusmagic.databinding.FragmentViewPagerBinding
import android.example.com.matsusmagic.view.adapters.MyPagerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : Fragment() {

    private lateinit var adapter: MyPagerAdapter
    private lateinit var viewPager2: ViewPager2
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentList = arrayListOf<Fragment>(
            MenuFragment(), SearchFragment(), DecksFragment()
        )
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        viewPager2 = binding.pager
        adapter = MyPagerAdapter(fragmentList, requireActivity())
        viewPager2.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tabLayout = view.findViewById<TabLayout>(R.id.tablayout)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = when(position) {
                0 -> "Home"
                1 -> "Card Search"
                2 -> "My Decks"
                else -> ""
            }
        }.attach()
    }
}
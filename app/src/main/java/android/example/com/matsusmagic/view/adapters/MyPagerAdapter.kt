package android.example.com.matsusmagic.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class MyPagerAdapter(list: ArrayList<Fragment>, fm : FragmentActivity): FragmentStateAdapter(fm) {

    val fragmentList: ArrayList<Fragment> = list


    override fun getItemCount(): Int = fragmentList.size


    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}
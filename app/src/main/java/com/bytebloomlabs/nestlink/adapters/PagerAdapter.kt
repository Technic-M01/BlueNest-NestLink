package com.bytebloomlabs.nestlink.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bytebloomlabs.nestlink.fragments.DataListFragment
import com.bytebloomlabs.nestlink.fragments.SettingsFragment

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> { DataListFragment() }
            1 -> { SettingsFragment() }

            else -> { throw Resources.NotFoundException("Position not found") }
        }
    }

}
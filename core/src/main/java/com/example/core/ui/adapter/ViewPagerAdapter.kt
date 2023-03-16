package com.example.core.ui.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val fragments = mutableListOf<Fragment>()
   val fragmentsTitle = mutableListOf<String>()

    fun addFragment(fragment: Fragment,title:String) {
        this.fragments.add(fragment)
        this.fragmentsTitle.add(title)
        notifyItemInserted(fragments.size-1)
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
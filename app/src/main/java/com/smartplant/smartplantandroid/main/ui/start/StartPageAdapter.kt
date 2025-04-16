package com.smartplant.smartplantandroid.main.ui.start

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smartplant.smartplantandroid.main.ui.start.StartViewModel.StartPageState
import com.smartplant.smartplantandroid.main.ui.start.fragments.FeaturesPageFragment
import com.smartplant.smartplantandroid.main.ui.start.fragments.StartPageFragment
import com.smartplant.smartplantandroid.main.ui.start.fragments.WelcomePageFragment


class StartPageAdapter(
    fragmentActivity: FragmentActivity,
) : FragmentStateAdapter(fragmentActivity) {
    inner class PageDiffCallback(
        val oldList: List<StartPageState>,
        val newList: List<StartPageState>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    private var pages: List<StartPageState> = emptyList()

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment {
        return when (pages[position]) {
            is StartPageState.Welcome -> WelcomePageFragment()
            is StartPageState.Features -> FeaturesPageFragment()
            is StartPageState.Start -> StartPageFragment()
        }
    }

    override fun getItemId(position: Int): Long {
        return pages[position].id.hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return pages.any { it.hashCode().toLong() == itemId }
    }

    fun submitPages(newPages: List<StartPageState>) {
        val diffResult = DiffUtil.calculateDiff(PageDiffCallback(pages, newPages))
        pages = newPages
        diffResult.dispatchUpdatesTo(this)
    }
}

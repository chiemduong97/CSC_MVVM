package com.example.csc_mvvm.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object ActivityUtils {
    fun addFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment, frameId: Int) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    fun addFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int,
        TAG: String?
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment).addToBackStack(TAG)
        transaction.commit()
    }

    fun replaceFragmentInActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)
        transaction.commit()
    }

    fun replaceFragmentInActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int,
        tag: String?
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment, tag)
        transaction.commit()
    }

    fun popFragment(fragmentManager: FragmentManager) {
        fragmentManager.popBackStack()
    }

    fun addFragmentToActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        frameId: Int,
        tag: String?
    ) {
        if (!fragmentManager.popBackStackImmediate(tag, 0)) {
            addFragment(fragmentManager, fragment, frameId, tag)
        }
    }
}
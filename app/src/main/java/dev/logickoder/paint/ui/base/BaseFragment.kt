package dev.logickoder.paint.ui.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * The base class for fragments
 */
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    abstract val binding: ViewBinding
}
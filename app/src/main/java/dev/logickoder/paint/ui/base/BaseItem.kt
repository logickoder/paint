package dev.logickoder.paint.ui.base

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * The base item to be used in recycler views especially when they are displaying different item types
 *
 * @property item The data item this base item is bound to
 * @property itemId A unique integer used to differentiate this [BaseItem] from other [BaseItem]s,
 * so it should always be unique if not it can lead to bugs in your code
 * @property uniqueId Used to compare items when diffing so RecyclerView knows how to animate
 */
abstract class BaseItem<T, VB : ViewBinding>(
    val item: T,
    val itemId: Int,
    val uniqueId: Any
) {

    /**
     * Inflate this [BaseItem]'s view and bind it to a [ViewBinding]
     *
     * @param parent the parent of the view to inflate
     *
     * @return the [ViewBinding] this [BaseItem] is bound to
     * */
    abstract fun inflate(parent: ViewGroup): VB

    /**
     * Binds this item to the recycler view
     *
     * @param holder the view holder
     * */
    abstract fun bind(holder: BaseViewHolder<*>)

    override fun equals(other: Any?): Boolean {
        return if (other !is BaseItem<*, *>) false else other.item == item
    }

    override fun hashCode() = item.hashCode()

}
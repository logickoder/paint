package dev.logickoder.paint.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * Base List Adapter that should help to easily create a list adapter without the over head of
 * writing a new class
 */
open class BaseListAdapter : ListAdapter<BaseItem<*, *>, BaseViewHolder<*>>(

    AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<BaseItem<*, *>>() {
        override fun areItemsTheSame(oldItem: BaseItem<*, *>, newItem: BaseItem<*, *>): Boolean {
            return oldItem.uniqueId == newItem.uniqueId
        }

        override fun areContentsTheSame(oldItem: BaseItem<*, *>, newItem: BaseItem<*, *>): Boolean {
            return oldItem == newItem
        }
    }).build()

) {
    private var lastItemForViewTypeLookup: BaseItem<*, *>? = null

    override fun getItemViewType(position: Int) = getItem(position).itemId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return BaseViewHolder(getItemForViewType(viewType).inflate(parent))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        getItem(position).bind(holder)
    }

    /**
     * Copied from Groupie library:
     *
     * This idea was copied from Epoxy. :wave: Bright idea guys!
     *
     * Find the model that has the given view type so we can create a viewholder for that model.
     *
     * To make this efficient, we rely on the RecyclerView implementation detail that [ListAdapter.getItemViewType]
     * is called immediately before [ListAdapter.onCreateViewHolder]. We cache the last model
     * that had its view type looked up, and unless that implementation changes we expect to have a
     * very fast lookup for the correct model.
     *
     * To be safe, we fallback to searching through all models for a view type match. This is slow and
     * shouldn't be needed, but is a guard against RecyclerView behavior changing.
     */
    private fun getItemForViewType(viewType: Int): BaseItem<*, *> {
        if (lastItemForViewTypeLookup?.itemId == viewType) {
            // We expect this to be a hit 100% of the time
            return lastItemForViewTypeLookup as BaseItem<*, *>
        }
        // To be extra safe in case RecyclerView implementation details change...
        lastItemForViewTypeLookup = currentList.firstOrNull {
            it.itemId == viewType
        } ?: throw IllegalStateException("Could not find model for view type: $viewType")
        return lastItemForViewTypeLookup as BaseItem<*, *>
    }
}
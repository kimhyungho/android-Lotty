package com.anseolab.lotty.view.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.anseolab.lotty.BR
import com.anseolab.lotty.utils.gilde.GlideUtil

abstract class BaseRecyclerViewAdapter<E : UiModel>(
    protected val viewTypes: Array<out ViewType>
) : ListAdapter<Differable, BaseRecyclerViewAdapter.BaseViewHolder<E>>(
    Differable.diffCallback
) {
    protected var _recyclerView: RecyclerView? = null

    private var _layoutInflater: LayoutInflater? = null
    protected val layoutInflater: LayoutInflater
        get() = _layoutInflater!!

    protected open val hasStableIds: Boolean = true

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        _layoutInflater = LayoutInflater.from(recyclerView.context)
        _recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this._recyclerView = null
        this._layoutInflater = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<E>) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<E>) {
        holder.onDetach()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: BaseViewHolder<E>) {
        GlideUtil.clear(holder.viewDataBinding.root)
        holder.onRecycle()
        super.onViewRecycled(holder)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == RecyclerView.NO_POSITION) {
            return super.getItemViewType(position)
        }

        val item = getItem(position)
        if (item !is Viewable) {
            throw IllegalStateException("item must be instance of ${Viewable::class.qualifiedName}")
        }
        return this.viewType(item.viewType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getItemId(position: Int): Long {
        if (position == RecyclerView.NO_POSITION) {
            return super.getItemId(position)
        }
        return (getItem(position) as E).hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<E> {
        return onCreateViewHolder(
            layoutInflater,
            this.viewType(viewType),
            parent
        )
    }

    protected open fun onBindListener(viewHolder: BaseViewHolder<E>){
        //bind user listener here.
    }

    @Suppress("UNCHECKED_CAST")
    protected fun getCurrentItem(viewHolder: BaseViewHolder<E>): E?{
        return getItem(viewHolder.layoutPosition) as? E
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<E>, position: Int) {
        if (position == RecyclerView.NO_POSITION) return
        onBindViewHolder(holder, getItem(position) as E)
    }

    @Suppress("UNCHECKED_CAST")
    open fun onBindViewHolder(holder: BaseViewHolder<E>, item: E) {
        holder.onBind(item)
    }

    fun viewType(viewType: Int): ViewType {
        return this.viewTypes[viewType]
    }

    fun viewType(viewType: ViewType): Int {
        return viewType.viewType
    }

    protected open fun onCreateViewHolder(
        inflater: LayoutInflater,
        viewType: ViewType,
        parent: ViewGroup
    ): BaseViewHolder<E> {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            viewType.layoutResId,
            parent,
            false
        )
        onViewCreated(viewType, viewDataBinding)
        return (object : BaseViewHolder<E>(viewDataBinding) {
            override val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
        }).also { viewHolder ->
            this.onBindListener(viewHolder)
        }
    }

    protected open fun onViewCreated(viewType: ViewType, viewDataBinding: ViewDataBinding){

    }

    abstract class BaseViewHolder<E : UiModel>(
        val viewDataBinding: ViewDataBinding
    ) : RecyclerView.ViewHolder(viewDataBinding.root), ViewHolderLifeCycle<E> {
        var nestedRecyclerViewId: Any? = null

        override fun onBind(item: E) {
            viewDataBinding.setVariable(BR.item, item)
            viewDataBinding.executePendingBindings()
        }
    }
}
package io.luxus.animation.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.luxus.animation.databinding.ItemAnimationBinding
import io.luxus.animation.domain.model.AnimationModel
import io.luxus.animation.presentation.view.custom.adapter.RecyclerViewAdapter

class AnimationListAdapter(private val animationList: List<AnimationModel>)
    : RecyclerViewAdapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        return AnimationViewHolder(ItemAnimationBinding.inflate(inflater, parent, false));
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val animationModel: AnimationModel = animationList[position]
        (holder as AnimationViewHolder).bind(animationModel);
    }

    override fun getItemId(position: Int): Long =
            animationList[position].hashCode().toLong()

    override fun getItemCount(): Int =
            animationList.size


    class AnimationViewHolder(private val binding: ItemAnimationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(animationModel: AnimationModel) {
            binding.model = animationModel
        }

    }

}
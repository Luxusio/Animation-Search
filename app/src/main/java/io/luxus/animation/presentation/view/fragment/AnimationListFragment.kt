package io.luxus.animation.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.luxus.animation.MainApplication
import io.luxus.animation.databinding.FragmentAnimationListBinding
import io.luxus.animation.presentation.viewmodel.AnimationListViewModel

class AnimationListFragment : Fragment() {


    private val viewModel: AnimationListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //MainApplication.app.appComponent.inject(this)

        val binding: FragmentAnimationListBinding = FragmentAnimationListBinding.inflate(inflater, container, false)



        return binding.root
    }

}
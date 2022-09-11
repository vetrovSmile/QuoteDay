package com.example.quoteday.presentation.favoritefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quoteday.databinding.FragmentFavoriteBinding
import com.example.quoteday.domain.model.QuotesItem
import com.example.quoteday.presentation.QuotesApplication
import com.example.quoteday.presentation.favoritefragment.adapter.QuotesFavoriteAdapter
import com.example.quoteday.presentation.utils.ViewModalFactory
import javax.inject.Inject


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModal: ViewModalFavoriteFragment
    private lateinit var viewAdapterFavoriteQuotes: QuotesFavoriteAdapter

    @Inject
    lateinit var viewModalFactory: ViewModalFactory

    private val component by lazy {
        (requireActivity().application as QuotesApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModal = ViewModelProvider(this, viewModalFactory)[ViewModalFavoriteFragment::class.java]

        initRecycleView()

        viewModal.favoriteQuotes.observe(viewLifecycleOwner) {
            viewAdapterFavoriteQuotes.submitList(it)

        }

    }

    private fun initRecycleView() {

        val rvAdapter = binding.rvFavoriteQuotes
        viewAdapterFavoriteQuotes = QuotesFavoriteAdapter()

        rvAdapter.adapter = viewAdapterFavoriteQuotes

        viewAdapterFavoriteQuotes.onClickListenerDeleteQuote =
            object : QuotesFavoriteAdapter.OnClickListenerDeleteQuote {
                override fun onClickDeleteQuote(quotesItem: QuotesItem) {
                    viewModal.deleteQuotes(quotesItem)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
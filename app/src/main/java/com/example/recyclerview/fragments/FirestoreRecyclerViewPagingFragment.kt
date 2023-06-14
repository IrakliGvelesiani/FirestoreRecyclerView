package com.android.recyclerview.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.firestorerecyclerviewsample.model.Fruit
import com.android.firestorerecyclerviewsample.ui.adapters.FruitListPagingAdapter
import com.example.recyclerview.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import android.os.Bundle as Bundle1


class FirestoreRecyclerViewPagingFragment : Fragment() {

    private lateinit var fruitsAdapter: FruitListPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle1?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle1?) {
        super.onViewCreated(view, savedInstanceState)

        val query = FirebaseFirestore.getInstance()
            .collection("Fruits")
            .orderBy("name", Query.Direction.DESCENDING)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(5)
            .setPageSize(5) // No of items loaded from datasource
            .build()
        val options = FirestorePagingOptions.Builder<Fruit>()
            .setQuery(query, config, Fruit::class.java)
            .build()

        fruitsAdapter = FruitListPagingAdapter(options)

        rvList.layoutManager = LinearLayoutManager(requireActivity())
        rvList.adapter = fruitsAdapter
    }

    override fun onStart() {
        super.onStart()
        fruitsAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        fruitsAdapter.stopListening()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FirestoreRecyclerViewPagingFragment()
    }
}
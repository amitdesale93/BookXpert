package com.ap.pdfviewerappbookxpert.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ap.pdfviewerappbookxpert.R
import com.ap.pdfviewerappbookxpert.Viewmodels.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment  : Fragment(R.layout.fragment_list) {
    private val viewModel: ItemViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext()) // ✅ Add this

        val adapter = ItemAdapter { item ->
            viewModel.deleteItem(item)  // Call delete function from ViewModel
        }

        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        viewModel.refreshItems()
    }
}
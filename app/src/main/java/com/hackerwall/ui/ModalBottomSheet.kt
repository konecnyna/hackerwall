package com.hackerwall.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hackerwall.R
import com.hackerwall.base.fadeIn
import com.hackerwall.di.HackerWallApp
import com.hackerwall.di.ServiceLocator
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModalBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val serviceLocator: ServiceLocator = (requireContext().applicationContext as HackerWallApp).serviceLocator

        view.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        view.recyclerView.adapter = BottomSheetAdapter(listOf("Loading..."))
        lifecycleScope.launch(Dispatchers.IO) {
            val logs = serviceLocator.provideLogger().getLogs()
            withContext(Dispatchers.Main) {
                view.recyclerView.adapter = BottomSheetAdapter(logs)
            }
        }

    }


    class BottomSheetAdapter(private var items: List<String>) : RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {
        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = items[position]
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_row, parent, false)
            return ViewHolder(itemView)
        }

        class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
            val text: TextView = row.findViewById(R.id.text)
        }
    }
}
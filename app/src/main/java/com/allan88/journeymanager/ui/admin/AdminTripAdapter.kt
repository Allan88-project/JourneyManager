package com.allan88.journeymanager.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allan88.journeymanager.data.model.Trip
import com.allan88.journeymanager.databinding.ItemAdminTripBinding

class AdminTripAdapter(
    private val trips: List<Trip>,
    private val onApprove: (Trip) -> Unit,
    private val onReject: (Trip) -> Unit
) : RecyclerView.Adapter<AdminTripAdapter.AdminTripViewHolder>() {

    inner class AdminTripViewHolder(val binding: ItemAdminTripBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminTripViewHolder {
        val binding = ItemAdminTripBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AdminTripViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminTripViewHolder, position: Int) {

        val trip = trips[position]

        holder.binding.tripRoute.text = trip.toString()
        holder.binding.tripStatus.text = trip.status

        if (trip.status == "PENDING") {
            holder.binding.btnApprove.setOnClickListener {
                onApprove(trip)
            }

            holder.binding.btnReject.setOnClickListener {
                onReject(trip)
            }
        } else {
            holder.binding.btnApprove.visibility = android.view.View.GONE
            holder.binding.btnReject.visibility = android.view.View.GONE
        }
    }

    override fun getItemCount() = trips.size
}
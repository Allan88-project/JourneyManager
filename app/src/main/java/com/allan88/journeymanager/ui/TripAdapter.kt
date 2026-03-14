package com.allan88.journeymanager.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allan88.journeymanager.R
import com.allan88.journeymanager.data.model.Trip

class TripAdapter(
    private val onStart: (Trip) -> Unit,
    private val onEmergency: (Trip) -> Unit,
    private val onComplete: (Trip) -> Unit
) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    private var trips: List<Trip> = emptyList()

    fun submitList(data: List<Trip>) {
        trips = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trip, parent, false)

        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(trips[position])
    }

    override fun getItemCount(): Int = trips.size

    inner class TripViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.textTitle)
        private val status: TextView = view.findViewById(R.id.textStatus)

        private val buttonStart: Button = view.findViewById(R.id.buttonStart)
        private val buttonEmergency: Button = view.findViewById(R.id.buttonEmergency)
        private val buttonComplete: Button = view.findViewById(R.id.buttonComplete)

        fun bind(trip: Trip) {

            title.text = trip.title
            status.text = trip.status

            buttonStart.visibility = View.GONE
            buttonEmergency.visibility = View.GONE
            buttonComplete.visibility = View.GONE

            when (trip.status) {

                "APPROVED" -> {
                    buttonStart.visibility = View.VISIBLE
                    buttonStart.setOnClickListener {
                        onStart(trip)
                    }
                }

                "IN_PROGRESS" -> {
                    buttonEmergency.visibility = View.VISIBLE
                    buttonComplete.visibility = View.VISIBLE

                    buttonEmergency.setOnClickListener {
                        onEmergency(trip)
                    }

                    buttonComplete.setOnClickListener {
                        onComplete(trip)
                    }
                }

                "COMPLETED" -> {
                    // No buttons
                }
            }
        }
    }
}
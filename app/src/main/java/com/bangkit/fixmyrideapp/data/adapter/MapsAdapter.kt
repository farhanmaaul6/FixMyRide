package com.bangkit.fixmyrideapp.data.adapter

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.fixmyrideapp.data.response.NearbyItem
import com.bangkit.fixmyrideapp.data.response.NewsData
import com.bangkit.fixmyrideapp.databinding.DialogMapsBinding
import com.bangkit.fixmyrideapp.databinding.ItemNewsBinding
import com.bangkit.fixmyrideapp.view.maps.RuteActivity

class MapsAdapter: RecyclerView.Adapter<MapsAdapter.MapsViewHolder>() {
    class MapsViewHolder(val binding: DialogMapsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(maps: NearbyItem){
            binding.tvNamaJalan.text = maps.formattedAddress
            binding.tvNamaLokasi.text = maps.name
            binding.ratingBar.rating = maps.rating
            binding.tvRating.text = maps.rating.toString()
            binding.tvPhoneNumber.text = maps.nationalPhoneNumber
            binding.tvLatitude.text = maps.latitude.toString()
            binding.tvLongitude.text = maps.longitude.toString()
        }

    }

    private val maps: MutableList<NearbyItem> = mutableListOf()
    private var navigateClickListener: OnNavigateClickListener? = null

    interface OnNavigateClickListener {
        fun onNavigateClicked(location: NearbyItem)
    }

    fun setOnNavigateClickListener(listener: OnNavigateClickListener) {
        navigateClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapsAdapter.MapsViewHolder {
        val binding = DialogMapsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapsAdapter.MapsViewHolder, position: Int) {
        val maps = maps[position]
        holder.bind(maps)

        holder.binding.toNavigate.setOnClickListener {
            navigateClickListener?.onNavigateClicked(maps)
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(holder.itemView.context, RuteActivity::class.java)
            intent.putExtra(RuteActivity.NAME, maps.name)
            intent.putExtra(RuteActivity.ADRESS, maps.formattedAddress)
            intent.putExtra(RuteActivity.RATING, maps.rating)
            intent.putExtra(RuteActivity.PHONE_NUMBER, maps.nationalPhoneNumber)
            intent.putExtra(RuteActivity.LATITUDE, maps.latitude)
            intent.putExtra(RuteActivity.LONGITUDE, maps.longitude)

            val optionsCOmpact: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.binding.tvNamaLokasi, "nama"),
                    Pair(holder.binding.tvNamaJalan, "jalan"),
                    Pair(holder.binding.tvRating, "rating"),
                    Pair(holder.binding.tvPhoneNumber, "phone_number"),
                    Pair(holder.binding.tvLatitude, "latitude"),
                    Pair(holder.binding.tvLongitude, "longitude"),
            )
            holder.itemView.context.startActivity(intent, optionsCOmpact.toBundle())
        }


    }

    override fun getItemCount(): Int {
        return maps.size
    }

    fun submitList(newMaps: List<NearbyItem>) {
        maps.clear()
        maps.addAll(newMaps)
        notifyDataSetChanged()
    }
}
package com.amrit.guidomia.ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amrit.guidomia.R
import com.amrit.guidomia.data.database.CarTableDetail
import com.amrit.guidomia.databinding.LayoutCarItemBinding

class CarAdapter : RecyclerView.Adapter<CarAdapter.ViewHolder>(), Filterable {

    var fromMake: Boolean = false
    var fromModel: Boolean = false
    private var previousExpandedPosition: Int = -1
    private var mExpanded = -1
    lateinit var carList: List<CarTableDetail>
    private var carFilteredList: List<CarTableDetail> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_car_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carList: CarTableDetail = carFilteredList[position]
        with(holder)
        {
            binding.modelTV.text = carList.model
            val price = (carList.marketPrice / 1000)
            price.also { binding.priceTV.text = "${it}k" }
            binding.rating.rating = carList.rating.toFloat()
            if (carList.prosList.isEmpty()) {
                holder.binding.prosLL.visibility = View.GONE
            } else {
                holder.binding.proListLL.removeAllViews()
                carList.prosList.forEach {

                    val view = holder.linearLayoutProsCons.inflate(
                        R.layout.layout_list_item,
                        holder.binding.proListLL, false
                    )
                    val name = view.findViewById<TextView>(R.id.nameTV)
                    name.text = it
                    holder.binding.proListLL.addView(view)
                }
            }
            if (carList.consList.isEmpty()) {
                holder.binding.consLL.visibility = View.GONE
            } else {
                holder.binding.consListLL.removeAllViews()
                carList.consList.forEach {

                    val view = holder.linearLayoutProsCons.inflate(
                        R.layout.layout_list_item,
                        holder.binding.consListLL, false
                    )
                    val name = view.findViewById<TextView>(R.id.nameTV)
                    name.text = it
                    holder.binding.consListLL.addView(view)
                }
            }
            when (carList.model) {
                "3300i" -> binding.imageIV.setImageResource(R.drawable.bmw)
                "GLE coupe" -> binding.imageIV.setImageResource(R.drawable.mercedez_benz_glc)
                "Range Rover" -> binding.imageIV.setImageResource(R.drawable.range_rover)
                "Roadster" -> binding.imageIV.setImageResource(R.drawable.alpine_roadster)
                else -> {
                    binding.imageIV.setImageResource(R.drawable.alpine_roadster)
                }
            }

            //expandable
            val isExpanded: Boolean = (position == mExpanded)
            binding.expandLL.visibility = if (isExpanded) View.VISIBLE else View.GONE

            if (isExpanded) previousExpandedPosition = position

            binding.recycleLinearLayout.setOnClickListener {
                mExpanded = if (isExpanded) -1 else position
                notifyItemChanged(previousExpandedPosition)
                notifyItemChanged(position)
            }


        }


    }

    override fun getItemCount(): Int {
        return carFilteredList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<CarTableDetail>) {
        carList = list as ArrayList<CarTableDetail>
        carFilteredList = carList
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = LayoutCarItemBinding.bind(itemView)
        var linearLayoutProsCons: LayoutInflater =
            LayoutInflater.from(itemView.context).context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as LayoutInflater

    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterString = constraint?.toString() ?: ""

                carFilteredList = if (filterString.isEmpty()) carList else {
                    val filteredList = ArrayList<CarTableDetail>()
                    carList
                        .filter {
                            if (fromMake) {
                                it.make.lowercase()
                                    .startsWith(filterString.lowercase())
                            } else {
                                it.model.lowercase()
                                    .startsWith(filterString.lowercase())
                            }

                        }
                        .forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply { values = carFilteredList }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                carFilteredList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as List<CarTableDetail>
                notifyDataSetChanged()
            }

        }
    }

}
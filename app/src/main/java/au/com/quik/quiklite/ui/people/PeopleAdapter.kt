package au.com.quik.quiklite.ui.people

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import au.com.quik.quiklite.R
import au.com.quik.quiklite.model.People
import kotlinx.android.synthetic.main.item_people.view.*

class PeopleAdapter(private val context: Context,
                    private val peopleList: MutableList<People>,
                    private val callback: PeopleAdapter.OnClick) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.item_people, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val person = peopleList[position]
        setName(holder, person.name)
        setCategory(holder, person.category)
        setAge(holder, person.age)
        setRating(holder, person.rating)


        holder!!.removeBtn.setOnClickListener {
            callback.personClicked(person)
        }
    }

    private fun setRating(holder: ViewHolder?, rating: String) {
        holder!!.rating.text = rating.toString()
    }

    private fun setAge(holder: ViewHolder?, age: String) {
        holder!!.age.text = age
    }

    private fun setCategory(holder: ViewHolder?, category: String) {
        holder!!.category.text = category
    }

    private fun setName(holder: ViewHolder?, name: String) {
        holder!!.name.text = name
    }

    fun update(newProducts : List<People>){
        peopleList.clear()
        peopleList.addAll(newProducts)
        refresh()
    }

    private fun refresh(){
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.item_shift_place_txt!!
        val age = itemView.item_shift_value_txt!!
        val category = itemView.item_people_category_txt!!
        val rating = itemView.item_people_rating_txt!!
        val removeBtn = itemView.item_shift_apply_btn!!
    }

    interface OnClick {
        fun personClicked(person : People)
    }

}

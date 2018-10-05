package au.com.quik.quiklite.ui.shift

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import au.com.quik.quiklite.R
import au.com.quik.quiklite.model.Shift
import kotlinx.android.synthetic.main.item_shift.view.*

class ShiftAdapter(private val context: Context,
                   private val shiftList: MutableList<Shift>,
                   private val callback: ShiftAdapter.OnClickShift) : RecyclerView.Adapter<ShiftAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.item_shift, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shiftList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val shift = shiftList[position]
        setCategory(holder, shift.category)
        setValue(holder, shift.value)
        setWhere(holder, shift.place)
        setWhen(holder, shift.date)

        holder!!.applyBtn.setOnClickListener {
            callback.click(shift)
            remove(shift)
        }
    }

    private fun setValue(holder: ViewHolder?, value: String) {
        holder!!.value.text = value
    }

    private fun setWhere(holder: ViewHolder?, where: String) {
        holder!!.where.text = where
    }

    private fun setWhen(holder: ViewHolder?, date: String) {
        holder!!.date.text = date
    }

    private fun setCategory(holder: ViewHolder?, category: String) {
        holder!!.category.text = category
    }

    fun update(shift : Shift){
        //shiftList.clear()
        shiftList.add(shift)
        refresh()
    }

    fun remove(shift: Shift){
        shiftList.remove(shift)
        refresh()
    }

    private fun refresh(){
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val where = itemView.item_shift_place_txt!!
        val date = itemView.item_shift_date_txt!!
        val category = itemView.item_shift_category_txt!!
        val value = itemView.item_shift_value_txt!!
        val applyBtn = itemView.item_shift_apply_btn!!
    }

    interface OnClickShift {
        fun click(shift: Shift)
    }

}

package au.com.quik.quiklite.ui.shift

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import au.com.quik.quiklite.R
import au.com.quik.quiklite.model.Message
import au.com.quik.quiklite.model.Shift
import au.com.quik.quiklite.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_shift.view.*


class ShiftFragment : Fragment() {

    private lateinit var mainActivity : MainActivity
    private lateinit var shiftAdapter: ShiftAdapter
    private lateinit var mainView: View
    private val viewModel = ShiftViewModel()

    companion object {
        const val TAG = "ShiftFragment"
        private const val TWO_COLUMNS = 2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_shift, container, false)
        init()
        return mainView
    }

    private fun init() {
        mainActivity = activity as MainActivity
        setList()
        shiftAdapter = mainView.fragment_shift_rv.adapter as ShiftAdapter
        setupWebSocket()
    }

    private fun setList() {

        with(mainView.fragment_shift_rv){
            adapter = ShiftAdapter(context, mainActivity.shiftList, onClickShift())
            layoutManager = GridLayoutManager(context, TWO_COLUMNS)
        }
    }

    private fun onClickShift(): ShiftAdapter.OnClickShift {
        return object : ShiftAdapter.OnClickShift{
            override fun click(shift: Shift) {
                showToastMessage("Applied!!")
                mainActivity.shiftList.remove(shift)
            }

        }
    }

    private fun setupWebSocket() {
        viewModel.connectToWebSocket(object : ShiftViewModel.WebSocketCallback{
            override fun onConnected() {
                showToastMessage("Web Socket connected")
            }

            override fun onReceived(message: Message) {
                addToList(message)
            }

            override fun onFailed() {
                showToastMessage("Check the internet connection and Server Address and try again")
            }

        })
    }

    fun showToastMessage(message: String) {
        Handler(Looper.getMainLooper()).post(object : Runnable {
            override fun run() {
                Toast.makeText(context,
                        message,
                        Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun addToList(message: Message) {
        Handler(Looper.getMainLooper()).post(object : Runnable {
            override fun run() {
                //mainActivity.shiftList.add(message.message)
                shiftAdapter.update(message.message)
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disconnect()
    }
}
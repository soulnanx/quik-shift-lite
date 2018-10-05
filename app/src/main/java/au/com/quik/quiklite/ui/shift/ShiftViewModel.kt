package au.com.quik.quiklite.ui.shift

import au.com.quik.quiklite.BuildConfig
import au.com.quik.quiklite.model.Message
import com.google.gson.Gson
import com.hosopy.actioncable.ActionCable
import com.hosopy.actioncable.Channel
import java.net.URI


class ShiftViewModel {

    val consumer = ActionCable.createConsumer(URI(BuildConfig.WEBSOCKET_URL))

    fun connectToWebSocket(callback : ShiftViewModel.WebSocketCallback){
        val appearanceChannel = Channel(BuildConfig.CHANNEL)
        val subscription = consumer.subscriptions.create(appearanceChannel)

        subscription
                .onConnected { callback.onConnected() }
                .onReceived { data ->
                    val message = Gson().fromJson(data, Message::class.java)
                    callback.onReceived(message)
                }
                .onFailed {
                    callback.onFailed()
                }

        consumer.connect()
    }

    fun disconnect(){
        consumer.disconnect()
    }

    interface WebSocketCallback{
        fun onConnected()
        fun onReceived(message: Message)
        fun onFailed()
    }

}

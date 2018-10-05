package au.com.quik.quiklite.ui.people

import au.com.quik.quiklite.RetrofitInitializer
import au.com.quik.quiklite.model.People
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeopleViewModel {

    fun getPeople(callback: OnGetPeople) {
        val request = RetrofitInitializer().peopleRemote().getPeople()

        request.enqueue(object : Callback<MutableList<People>>{
            override fun onFailure(call: Call<MutableList<People>>?, t: Throwable?) {
                t?.let {
                    callback.failure(t)
                }
            }

            override fun onResponse(call: Call<MutableList<People>>?, response: Response<MutableList<People>>?) {
                response?.body()?.let {
                    callback.success(it)
                }
            }

        })
    }

    fun removePeople(id : Long, callback: OnDelete){
        val request = RetrofitInitializer().peopleRemote().deletePeople(id)

        request.enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                t?.let {
                    callback.failure(t)
                }
            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                response?.let{
                   if (it.isSuccessful){
                       callback.success()
                    }
                }
            }
        })
    }

    interface OnGetPeople{
        fun success(people: MutableList<People>)
        fun failure(t : Throwable)
    }

    interface OnDelete{
        fun success()
        fun failure(t : Throwable)
    }
}
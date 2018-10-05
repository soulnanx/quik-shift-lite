package au.com.quik.quiklite.ui.addPeople

import au.com.quik.quiklite.RetrofitInitializer
import au.com.quik.quiklite.model.People
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPeopleViewModel {

    fun savePeople(people :People, callback: AddPeopleViewModel.OnSave){
        val request = RetrofitInitializer().peopleRemote().savePeople(people)

        if (invalidForm(people)){
            callback.invalidForm()
            return
        }

        request.enqueue(object : Callback<Void> {
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

    private fun invalidForm(people: People): Boolean {
        return people.name.isNullOrEmpty()
                || people.age.isNullOrEmpty()
                || people.category.isNullOrEmpty()
                || people.rating.isNullOrEmpty()
    }

    interface OnSave {
        fun success()
        fun failure(t :Throwable)
        fun invalidForm()
    }
}
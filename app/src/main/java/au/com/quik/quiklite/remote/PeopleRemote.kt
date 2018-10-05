package au.com.quik.quiklite.remote

import au.com.quik.quiklite.model.People
import retrofit2.Call
import retrofit2.http.*

interface PeopleRemote  {
    @Headers("Accept: application/json")
    @GET("/people")
    fun getPeople(): Call<MutableList<People>>

    @POST("/people")
    fun savePeople(@Body people: People): Call<Void>

    @PUT("/people/{id}")
    fun updatePeople(@Path("id") id: Long): Call<Void>

    @DELETE("/people/{id}")
    fun deletePeople(@Path("id") id: Long): Call<Void>
}
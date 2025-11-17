package com.example.attractive_places_app_s379760.nettverk

import com.example.attractive_places_app_s379760.data.LagretSted
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val BASE_URL =
    "https://dave3600.cs.oslomet.no/~tofri9269/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface ApiService {
    @GET("jsonout.php")
    suspend fun getLagretSted(): List<LagretSted>
    @GET("jsonin.php")
    suspend fun putLagretSted(
        @Query("beskrivelse") beskrivelse: String,
        @Query("positivt") positivt: String,
        @Query("gateadresse") gateadresse: String,
        @Query("gps_koordinater") gpsKoordinater: String
    )
}
object Api {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
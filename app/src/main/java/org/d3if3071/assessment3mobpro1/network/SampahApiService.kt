package org.d3if3071.assessment3mobpro1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3071.assessment3mobpro1.model.OpStatus
import org.d3if3071.assessment3mobpro1.model.Sampah
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://unspoken.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface SampahApiService {
    @GET("api_hafizh.php")
    suspend fun getSampah(
        @Header("Authorization") userId: String
    ): List<Sampah>

    @Multipart
    @POST("api_hafizh.php")
    suspend fun postSampah(
        @Header("Authorization") userId: String,
        @Part("namaSampah") namaSampah: RequestBody,
        @Part("jenisSampah") jenisSampah: RequestBody,
        @Part image: MultipartBody.Part
    ): OpStatus
    @DELETE("api_hafizh.php")
    suspend fun deleteSampah(
        @Header("Authorization") userId: String,
        @Query("id") SampahId: String
    ): OpStatus
}

object SampahApi {
    val service: SampahApiService by lazy {
        retrofit.create(SampahApiService::class.java)
    }
    fun getSampahUrl(imageId: String): String {
        return "${BASE_URL}image.php?id=$imageId"
    }
    enum class Apistatus { LOADING, SUCCESS, FAILED }
}
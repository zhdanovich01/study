package com.example.pictsearch.logic

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StepikApiService {

    @GET("/api/social-accounts")
    fun getSocialAccounts(
        @Header("Authorization") authorization: String,
        @Query("user") userId: String
    ): Call<SocialAccountsResponse>

    data class SocialAccountsResponse(
        val meta: Meta,
        val socialAccounts: List<SocialAccount>
    )

    data class Meta(
        val page: Int,
        val has_next: Boolean,
        val has_previous: Boolean
    )

    data class SocialAccount(
        val id: String,
        val user: String,
        val provider: String,
        val uid: String
    )
}

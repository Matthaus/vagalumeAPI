package com.example.vagalumeapi

import retrofit2.http.GET
import retrofit2.http.Query

interface VagalumeAPI {

    @GET("search.php?apikey=9f69d64dc07d12f73d86e239ca6d72b3")
    suspend fun buscarLetraMusica(
        @Query("art") art: String,
        @Query("mus") mus: String
    ) : ResultadoPesquisa?
}


//tudo q vem acompanhado / idica o Path, tudo q está dps do .com. Qdo encontramos uma ? apartir disso temos as query
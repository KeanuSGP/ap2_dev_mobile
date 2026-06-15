package com.example.ap2_dev_mobile.apiConfig

import com.example.ap2_dev_mobile.dto.CategoriaRequest
import com.example.ap2_dev_mobile.dto.CategoriaResponse
import com.example.ap2_dev_mobile.dto.ContaFinanceiraRequest
import com.example.ap2_dev_mobile.dto.ContaFinanceiraResponse
import com.example.ap2_dev_mobile.dto.DashboardResponse
import com.example.ap2_dev_mobile.dto.LoginRequest
import com.example.ap2_dev_mobile.dto.LoginResponse
import com.example.ap2_dev_mobile.dto.TransacaoRequest
import com.example.ap2_dev_mobile.dto.TransacaoResponse
import com.example.ap2_dev_mobile.dto.TransacaoResumoTelaInicial
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID
import kotlin.String

interface ApiService {


    @POST("auth/login")
    suspend fun login(@Body data: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun cadastrar(): String

    @GET("/dashboard")
    suspend fun dashboard(@Header("Authorization") token: String?): DashboardResponse

    @POST("/contas")
    suspend fun criarContaFinanceira(@Header("Authorization") token: String?, @Body data: ContaFinanceiraRequest): Unit

    @GET("contas/resumo")
    suspend fun getContas(@Header("Authorization") token: String?): MutableList<ContaFinanceiraResponse>

    @PUT("contas/{id}")
    suspend fun atualizarConta(@Header("Authorization") token: String?, @Path("id") id: UUID, @Body data: ContaFinanceiraRequest)

    @DELETE("contas/{id}")
    suspend fun deletarConta(@Header("Authorization") token: String?, @Path("id") id: UUID)

    @GET("categorias/resumo")
    suspend fun getCategorias(@Header("Authorization") token: String?): MutableList<CategoriaResponse>

    @POST("categorias")
    suspend fun criarCategoria(@Header("Authorization") token: String?, @Body data: CategoriaRequest)

    @PUT("categorias/{id}")
    suspend fun atualizarCategoria(@Header("Authorization") token: String?, @Path("id") id: UUID, @Body data: CategoriaRequest)

    @DELETE("categorias/{id}")
    suspend fun deletarCategoria(@Header("Authorization") token: String?, @Path("id") id: UUID): Response<Unit>

    @GET("transacoes")
    suspend fun getTransacoes(@Header("Authorization") token: String?): MutableList<TransacaoResponse>

    @POST("transacoes")
    suspend fun criarTransacao(@Header("Authorization") token: String?, @Body data: TransacaoRequest)

    @GET("categorias")
    suspend fun getAllCategorias(@Header("Authorization") token: String?) : List<CategoriaResponse>

    @GET("contas")
    suspend fun getAllContas(@Header("Authorization") token: String?) : List<ContaFinanceiraResponse>

    @DELETE("transacoes/{id}")
    suspend fun deletarTransacao(@Header("Authorization") token: String?, @Path("id") id: UUID): Response<Unit>

    @PUT("transacoes/{id}")
    suspend fun atualizarTransacao(@Header("Authorization") token: String?, @Path("id") id: UUID, @Body data: TransacaoRequest)
}
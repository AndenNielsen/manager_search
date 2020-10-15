package com.demo.managersearch

import com.demo.managersearch.data.ManagerSearchRepository
import com.demo.managersearch.data.ManagerSearchRepositoryImpl
import com.demo.managersearch.data.api.ManagerSearchAPI
import com.demo.managersearch.ui.main.MainViewModel
import moe.banana.jsonapi2.JsonApiConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<ManagerSearchRepository> { ManagerSearchRepositoryImpl(get()) }
    factory { provideManagerSearchAPI(get()) }

    factory { provideOkHttpClient() }
    single { provideRetrofit(get()) }

    viewModel { MainViewModel() }

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(JsonApiConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
}

val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

fun provideManagerSearchAPI(retrofit: Retrofit): ManagerSearchAPI = retrofit.create(
    ManagerSearchAPI::class.java
)



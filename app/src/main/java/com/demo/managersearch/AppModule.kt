package com.demo.managersearch

import com.demo.managersearch.data.ManagerSearchRepository
import com.demo.managersearch.data.ManagerSearchRepositoryImpl
import com.demo.managersearch.data.api.ManagerSearchAPI
import com.demo.managersearch.data.model.Account
import com.demo.managersearch.data.model.Employee
import com.demo.managersearch.ui.main.MainViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import moe.banana.jsonapi2.JsonApiConverterFactory
import moe.banana.jsonapi2.ResourceAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


@ExperimentalCoroutinesApi
@FlowPreview
val appModule = module {
    single<ManagerSearchRepository> { ManagerSearchRepositoryImpl(get()) }
    factory { provideManagerSearchAPI(get()) }

    factory { provideOkHttpClient() }
    single { provideRetrofit(get()) }

    viewModel { MainViewModel(get()) }

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val jsonApiAdapterFactory: JsonAdapter.Factory = ResourceAdapterFactory.builder()
        .add(Employee::class.java)
        .add(Account::class.java)
        .build()
    val moshi = Moshi.Builder()
        .add(jsonApiAdapterFactory)
        .build()

    return Retrofit.Builder().baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(JsonApiConverterFactory.create(moshi)).build()
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



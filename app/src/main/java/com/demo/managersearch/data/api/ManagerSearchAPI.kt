package com.demo.managersearch.data.api

import com.demo.managersearch.data.model.Employee
import retrofit2.http.GET
import retrofit2.http.Query

interface ManagerSearchAPI {

    @GET("/employees.json")
    suspend fun getEmployees(@Query("q") query: String): List<Employee>

}
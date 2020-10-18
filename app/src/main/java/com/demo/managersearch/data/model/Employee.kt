package com.demo.managersearch.data.model

import com.squareup.moshi.Json
import moe.banana.jsonapi2.HasOne
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "employees")
data class Employee(
    val name: String? = null,
    @field:Json(name = "Business Unit")
    val businessUnit: String? = null,
    @field:Json(name = "Department")
    val department: String? = "",
    @field:Json(name = "Job Level")
    val jobLevel: String? = "",
    @field:Json(name = "Local Office")
    val localOffice: String? = "",
    val account: HasOne<Account>? = null
) : Resource()
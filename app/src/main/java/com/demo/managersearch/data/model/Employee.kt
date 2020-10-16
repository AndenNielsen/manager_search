package com.demo.managersearch.data.model

import moe.banana.jsonapi2.HasOne
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "employees")
data class Employee(
    var name: String? = null,
    val account: HasOne<Account>? = null
) : Resource()
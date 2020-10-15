package com.demo.managersearch.data.model

import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "accounts")
data class Account(
    var email: String? = null
) : Resource()
package com.demo.managersearch.data

import com.demo.managersearch.data.api.ManagerSearchAPI
import com.demo.managersearch.data.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface ManagerSearchRepository {
    suspend fun getEmployeesByQuery(query: String = ""): Flow<List<Employee>>
}

class ManagerSearchRepositoryImpl(private val api: ManagerSearchAPI) :
    ManagerSearchRepository {

    override suspend fun getEmployeesByQuery(query: String): Flow<List<Employee>> {
        return flow {
            val repositoryList = api.getEmployees(query)
            emit(repositoryList)
        }.flowOn(Dispatchers.IO)
    }
}
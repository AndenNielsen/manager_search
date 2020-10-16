package com.demo.managersearch.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.managersearch.data.ManagerSearchRepository
import com.demo.managersearch.data.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ManagerSearchRepository) : ViewModel() {
    val query = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getRepositoriesByQuery()
        }
    }

    private suspend fun getRepositoriesByQuery() {
        query.debounce(300)
            .filter { query ->
                if (query.isEmpty()) {
                    _items.postValue(emptyList())
                    return@filter false
                } else {
                    return@filter true
                }
            }
            .distinctUntilChanged()
            .flatMapLatest { query ->
                repository.getEmployeesByQuery(query)
                    .onStart { /* //todo set loading state? */ }
                    .onCompletion { /* //todo remove loading state? */ }
                    .catch { exception ->
                        Log.e(
                            this@MainViewModel.javaClass.simpleName,
                            "error: $exception",
                            exception
                        )
                    }
                    .map { response ->
                        response.filter {
                            val email = it.account?.get(it.document)?.email ?: ""
                            val name = it.name ?: ""
                            return@filter name.contains(query) || email.contains(query)
                        }.map { it.toUIModel() }
                    }
            }
            .collect { result ->
                Log.d(this@MainViewModel.javaClass.simpleName, "result: $result")
                _items.postValue(result)
            }
    }

    private val _items = MutableLiveData<List<ManagerSearchListItemViewModel>>()
    val items: LiveData<List<ManagerSearchListItemViewModel>> get() = _items

    private fun Employee.toUIModel() =
        ManagerSearchListItemViewModel(
            id = this.id,
            name = this.name ?: "",
            email = this.account?.get(this.document)?.email ?: ""
        )
}
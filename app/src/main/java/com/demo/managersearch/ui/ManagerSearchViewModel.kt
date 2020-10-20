package com.demo.managersearch.ui

import android.util.Log
import androidx.lifecycle.*
import com.demo.managersearch.data.ManagerSearchRepository
import com.demo.managersearch.data.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class ManagerSearchViewModel(private val repository: ManagerSearchRepository) :
    ViewModel() {

    private var allItems = listOf<ManagerSearchListItemViewModel>()

    private val _items = MutableLiveData<List<ManagerSearchListItemViewModel>>()
    val items: LiveData<List<ManagerSearchListItemViewModel>> get() = _items

    val query = MutableStateFlow("")

    val isLoading = MutableLiveData(false)

    val emptyStateVisibility: LiveData<Boolean> =
        Transformations.switchMap(_items) { itemList ->
            Transformations.map(isLoading) { isLoading ->
                !isLoading && itemList.isEmpty() && query.value.isNotEmpty()
            }
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEmployeesByQuery()
                .onStart { isLoading.postValue(true) }
                .onCompletion {
                    isLoading.postValue(false)
                    getManagersByQuery()
                }
                .catch { exception ->
                    emit(emptyList())
                    Log.e(
                        this@ManagerSearchViewModel.javaClass.simpleName,
                        "error: $exception",
                        exception
                    )
                }
                .map { response ->
                    response.map {
                        it.toUIModel()
                    }
                }
                .collect { result ->
                    allItems = result
                }
        }
    }

    private suspend fun getManagersByQuery() {
        query.filter { query ->
            if (query.isEmpty()) {
                _items.postValue(emptyList())
                return@filter false
            } else {
                return@filter true
            }
        }
            .map { query ->
                allItems.filter {
                    return@filter it.name.contains(query, ignoreCase = true) || it.email.contains(
                        query,
                        ignoreCase = true
                    )
                }
            }
            .collect { result ->
                _items.postValue(result)
            }
    }

    private fun Employee.toUIModel() =
        ManagerSearchListItemViewModel(
            id = this.id,
            name = this.name ?: "",
            email = this.account?.get(this.document)?.email ?: "",
            businessUnit = this.businessUnit ?: "",
            department = this.department ?: "",
            jobLevel = this.jobLevel ?: "",
            localOffice = this.localOffice ?: ""
        )
}
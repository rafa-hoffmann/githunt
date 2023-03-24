package com.sonder.githunt.repository_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sonder.githunt.core.common.result.Result
import com.sonder.githunt.core.common.result.asResult
import com.sonder.githunt.core.data.repository.GitHuntRepository
import com.sonder.githunt.core.model.data.Repository
import com.sonder.githunt.repository_list.UiState.Initial
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val repository: GitHuntRepository
) : ViewModel() {
    private val _repositoriesState: MutableStateFlow<UiState<PagingData<Repository>>> =
        MutableStateFlow(Initial)

    val repositoriesState: StateFlow<UiState<PagingData<Repository>>> = _repositoriesState

    init {
        getRepositories()
    }

    private fun getRepositories() {
        viewModelScope.launch {
            repository.getRepositories().cachedIn(viewModelScope).asResult().collect { activityResult ->
                _repositoriesState.update {
                    when (activityResult) {
                        is Result.Success -> UiState.Success(activityResult.data)
                        is Result.Error -> UiState.Error(activityResult.exception)
                        is Result.Loading -> UiState.Loading
                    }
                }
            }
        }
    }
}

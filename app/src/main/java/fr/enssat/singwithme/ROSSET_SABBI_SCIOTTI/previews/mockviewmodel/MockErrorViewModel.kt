package fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.previews.mockviewmodel

import fr.enssat.singwithme.ROSSET_SABBI_SCIOTTI.viewmodel.ErrorViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MockErrorViewModel : ErrorViewModel() {
    private val _mockErrorMessage = MutableStateFlow<String?>("Error")
    override val errorMessage: StateFlow<String?> = _mockErrorMessage

    override fun showError(message: String) {
        _mockErrorMessage.value = message
    }

    override fun clearError() {
        _mockErrorMessage.value = null
    }
}

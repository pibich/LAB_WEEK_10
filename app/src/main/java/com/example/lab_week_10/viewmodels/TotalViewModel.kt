package com.example.lab_week_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel: ViewModel() {
//    var total: Int = 0
//    fun incrementTotal(): Int {
//        total++
//        return total
//    }

    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total

    init {
        _total.postValue(0)
    }

    fun incrementTotal() {
        _total.postValue(_total.value?.plus(1))
    }

    fun setTotal(total: Int) {
        _total.postValue(total)
    }

}
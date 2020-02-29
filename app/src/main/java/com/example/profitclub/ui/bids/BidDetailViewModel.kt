package com.example.profitclub.ui.bids

import RequestQuestionConsultantClose
import RequestQuestionConsultantCloseItem
import RequestQuestionConsultantEnd
import RequestQuestionConsultantEndItem
import RequestQuestionConsultantPreview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.*
import kotlinx.coroutines.launch
import java.lang.Exception

class BidDetailViewModel(val repository: QuestionRepository): ViewModel() {

    val error = MutableLiveData<String>()

   /* val data = MutableLiveData<QuestionConsultantView>().apply {
        value = null
    }*/

    val data = MutableLiveData<ResponseQuestionConsultantPreview>().apply {
        value = null
    }

    val statusComplete = MutableLiveData<ResponseQuestionConsultantEnd>().apply {
        value = null
    }

    val statusCancel = MutableLiveData<ResponseQuestionConsultantClose>().apply {
        value = null
    }


    val postPreview = fun(question_id: Int, lang: String?){
      viewModelScope.launch {
          try {
              val response = repository.postConsultantQuestionPreview(RequestQuestionConsultantPreview(question_id, lang))
              if (response.isSuccessful){
                  data.apply { value = response.body() }
              }
          } catch (e: Exception){
              error.apply { value = e.message.toString() }
          }
      }
    }

    val postComplete = fun(question_id: Int, description_answer: String,
                           description_rate: String, rate: Float){
        viewModelScope.launch {
            try {
                val response = repository.postQuestionConsultantEnd(RequestQuestionConsultantEnd(RequestQuestionConsultantEndItem(question_id, description_answer,
                    description_rate, rate)))
                if (response.isSuccessful){
                    statusComplete.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val postCancel = fun(question_id: Int){
        viewModelScope.launch {
            try {
                val response = repository.postQuestionConsultantClose(RequestQuestionConsultantClose(RequestQuestionConsultantCloseItem(question_id)))
                if (response.isSuccessful){
                    statusCancel.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

 /*   init {
        viewModelScope.launch {
            try {
                val res = repository.getConsultantQuestionView()
                if(res.isSuccessful) {
                    data.apply { value = res.body() }
                }
            } catch (e: Exception) {
                error.apply { value = e.message }
            }
        }
    }*/
}
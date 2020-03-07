package com.example.profitclub.ui.bids

import RequestQuestionConsultantCloseItem
import RequestQuestionConsultantEndItem
import RequestQuestionConsultantPreview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.profitclub.data.questions.QuestionRepository
import com.example.profitclub.data.questions.ResponseQuestionConsultantClose
import com.example.profitclub.data.questions.ResponseQuestionConsultantEnd
import com.example.profitclub.data.questions.ResponseQuestionConsultantPreview
import kotlinx.coroutines.launch
import java.util.*

class BidDetailViewModel(val repository: QuestionRepository): ViewModel() {

    val error = MutableLiveData<String>()

   /* val data = MutableLiveData<QuestionConsultantView>().apply {
        value = null
    }*/

    val data = MutableLiveData<ResponseQuestionConsultantPreview>().apply {
        value = null
    }

    val dataClient = MutableLiveData<ArrayList<ResponseQuestionConsultantPreview>>().apply {
        value = null
    }

    val statusComplete = MutableLiveData<ResponseQuestionConsultantEnd>()?.apply {

    }

    val statusCancel = MutableLiveData<ArrayList<ResponseQuestionConsultantClose>>().apply {

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

    val postPreviewClient = fun(question_id: Int, lang: String?){
        viewModelScope.launch {
            try {
                val response = repository.postClientQuestionPreview(RequestQuestionConsultantPreview(question_id, lang))
                if (response.isSuccessful){
                    dataClient.apply { value = response.body() as ArrayList<ResponseQuestionConsultantPreview> }
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
                val response = repository.postQuestionConsultantEnd(RequestQuestionConsultantEndItem(question_id, description_answer, description_rate, rate))
                if (response.isSuccessful){
                    statusComplete.apply { value = response.body() }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val postCompleteClient = fun(question_id: Int, description_answer: String,
                           description_rate: String, rate: Float){
        viewModelScope.launch {
            try {
                val response = repository.postQuestionClientEnd(RequestQuestionConsultantEndItem(question_id, description_answer, description_rate, rate))
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
                val response = repository.postQuestionConsultantClose(RequestQuestionConsultantCloseItem(question_id))
                if (response.isSuccessful){
                    statusCancel.apply { value = response.body() as ArrayList<ResponseQuestionConsultantClose> }
                }
            } catch (e: Exception){
                error.apply { value = e.message.toString() }
            }
        }
    }

    val postCancelClient = fun(question_id: Int){
        viewModelScope.launch {
            try {
                val response = repository.postQuestionClientClose(RequestQuestionConsultantCloseItem(question_id))
                if (response.isSuccessful){
                    statusCancel.apply { value = response.body() as ArrayList<ResponseQuestionConsultantClose> }
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
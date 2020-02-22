data class RequestQuestionConsultantPreview(
    val question_id: Int,
    val lang: String?
)

data class RequestQuestionConsultantEndItem(
    val question_id: Int,
    val description_answer: String,
    val description_rate: String,
    val rate: Float
)

data class RequestQuestionConsultantEnd(
    val item: RequestQuestionConsultantEndItem
)

data class RequestQuestionConsultantCloseItem(
    val question_id: Int
)

data class RequestQuestionConsultantClose(
    val item: RequestQuestionConsultantCloseItem
)
data class RequestChatSocket(
    val user: Int = 1
)

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

data class RequestQuestionConsultantCloseItem(
    val question_id: Int
)

data class RequestQuestionDisputeOpenItem(
    val question_id: Int,
    val description: String
)

data class RequestQuestionDisputeCloseItem(
    val question_id: Int,
    val true_user_id: Int,
    val answer: String
)

data class GenericRequest<GenericType>(
    val item: GenericType
)
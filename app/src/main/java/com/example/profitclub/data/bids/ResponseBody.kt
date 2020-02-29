data class ConsultantBidsData(
    val question_id: Int,
    val qlang: String,
    val title: String,
    val description: String,
    val categories: ArrayList<String>,
    val question_date: String,
    val status: Int,
    val client_id: Int,
    val click_count: String,
    val click_status: String,
    val createdate: String
)

data class ResponseBidsConsultant(
    val count: Int,
    val data: ArrayList<ConsultantBidsData>
)
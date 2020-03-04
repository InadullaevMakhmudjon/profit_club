data class BidsConsultantPreviewClient(
    val question_id: String
)

data class BidsClientUpdate(
    val bid_id: Int
)

data class BidsConsultantBidItem(
    val question_id: Int,
    val deadline: String,
    val price: Float
)

data class RequestBidsConsultantBidItem(
    val item: BidsConsultantBidItem
)

data class BidsClientAddItem(
    val client_id: Int,
    val categories: ArrayList<Int>,
    val title: String,
    val description: String,
    val language_id: Int,
    val deadline: String
)


data class RequestBidsClientAdd(
    val  item: BidsClientAddItem
)
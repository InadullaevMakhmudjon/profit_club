package com.example.profitclub.ui.transactions

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.profitclub.*
import com.example.profitclub.databinding.FragmentTransactionContainerBinding
import com.example.profitclub.model.UserDetail
import kotlinx.android.synthetic.main.payment_alert_dialog.view.*
import uz.click.mobilesdk.core.ClickMerchant
import uz.click.mobilesdk.core.ClickMerchantConfig
import uz.click.mobilesdk.core.callbacks.ClickMerchantListener
import uz.click.mobilesdk.core.callbacks.ResponseListener
import uz.click.mobilesdk.core.data.CheckoutResponse
import uz.click.mobilesdk.core.data.ConfirmPaymentByCardResponse
import uz.click.mobilesdk.core.data.InitialResponse
import uz.click.mobilesdk.core.data.InvoiceResponse
import uz.click.mobilesdk.impl.paymentoptions.PaymentOptionEnum
import uz.click.mobilesdk.impl.paymentoptions.ThemeOptions

class TransactionContainerFragment : Fragment(), View.OnClickListener {
    private lateinit var preferences: SharedPreferences
    private lateinit var preferencesLang: SharedPreferences
    private val APP_PREFERENCE = "MYSETTINGS"
    private val LANGUAGE = "profitclub"
    private lateinit var binding: FragmentTransactionContainerBinding
    private var mSectionPageAdapter: SectionPageAdapter5? = null
    private val currentUser = UserDetail(0, "", null, false)
    private val productName = "Online Sovetnik"
    private val productDescription = "Пополнить счет"
    private var lang: String? = null
    private var amount: Double? = 0.0
    private var userId: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as MainActivity?
        activity.let {
            activity?.customActionBarTitle(getString(R.string.bids))
        }
        binding = FragmentTransactionContainerBinding.inflate(layoutInflater)
        preferences = activity!!.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        preferencesLang = activity.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE)
         when (preferences.getInt("role", 0)){
           5, 7 -> mSectionPageAdapter = SectionPageAdapter5(childFragmentManager, activity, 1)
           2, 4 -> {
               mSectionPageAdapter = SectionPageAdapter5(childFragmentManager, activity, 2)
               binding.topUp.isVisible = false
           }
       }
        //mSectionPageAdapter = SectionPageAdapter5(childFragmentManager, activity!!)
        binding.viewPager.adapter = mSectionPageAdapter
        binding.pagerHeader.setTabIndicatorColorResource(R.color.colorAccent2)

        userId = preferences.getInt("user_id", 0)
        when (preferencesLang.getString("lang", null)){
            LANGUAGE_RUSSIAN -> lang = "RU"
            LANGUAGE_ENGLISH -> lang = "EN"
            LANGUAGE_UZBEK -> lang = "UZ"
        }

     /*   binding.topUp.setOnClickListener {
            context?.let { it1 -> openApp(it1, "uz.dida.payme") }
        }*/

        binding.topUp.setOnClickListener {
            //context?.let { it1 -> openApp(it1, "uz.dida.payme") }
            alertDialogPayment()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun openApp(context: Context, packageName: String): Boolean {
        val manager = context.packageManager
        try {
            val i = manager.getLaunchIntentForPackage(packageName)
            if (i == null){
                return false
                throw PackageManager.NameNotFoundException()
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            context.startActivity(i)
            return true
        } catch (e: ActivityNotFoundException) {
            return false
        }
    }

    private fun alertDialogPayment(){
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle(getString(R.string.payment_methods))
        val customLayout = layoutInflater.inflate(R.layout.payment_alert_dialog, null)
        alertDialogBuilder.setView(customLayout)
        customLayout.click.setOnClickListener {
            val summa = customLayout.amount_payment.text.toString()
            if (summa != ""){
                amount = summa.toDouble()
                openClick()
            }
        }

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel_alert)) { dialog, which ->
            dialog?.dismiss()
        }

        alertDialogBuilder.show()
    }

    private fun openClick(){
        val config = ClickMerchantConfig.Builder()
            .serviceId(BuildConfig.SERVICE_ID)
            .merchantId(BuildConfig.MERCHANT_ID)
            .amount(amount!!)
            .transactionParam(userId.toString())
            .locale(lang!!)
            .theme(ThemeOptions.LIGHT) //ThemeOptions.NIGHT
            .option(PaymentOptionEnum.USSD)
            .productName(productName)
            .productDescription(productDescription)
            .merchantUserId(BuildConfig.MERCHANT_USER_ID)
            .build()

        ClickMerchant.init(
            childFragmentManager, config,
            object : ClickMerchantListener {
                override fun onReceiveRequestId(id: String) {
                    currentUser.requestId = id
                }

                override fun onSuccess(paymentId: Long) {
                    currentUser.paymentId = paymentId
                    currentUser.paid = true
                }

                override fun onFailure() {
                    currentUser.requestId = ""
                }

                override fun onInvoiceCancelled() {
                    currentUser.requestId = ""
                }
            }
        )
    }

    fun sendInitialRequest(
        serviceId:Long, merchantId:Long,
        amount:Double, transactionParam:String, communalParam:String,
        merchantUserId:Long, language:String, listener:ResponseListener<InitialResponse>
    ){

    }

    fun paymentByUSSD(requestId: String, phoneNumber: String, listener: ResponseListener<InvoiceResponse>){

    }

    fun paymentByCard(requestId: String,  cardNumber: String, expireDate: String,  listener: ResponseListener<InvoiceResponse>){

    }

    fun confirmPaymentByCard(
        requestId: String,
        confirmCode: String,
        listener: ResponseListener<ConfirmPaymentByCardResponse>
    ) {

    }

    fun checkPaymentByRequestId(requestId: String,  listener: ResponseListener<CheckoutResponse>){

    }
}
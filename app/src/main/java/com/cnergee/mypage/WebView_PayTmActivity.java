package com.cnergee.mypage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.cnergee.myapp.instanet.R;
import com.cnergee.mypage.caller.AfterInsertPaymentsCaller;
import com.cnergee.mypage.caller.RenewalCaller;
import com.cnergee.mypage.obj.AdditionalAmount;
import com.cnergee.mypage.obj.PaymentsObj;
import com.cnergee.mypage.utils.AlertsBoxFactory;
import com.cnergee.mypage.utils.Utils;
import com.cnergee.widgets.ProgressHUD;
//import com.paytm.pgsdk.PaytmOrder;
//import com.paytm.pgsdk.PaytmPGService;
//import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;

//import com.paytm.pgsdk.TransactionManager;

public class WebView_PayTmActivity extends BaseActivity {

    private static final String TAG = "WebView_PayTmActivity";

    WebView webView ;

    String mid = "";
    String order_id = "";
    String customer_id = "";
    String industryTypeId = "";
    String channelId = "";
    String txn_amnt = "";
    String website = "";
    String callback_url = "";
    String checkSum = "";
    String PackageName = "";
    String PackageAmount = "";
    String returnURL = "";
    String trackId = "";
    String email = "",Signature="";
    String mobileNo = "",UpdateFrom="",  queryUrl="";


    PaymentsObj paymentsObj = new PaymentsObj();
    public static String rslt = "", responseMsg = "";
    AdditionalAmount additionalAmount;
    String type="Renew";
    String mid_data,orderId,TxnId,responseCode,TxnAmount,ResMsg,Status,CheckSum,bankTxnId;

    public boolean Changepack;
    Bundle bundle;
    Utils utils = new Utils();
    private String sharedPreferences_name;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Intent intent = getIntent();
        mid = intent.getStringExtra("mid");
        order_id = intent.getStringExtra("order_id");
        customer_id = intent.getStringExtra("customer_id");
        industryTypeId = intent.getStringExtra("industryTypeId");
        channelId = intent.getStringExtra("channelId");
        txn_amnt = intent.getStringExtra("txn_amnt");
        website = intent.getStringExtra("website");
        callback_url = intent.getStringExtra("callback_url");
        checkSum = intent.getStringExtra("checkSum");
        PackageName = intent.getStringExtra("PackageName");
        PackageAmount = intent.getStringExtra("PackageAmount");
        returnURL = intent.getStringExtra("returnURL");
        email = intent.getStringExtra("email");
        mobileNo = intent.getStringExtra("mobileNo");
        trackId=intent.getStringExtra("TrackId");
        bundle = intent.getExtras();
        queryUrl = intent.getStringExtra("queryUrl");
        additionalAmount = (AdditionalAmount) bundle.getSerializable("addtional_amount");
        Signature = intent.getStringExtra("Signature");
        sharedPreferences_name = getString(R.string.shared_preferences_name);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(sharedPreferences_name, 0);

        utils.setSharedPreferences(sharedPreferences);

        Log.e("getMemberId",":"+utils.getMemberId());
        Log.e("getMemberLoginID",":"+utils.getMemberLoginID());

        Changepack= intent.getBooleanExtra("Changepack",false);
        UpdateFrom=intent.getStringExtra("updateFrom");
        PackageName=intent.getStringExtra("PackageName");
        PackageAmount=intent.getStringExtra("PackageAmount");

        webView = (WebView)findViewById(R.id.webView1);

        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setCacheMode(2);
        webView.getSettings().setDomStorageEnabled(true);
        webView.clearHistory();
        webView.clearCache(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setUseWideViewPort(false);
        //webView.getSettings().setJavaScriptEnabled(true);
//        paytm();
        //paytmDummy();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }


    @Override
    protected void onStart() {
        super.onStart();
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

//    private void paytmDummy() {
//        String callBackUrl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+order_id;
//        PaytmOrder paytmOrder = new PaytmOrder(order_id, mid, Signature, txn_amnt, "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=AS010000000437PT");
//        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){
//
//            @Override
//            public void onTransactionResponse(Bundle bundle) {
//                Toast.makeText(WebView_PayTmActivity.this, "Response (onTransactionResponse) : "+bundle.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void networkNotAvailable() {
//
//            }
//
//
//            @Override
//            public void clientAuthenticationFailed(String s) {
//
//            }
//
//            @Override
//            public void someUIErrorOccurred(String s) {
//
//            }
//
//            @Override
//            public void onErrorLoadingWebPage(int i, String s, String s1) {
//
//            }
//
//            @Override
//            public void onBackPressedCancelTransaction() {
//
//            }
//
//            @Override
//            public void onTransactionCancel(String s, Bundle bundle) {
//
//            }
//        });
//        transactionManager.setShowPaymentUrl("https://securegw-stage.paytm.in/theia/api/v1/showPaymentPage");
//        transactionManager.startTransaction(this, 101);
//    }
//
//    public void paytm(){
//        PaytmPGService Service = PaytmPGService.getStagingService("");
//       // PaytmPGService Service = PaytmPGService.getProductionService();
//
//        HashMap<String, String> paramMap = new HashMap<String, String>();
//
//        paramMap.put("MID" , mid);
//        paramMap.put("ORDER_ID" , order_id);
//        paramMap.put("CUST_ID" , customer_id);
//        paramMap.put("INDUSTRY_TYPE_ID" ,industryTypeId);
//        paramMap.put("CHANNEL_ID" , channelId);
//        paramMap.put("TXN_AMOUNT" , txn_amnt);
//        paramMap.put("WEBSITE" , website);
//        paramMap.put("CALLBACK_URL" , callback_url);
//        paramMap.put("CHECKSUMHASH" , checkSum);
//
//       /* paramMap.put("MID" , "FBBmAh27427319594058");
//        paramMap.put("ORDER_ID" , "AS010000000437PT");
//        paramMap.put("CUST_ID" , "157");
//        paramMap.put("INDUSTRY_TYPE_ID" ,"WAP");
//        paramMap.put("CHANNEL_ID" , "WAP");
//        paramMap.put("TXN_AMOUNT" , "5");
//        paramMap.put("WEBSITE" , "WEBSTAGING");
//        paramMap.put("CALLBACK_URL" , "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=AS010000000437PT");
//        paramMap.put("CHECKSUMHASH" , checkSum);*/
//
//
//        Utils.log(TAG,"paramMap : "+paramMap.toString());
//        PaytmOrder Order = new PaytmOrder(paramMap);
//
//        Service.initialize(Order, null);
//        Service.startPaymentTransaction(this, true, true,
//                new PaytmPaymentTransactionCallback() {
//
//                    @Override
//                    public void someUIErrorOccurred(String inErrorMessage) {
//                        Utils.log(TAG, "UI Error  : " + inErrorMessage);
//                    }
//
//                    @Override
//                    public void onTransactionResponse(Bundle inResponse) {
//                        Utils.log(TAG, "Payment Transaction : " + inResponse.toString());
//
//                        try{
//                             mid_data = inResponse.getString("MID");
//                             orderId = inResponse.getString("ORDERID");
//                             TxnId =  inResponse.getString("TXNID");
//                             responseCode =  inResponse.getString("RESPCODE");
//                             TxnAmount =  inResponse.getString("TXNAMOUNT");
//                             ResMsg =  inResponse.getString("RESPMSG");
//                             Status =  inResponse.getString("STATUS");
//                             CheckSum =  inResponse.getString("CHECKSUMHASH");
//                             bankTxnId =  inResponse.getString("BANKTXNID","");
//
//                            if(responseCode.contains("01")){
//                                Status="Success";
//                            }else  if(responseCode.contains("141")){
//                                Status="Cancelled";
//                            }else {
//                                Status="Pending";
//                            }
//
//                            paymentsObj.setAuthIdCode(TxnId);
//                            paymentsObj.setTxStatus(Status);
//                            paymentsObj.setTxMsg(ResMsg);
//                            paymentsObj.setTxId(orderId);
//                            paymentsObj.setIssuerRefNo(bankTxnId);
//                            paymentsObj.setAmount(TxnAmount);
//                            paymentsObj.setPgTxnNo(CheckSum);
//
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                                new InsertAfterPayemnt().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (String) null);
//                                Utils.log("InsertPayment", "Insert Pay executed2");
//                            } else {
//                                new InsertAfterPayemnt().execute();
//                                 Utils.log("InsertPayment", "Insert Pay executed3");
//                            }
//
//                        }catch(Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void networkNotAvailable() {
//                        // If network is not
//                        // available, then this
//                        // method gets called.
//                    }
//
//                    @Override
//                    public void clientAuthenticationFailed(String inErrorMessage) {
//
//                    }
//
//                    @Override
//                    public void onErrorLoadingWebPage(int iniErrorCode,
//                                                      String inErrorMessage, String inFailingUrl) {
//
//                    }
//
//                    // had to be added: NOTE
//                    @Override
//                    public void onBackPressedCancelTransaction() {
//                        // TODO Auto-generated method stub
//                        Utils.log(TAG,"back pressed");
//
//                        paymentsObj.setAuthIdCode(trackId);
//                        paymentsObj.setTxStatus("onbackpressed");
//                        paymentsObj.setTxMsg("Cancelled by backpressed");
//                        paymentsObj.setTxId(trackId);
//                        paymentsObj.setIssuerRefNo("");
//                        paymentsObj.setAmount(txn_amnt);
//                        paymentsObj.setPgTxnNo("");
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                            new InsertAfterPayemnt().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (String) null);
//                            Utils.log("InsertPayment", "Insert Pay executed2");
//                        } else {
//                            new InsertAfterPayemnt().execute();
//                            Utils.log("InsertPayment", "Insert Pay executed3");
//                        }
//                    }
//
//                    @Override
//                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//                        Utils.log(TAG, "Payment Transaction Failed " + inErrorMessage);
//                    }
//
//                });
//    }

    private class InsertAfterPayemnt extends AsyncTask<String, Void, Void>
            implements DialogInterface.OnCancelListener {

        ProgressHUD mProgressHUD;


        protected void onPreExecute() {

            Utils.log("Started", " InsertAfterPayment");
//            mProgressHUD = ProgressHUD
//                    .show(WebView_PayTmActivity.this,
//                            getString(R.string.app_please_wait_label), true,
//                            false, this);
        }

        @Override
        protected void onCancelled() {
            mProgressHUD.dismiss();
            // submit.setClickable(true);
        }

        protected void onPostExecute(Void unused) {

            mProgressHUD.dismiss();
            // submit.setClickable(true);

            if (rslt.trim().equalsIgnoreCase("ok")) {

                Utils.log("STATUS AFTER",":"+paymentsObj.getTxStatus());

                if(paymentsObj.getTxStatus().contains("Success")){

                    if (additionalAmount.getFinalcharges() != null) {
                        if (additionalAmount.getAdditionalAmountType() != null) {
                            if (additionalAmount.getAdditionalAmountType().length() > 0) {
                                if (additionalAmount.getAdditionalAmountType().contains("#")) {
                                    String split[] = additionalAmount.getAdditionalAmountType().split("#");
                                    if (split.length > 0) {
                                        type = split[1];
                                    }
                                }
                            } else {

                            }
                        }
                    } else {
                        Utils.log("Additional Amount ", "is null");
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        new RenewalProcessWebService().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (String) null);
                    } else {
                        new RenewalProcessWebService().execute();
                    }

                }else {
                    Utils.log("Call else after",":");
                    Intent intent = new Intent(getApplicationContext(), TransResponseCCAvenue.class);
                    intent.putExtra("ResponseMsg", paymentsObj.getTxStatus());
                    intent.putExtra("order_id",trackId );
                    intent.putExtra("tracking_id",trackId);
                    intent.putExtra("amount", PackageAmount);
                    intent.putExtra("order_status", paymentsObj.getTxMsg());
                    intent.putExtra("bank_ref_no", bankTxnId);
                    intent.putExtra("payment_id", TxnId);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }

            }else{
                Utils.log("Call else after",":");
                Intent intent = new Intent(getApplicationContext(), TransResponseCCAvenue.class);
                intent.putExtra("ResponseMsg", paymentsObj.getTxStatus());
                intent.putExtra("order_id",trackId );
                intent.putExtra("tracking_id",trackId);
                intent.putExtra("amount", PackageAmount);
                intent.putExtra("order_status", paymentsObj.getTxMsg());
                intent.putExtra("bank_ref_no", bankTxnId);
                intent.putExtra("payment_id", TxnId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                AfterInsertPaymentsCaller caller = new AfterInsertPaymentsCaller(
                        getApplicationContext().getResources().getString(
                                R.string.WSDL_TARGET_NAMESPACE),
                        getApplicationContext().getResources().getString(
                                R.string.SOAP_URL), getApplicationContext()
                        .getResources().getString(
                                R.string.METHOD_AFTER_MEMBER_PAYMENTS), true);

                caller.setPaymentdata(paymentsObj);

                caller.join();
                caller.start();
                rslt = "START";

                while (rslt == "START") {
                    try {
                        Thread.sleep(10);
                    } catch (Exception ex) {
                    }
                }

            } catch (Exception e) {
                AlertsBoxFactory.showAlert(rslt, WebView_PayTmActivity.this);

            }
            return null;
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            // TODO Auto-generated method stub
            mProgressHUD.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private class RenewalProcessWebService extends
            AsyncTask<String, Void, Void> implements DialogInterface.OnCancelListener {

        ProgressHUD mProgressHUD;
        ProgressDialog progressDialog;
        PaymentsObj paymentsObj = new PaymentsObj();

        protected void onPreExecute() {

            if(mProgressHUD==null){
                mProgressHUD = ProgressHUD
                        .show(WebView_PayTmActivity.this,
                                "Please Wait...", true,
                                false, this);
            }

			/*progressDialog= new ProgressDialog(WebView_CCAvenueActivity.this);
			progressDialog.show();
			progressDialog.setCancelable(false);*/
        }

        @Override
        protected void onCancelled() {
            mProgressHUD.dismiss();
            //progressDialog.dismiss();
            // submit.setClickable(true);
        }

        protected void onPostExecute(Void unused) {

            mProgressHUD.dismiss();
            // submit.setClickable(true);
            //progressDialog.dismiss();

            if (rslt.trim().equalsIgnoreCase("ok")) {
                // finish();
                WebView_PayTmActivity.this.finish();

                Intent intent = new Intent(getApplicationContext(), TransResponseCCAvenue.class);
                intent.putExtra("ResponseMsg", ResMsg);
                intent.putExtra("order_id", orderId);
                intent.putExtra("tracking_id",mid_data);
                intent.putExtra("amount", PackageAmount);
                intent.putExtra("order_status", Status);
                intent.putExtra("bank_ref_no", bankTxnId);
                intent.putExtra("payment_id", TxnId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            } else {
                AlertsBoxFactory.showAlert(rslt, WebView_PayTmActivity.this);
                return;
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                RenewalCaller caller = new RenewalCaller(
                        getApplicationContext().getResources().getString(
                                R.string.WSDL_TARGET_NAMESPACE),
                        getApplicationContext().getResources().getString(
                                R.string.SOAP_URL),
                        getApplicationContext().getResources().getString(
                                R.string.METHOD_RENEWAL_REACTIVATE_METHOD));


                //paymentsObj.setMobileNumber(utils.getMobileNoPrimary());
                paymentsObj.setMobileNumber(mobileNo);
                paymentsObj.setSubscriberID(utils.getMemberLoginID());
                // System.out.println("-------------Change Package :-----------"
                // + Changepack);

                paymentsObj.setIsChangePlan(Boolean.parseBoolean(String.valueOf(Changepack)));
                paymentsObj.setPlanName(PackageName);
                paymentsObj.setPaidAmount(Double.parseDouble(PackageAmount));
                paymentsObj.setTrackId(trackId);
                paymentsObj.setActionType(UpdateFrom);
                paymentsObj.setPaymentId(TxnId);
                paymentsObj.setTxMsg(responseMsg);

                caller.setPaymentdata(paymentsObj);
                caller.setRenewalType(type);

                caller.join();
                caller.start();
                rslt = "START";

                while (rslt == "START") {
                    try {
                        Thread.sleep(10);
                    } catch (Exception ex) {

                    }
                }

            } catch (Exception e) {
				/* AlertsBoxFactory.showAlert(rslt,context ); */
            }
            return null;
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            // TODO Auto-generated method stub
            //progressDialog.dismiss();
        }
    }

}

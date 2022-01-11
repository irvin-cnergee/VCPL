package com.cnergee.mypage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.cnergee.myapp.instanet.R;
import com.cnergee.mypage.caller.AfterInsertPaymentsCaller;
import com.cnergee.mypage.caller.RenewalCaller;
import com.cnergee.mypage.obj.AdditionalAmount;
import com.cnergee.mypage.obj.PaymentsObj;
import com.cnergee.mypage.utils.AlertsBoxFactory;
import com.cnergee.mypage.utils.Utils;
import com.cnergee.widgets.ProgressHUD;
//import com.payumoney.core.request.PaymentRequest;
//import com.ebs.android.sdk.PaymentRequest;

import org.json.JSONException;
import org.json.JSONObject;




public class PaymentSuccessActivity extends Activity {
	String ebs_response;
	String PaymentId;
	String AccountId;
	String MerchantRefNo;
	String Amount;
	String DateCreated;
	String Description;
	String Mode;
	String IsFlagged;
	String BillingName;
	String BillingAddress;
	String BillingCity;
	String BillingState;
	String BillingPostalCode;
	String BillingCountry;
	String BillingPhone;
	String BillingEmail;
	String DeliveryName;
	String DeliveryAddress;
	String DeliveryCity;
	String DeliveryState;
	String DeliveryPostalCode;
	String DeliveryCountry;
	String DeliveryPhone;
	String PaymentStatus;
	String PaymentMode;
	String SecureHash;
	String ResponseCode;
	String ResponseMsg;
	String Transaction_id;
	String[] description;
	String package_name, change_pkg, update_from, memberLoginId, additional_amt_type;
	String type;

	public static String rslt = "", responseMsg = "";
	PaymentsObj paymentsObj = new PaymentsObj();
	Utils utils = new Utils();
	AdditionalAmount additionalAmount;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_payment_success);

		Intent intent = getIntent();

		ebs_response = intent.getStringExtra("payment_id");
		getJsonReport();

	}

	private void getJsonReport() {
		String response = ebs_response;

		Log.d("response", ":" + response);

		JSONObject jObject;
		try {
			jObject = new JSONObject(response.toString());
//			PaymentRequest.getInstance().setPaymentResponse(response.toString());

			PaymentId = jObject.getString("PaymentId");
			AccountId = jObject.getString("AccountId");
			MerchantRefNo = jObject.getString("MerchantRefNo");
			Amount = jObject.getString("Amount");

			Utils.log("Amount",":"+Amount);

			DateCreated = jObject.getString("DateCreated");
			Description = jObject.getString("Description");
			Mode = jObject.getString("Mode");
			IsFlagged = jObject.getString("IsFlagged");
			BillingName = jObject.getString("BillingName");
			BillingAddress = jObject.getString("BillingAddress");
			BillingCity = jObject.getString("BillingCity");
			BillingState = jObject.getString("BillingState");
			BillingPostalCode = jObject.getString("BillingPostalCode");
			BillingCountry = jObject.getString("BillingCountry");
			BillingPhone = jObject.getString("BillingPhone");
			BillingEmail = jObject.getString("BillingEmail");
			DeliveryName = jObject.getString("DeliveryName");
			DeliveryAddress = jObject.getString("DeliveryAddress");
			DeliveryCity = jObject.getString("DeliveryCity");
			DeliveryState = jObject.getString("DeliveryState");
			DeliveryPostalCode = jObject.getString("DeliveryPostalCode");
			DeliveryCountry = jObject.getString("DeliveryCountry");
			DeliveryPhone = jObject.getString("DeliveryPhone");
			PaymentStatus = jObject.getString("PaymentStatus");
			PaymentMode = jObject.getString("PaymentMode");
			SecureHash = jObject.getString("SecureHash");
			ResponseCode = jObject.getString("ResponseCode");
			ResponseMsg = jObject.getString("ResponseMessage");
			Transaction_id = jObject.getString("TransactionId");
//			PaymentRequest.getInstance().setPaymentId(PaymentId);

			paymentsObj.setAuthIdCode(PaymentId);
			paymentsObj.setTxId(MerchantRefNo);
			paymentsObj.setTxStatus(PaymentStatus);
			paymentsObj.setPgTxnNo(Transaction_id);
			paymentsObj.setIssuerRefNo(Transaction_id);
			paymentsObj.setTxMsg(ResponseMsg);

			description = Description.split("#");
			package_name = description[0];
			change_pkg = description[1];
			update_from = description[2];
			memberLoginId = description[3];
			additional_amt_type = description[4];

			Utils.log("package_name", ":" + package_name);
			Utils.log("change_pkg", ":" + change_pkg);
			Utils.log("update_from", ":" + update_from);
			Utils.log("memberLoginId", ":" + memberLoginId);

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				new InsertAfterPayemnt().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (String) null);
			} else {
				new InsertAfterPayemnt().execute();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	private class InsertAfterPayemnt extends AsyncTask<String, Void, Void>
			implements DialogInterface.OnCancelListener {

		ProgressHUD mProgressHUD;


		protected void onPreExecute() {

			Utils.log("Started", " InsertAfterPayment");
			mProgressHUD = ProgressHUD
					.show(PaymentSuccessActivity.this,
							getString(R.string.app_please_wait_label), true,
							true, this);
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
				Utils.log("EBS RESPONSE", ":" + ResponseMsg);
				Utils.log("EBS RESPONSE CODE", ":" + ResponseCode);
				Utils.log("EBS DESCRIPTION", ":" + Description);

				if (ResponseCode.equalsIgnoreCase("0") || !(Integer.parseInt(ResponseCode) > 0)) {
					if (ResponseMsg.contains("Successful")) {
						if (additional_amt_type.contains("#")) {
							String split[] = additional_amt_type.split("#");
							if (split.length > 0) {
								type = split[1];
							}
						} else {
							type = additional_amt_type;
						}

						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
							new RenewalProcessWebService().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (String) null);
						} else {
							new RenewalProcessWebService().execute();
						}
					} else {

					}
				} else {

				}
			} else {
				PaymentSuccessActivity.this.finish();
				Intent intent = new Intent(getApplicationContext(), TransResponseCCAvenue.class);
				intent.putExtra("transStatus", MerchantRefNo);
				intent.putExtra("order_id", MerchantRefNo);
				intent.putExtra("tracking_id", MerchantRefNo);
				intent.putExtra("amount", Amount);
				intent.putExtra("order_status", PaymentStatus);
				intent.putExtra("bank_ref_no", Transaction_id);
				intent.putExtra("payment_id", PaymentId);
				startActivity(intent);
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				// setCurrDateTime();
				// Log.i(" >>>>> ",getCurrDateTime());

				AfterInsertPaymentsCaller caller = new AfterInsertPaymentsCaller(
						getApplicationContext().getResources().getString(R.string.WSDL_TARGET_NAMESPACE),
						getApplicationContext().getResources().getString(R.string.SOAP_URL), getApplicationContext()
						.getResources().getString(R.string.METHOD_AFTER_MEMBER_PAYMENTS), true);
				caller.setPaymentdata(paymentsObj);
				caller.join();
				caller.start();
				rslt = "START";

				while (rslt == "START") {
					try {
						Thread.sleep(10);
					} catch (Exception ex) { }
				}

			} catch (Exception e) {
				AlertsBoxFactory.showAlert(rslt, PaymentSuccessActivity.this);
			}
			return null;
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub

			mProgressHUD.dismiss();

		}
	}


	private class RenewalProcessWebService extends
			AsyncTask<String, Void, Void> implements DialogInterface.OnCancelListener {

		ProgressHUD mProgressHUD;
		PaymentsObj paymentsObj = new PaymentsObj();

		protected void onPreExecute() {

			if(mProgressHUD==null){
			mProgressHUD = ProgressHUD
					.show(PaymentSuccessActivity.this,
							getString(R.string.app_please_wait), true,
							false, this);
			}

			/*progressDialog= new ProgressDialog(WebView_CCAvenueActivity.this);
			progressDialog.show();
			progressDialog.setCancelable(false);*/
		}

		@Override
		protected void onCancelled() {
			//mProgressHUD.dismiss();
			//progressDialog.dismiss();
			// submit.setClickable(true);
		}

		protected void onPostExecute(Void unused) {

			 mProgressHUD.dismiss();
			// submit.setClickable(true);
			//progressDialog.dismiss();


			if (rslt.trim().equalsIgnoreCase("ok")) {
				// finish();
				PaymentSuccessActivity.this.finish();

				Intent intent = new Intent(getApplicationContext(), TransResponseCCAvenue.class);
				intent.putExtra("transStatus", MerchantRefNo);
				intent.putExtra("order_id", MerchantRefNo);
				intent.putExtra("tracking_id", MerchantRefNo);
				intent.putExtra("amount", Amount);
				intent.putExtra("order_status", PaymentStatus);
				intent.putExtra("bank_ref_no", Transaction_id);
				intent.putExtra("payment_id", PaymentId);
				startActivity(intent);

			} else {
				AlertsBoxFactory.showAlert(rslt, PaymentSuccessActivity.this);
				return;
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				// setCurrDateTime();
				// Log.i(" >>>>> ",getCurrDateTime());

				/*
				 * RenewalCaller caller = new RenewalCaller(
				 * getApplicationContext().getResources().getString(
				 * R.string.WSDL_TARGET_NAMESPACE),
				 * getApplicationContext().getResources().getString(
				 * R.string.SOAP_URL), getApplicationContext()
				 * .getResources().getString(
				 * R.string.METHOD_MYPAGE_RENEWAL_PROCESS))
				 */
				;
				RenewalCaller caller = new RenewalCaller(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
						getApplicationContext().getResources().getString(
								R.string.SOAP_URL),
						getApplicationContext().getResources().getString(
								R.string.METHOD_RENEWAL_REACTIVATE_METHOD));


				paymentsObj.setMobileNumber(BillingPhone);
				paymentsObj.setSubscriberID(memberLoginId);
				// System.out.println("-------------Change Package :-----------"
				// + Changepack);

				paymentsObj.setIsChangePlan(Boolean.parseBoolean(change_pkg));
				paymentsObj.setPlanName(package_name);
				paymentsObj.setPaidAmount(Double.parseDouble(Amount));
				paymentsObj.setTrackId(MerchantRefNo);
				paymentsObj.setActionType(update_from);
				paymentsObj.setPaymentId(PaymentId);
				paymentsObj.setTxMsg(ResponseCode);

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

		/*
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 *
		 * }
		 */

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			//progressDialog.dismiss();
		}
	}


}
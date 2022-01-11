package com.cnergee.mypage.SOAP;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cnergee.mypage.obj.AuthenticationMobile;

public class CitrusSignatureSOAP {
	
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	private String  HMACSignature;
	private long MemberId;
	private String Amount;
	private String TrackId;

	private String serverMessage;

	public CitrusSignatureSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;

		this.METHOD_NAME = METHOD_NAME;
	}

	public String CallCitrusSignatureSOAP() {
		//Log.i("#####################", " START ");
		//Log.i("WSDL_TARGET_NAMESPACE :", WSDL_TARGET_NAMESPACE);
		//Log.i("SOAP_URL :", SOAP_URL);
		//Log.i("SOAP_ACTION :", WSDL_TARGET_NAMESPACE + METHOD_NAME);
		//Log.i("METHOD_NAME :", METHOD_NAME);
		/*Log.i(" IMEI No ", Authobj.getIMEINo());
		Log.i(" Mobile ", Authobj.getMobileNumber());
		Log.i(" Mobile User ", Authobj.getMobLoginId());
		Log.i(" Mobile Password ", Authobj.getMobUserPass());
		Log.i("SubscriberID :", getSubscriberID());
		Log.i("UserLoginName :", getUsername());
		Log.i("NewPlanName :", getNewPlan());
		Log.i("AreaCode :", getAreaCode());
		Log.i("AreaCodeFilter :", getAreaCodeFilter());*/

		//Log.i("#####################", "");

		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

		PropertyInfo pi = new PropertyInfo();
		pi.setName("MemberId");
		pi.setValue(getMemberId());
		pi.setType(long.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("Amount");
		pi.setValue(getAmount());
		pi.setType(String.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("TrackId");
		pi.setValue(getTrackId());
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName(AuthenticationMobile.CliectAccessName);
		pi.setValue(AuthenticationMobile.CliectAccessId);
		pi.setType(String.class);
		request.addProperty(pi);
		

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		//Log.i(">>>>>Request<<<<<", request.toString());
		
		envelope.encodingStyle = SoapSerializationEnvelope.ENC;

		envelope.implicitTypes = true;
		envelope.addMapping(WSDL_TARGET_NAMESPACE, "",
				this.getClass());

		HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
		androidHttpTransport.debug = true;

		try {
			//Log.i("#####################", " CALL ");
			androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,
					envelope);
			if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
				Object response = envelope.getResponse();
				//Log.i(">>>>Response<<<<<",response.toString());
				setServerMessage(response.toString());
				
				/*String ss = androidHttpTransport.responseDump;
				Log.i("#####################", ss);
				*/
				
			} else if (envelope.bodyIn instanceof SoapFault) { // SoapFault =
																// FAILURE
				SoapFault soapFault = (SoapFault) envelope.bodyIn;
				return soapFault.getMessage().toString();
			}
			//Log.i("#####################", " RESPONSE ");
			//Log.i("#####################", getServerMessage());
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	public long getMemberId() {
		return MemberId;
	}

	public void setMemberId(long memberId) {
		MemberId = memberId;
	}

	
	
	
	
	public String getHMACSignature() {
		return HMACSignature;
	}

	public void setHMACSignature(String hMACSignature) {
		HMACSignature = hMACSignature;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getTrackId() {
		return TrackId;
	}

	public void setTrackId(String trackId) {
		TrackId = trackId;
	}

	/**
	 * @return the serverMessage
	 */
	public String getServerMessage() {
		return serverMessage;
	}

	/**
	 * @param serverMessage
	 *            the serverMessage to set
	 */
	public void setServerMessage(String serverMessage) {
		this.serverMessage = serverMessage;
	}


}

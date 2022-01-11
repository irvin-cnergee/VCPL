package com.cnergee.mypage.SOAP;

import com.cnergee.mypage.obj.AuthenticationMobile;
import com.cnergee.mypage.obj.ComplaintObj;
import com.cnergee.mypage.obj.PaymentsObj;
import com.cnergee.mypage.utils.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * Created by Jyoti on 1/29/2019.
 */

public class InsertBeforeWithTrackSOAP {
    private String WSDL_TARGET_NAMESPACE;
    private String SOAP_URL;
    private String METHOD_NAME;
    public PaymentsObj paymentdata;
    private Map<String, ComplaintObj> mapComplaintNo;
    private boolean isAllData;
    private String responseMsg;
    private String serverMessage;
    public long memberid;
    public String TrackId;
    public String Amount;
    public String PackageName;



    public String ServiceTax;


    public InsertBeforeWithTrackSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
                                   String METHOD_NAME) {
        this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
        this.SOAP_URL = SOAP_URL;
        this.METHOD_NAME = METHOD_NAME;
    }


    public String CallComplaintNoSOAP()throws SocketException,SocketTimeoutException,Exception {

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("MemberId");
        pi.setValue(getPaymentdata().getMemberId());
        pi.setType(long.class);
        request.addProperty(pi);

//        pi = new PropertyInfo();
//        pi.setName("TrackId");
//        pi.setValue(getPaymentdata().getTrackId());
//        pi.setType(String.class);
//        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("BankCode");
        pi.setValue(getPaymentdata().getBankcode());
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("Amount");
        pi.setValue(getPaymentdata().getAmount());
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("PackageName");
        pi.setValue(getPaymentdata().getPackageName());
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("ServiceTax");
        pi.setValue(getPaymentdata().getServiceTax());
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("DiscountAmount");
        pi.setValue(getPaymentdata().getDiscount_Amount());
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName(AuthenticationMobile.CliectAccessName);
        pi.setValue(AuthenticationMobile.CliectAccessId);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("PGUniqueId");
        pi.setValue(getPaymentdata().getPg_sms_unique_id());
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("IsPhoneRenewal");
        pi.setValue(getPaymentdata().getIs_renew());
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("RenewalType");
        pi.setValue(getPaymentdata().getRenewaltype());
        pi.setType(String.class);
        request.addProperty(pi);



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);
        envelope.encodingStyle = SoapSerializationEnvelope.ENC;
        envelope.implicitTypes = true;
        envelope.addMapping(WSDL_TARGET_NAMESPACE, "",
                this.getClass());

        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
        androidHttpTransport.debug = true;

        Utils.log("before pay Request",":"+request.toString());
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
                Utils.log("Before Track ID Request",":"+androidHttpTransport.requestDump);
                Utils.log("Before Track ID Response", ":" + androidHttpTransport.responseDump);
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


    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
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


    public long getMemberid() {
        return memberid;
    }


    public void setMemberid(long memberid) {
        this.memberid = memberid;
    }

/*
    public String getTrackId() {
        return TrackId;
    }


    public void setTrackId(String trackId) {
        TrackId = trackId;
    }*/


    public String getAmount() {
        return Amount;
    }


    public void setAmount(String amount) {
        Amount = amount;
    }


    public String getPackageName() {
        return PackageName;
    }


    public void setPackageName(String packageName) {
        PackageName = packageName;
    }


    public String getServiceTax() {
        return ServiceTax;
    }


    public void setServiceTax(String serviceTax) {
        ServiceTax = serviceTax;
    }


    public PaymentsObj getPaymentdata() {
        return paymentdata;
    }


    public void setPaymentdata(PaymentsObj paymentdata) {
        this.paymentdata = paymentdata;
    }





}

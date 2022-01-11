package com.cnergee.mypage.SOAP;

import com.cnergee.mypage.obj.AuthenticationMobile;
import com.cnergee.mypage.obj.RedirectionDetailObj;
import com.cnergee.mypage.utils.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Hashtable;
import java.util.Map;

public class GetRedirectionDetailsSOAP {

    private String WSDL_TARGET_NAMESPACE;
    private String SOAP_URL;
    private String METHOD_NAME;
    private Map<String, RedirectionDetailObj> mapRedirectionDetial;


    public GetRedirectionDetailsSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL, String METHOD_NAME) {
        this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
        this.SOAP_URL = SOAP_URL;
        this.METHOD_NAME = METHOD_NAME;
    }


    public String CallComplaintNoSOAP()throws SocketException, SocketTimeoutException,Exception {

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);



        PropertyInfo pi = new PropertyInfo();
        pi.setName("ClientAccessId");
        pi.setValue(AuthenticationMobile.CliectAccessId);
        pi.setType(String.class);
        request.addProperty(pi);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        Utils.log("redirection", request.toString());
        envelope.setOutputSoapObject(request);
        envelope.encodingStyle = SoapSerializationEnvelope.ENC;
        envelope.implicitTypes = true;
        envelope.addMapping(WSDL_TARGET_NAMESPACE, "", this.getClass());

        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
        androidHttpTransport.debug = true;

        try {
            String str_msg = "ok";
            //Log.i("#####################", " CALL ");
            androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,
                    envelope);

            Utils.log("redirection",":"+androidHttpTransport.requestDump);
            Utils.log("redirection",":"+androidHttpTransport.responseDump);
            if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
                Map<String, RedirectionDetailObj> mapRedirectionDetial = new Hashtable<String, RedirectionDetailObj>();
                SoapObject response = (SoapObject) envelope.getResponse();

                Utils.log("redirection","response:"+response.toString());
                if (response != null) {
                    //Log.i(" RESPONSE ", response.toString());
                    response = (SoapObject) response.getProperty("NewDataSet");
                    if (response.getPropertyCount() > 0) {
                        for (int i = 0; i < response.getPropertyCount(); i++) {
                            SoapObject tableObj = (SoapObject) response.getProperty(i);
                            setredirectionDetails(tableObj, mapRedirectionDetial);
                        }
                        setMapRedirectionDetial(mapRedirectionDetial);

                        str_msg = "ok";
                    } else {
                        str_msg = "not";
                    }
                } else {
                    str_msg = "not";
                }

            } else if (envelope.bodyIn instanceof SoapFault) { // SoapFault =
                // FAILURE
                SoapFault soapFault = (SoapFault) envelope.bodyIn;
                return soapFault.getMessage().toString();
            }

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }

    }

    public void setredirectionDetails(SoapObject response,
                                      Map<String, RedirectionDetailObj> mapRedirectionDetial) {

        RedirectionDetailObj detailObj = new RedirectionDetailObj();

        detailObj.setTremsAndConditions(response.getPropertyAsString("TremsAndConditions").toString());
        detailObj.setPrivacyPolicy(response.getPropertyAsString("PrivacyPolicy").toString());
        detailObj.setCancellationAndRefundPolicy(response.getPropertyAsString("CancellationAndRefundPolicy").toString());
        detailObj.setEnablePolicies(Integer.parseInt(response.getPrimitivePropertyAsString("EnablePolicies").toString()));

        mapRedirectionDetial.put("Redirection", detailObj);
    }

    public void setMapRedirectionDetial(Map<String, RedirectionDetailObj> mapRedirectionDetial) {
        this.mapRedirectionDetial = mapRedirectionDetial;
    }

    public Map<String, RedirectionDetailObj> getMapRedictionDetail() {
        return this.mapRedirectionDetial;
    }






}

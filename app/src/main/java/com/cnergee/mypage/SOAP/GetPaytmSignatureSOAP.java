package com.cnergee.mypage.SOAP;

import com.cnergee.mypage.obj.AuthenticationMobile;
import com.cnergee.mypage.utils.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by Siddhesh on 12/12/2017.
 */

public class GetPaytmSignatureSOAP {

    private String SOAP_URL;
    private String WSDL_TARGET_NAMESPACE;
    private String METHOD_NAME;
    public static String TAG = "GetAtomSignatureSoap";
    public String response;

    public GetPaytmSignatureSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL, String METHOD_NAME){
        this.METHOD_NAME = METHOD_NAME;
        this.SOAP_URL = SOAP_URL;
        this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;

    }

    public String GetPaytmSignatureResult(long MemberId,String TrackId,String Amount)throws Exception,SocketException,SocketTimeoutException {

        String Result = "OK";

        try{
            Utils.log(""+this.getClass().getSimpleName(),":"+SOAP_URL);
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,METHOD_NAME);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("MemberId");
            Utils.log(TAG+"member:",""+MemberId);
            pi.setValue(MemberId);//78846
            //pi.setValue("49874");//78846
            pi.setType(long.class);
            request.addProperty(pi);

            /*pi = new PropertyInfo();
            pi.setName("BankCode");
            pi.setValue(BankName);
            pi.setType(String.class);
            request.addProperty(pi);*/

            pi = new PropertyInfo();
            pi.setName("TrackId");
            pi.setValue(TrackId);
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("Amount");
            pi.setValue(Amount);
            //pi.setValue("1");
            Utils.log("Amount ", "" + Amount);
            pi.setType(String.class);
            request.addProperty(pi);


            pi = new PropertyInfo();
            pi.setName(AuthenticationMobile.CliectAccessName);
            pi.setValue(AuthenticationMobile.CliectAccessId);
            //pi.setValue("CM000196UT");
            pi.setType(String.class);
            request.addProperty(pi);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle =  SoapSerializationEnvelope.ENC;
            envelope.implicitTypes = true;
            envelope.addMapping(WSDL_TARGET_NAMESPACE, "",
                    this.getClass());

            HttpTransportSE transport = new HttpTransportSE(SOAP_URL);
            transport.debug=true;


            transport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME, envelope);
            Utils.log("GEPAYTM request",""+transport.requestDump);
            Utils.log("GEPAYTM response",""+transport.responseDump);

            Utils.log("GetPayTMSignatureSoap",""+request.toString());

            response = envelope.getResponse().toString();
            Utils.log("GetPayTMSignatureSoap", "" + response.toString());
        }catch(Exception e){
            Utils.log("Error",":"+e);
        }



        return Result;

    }

    public String getResponse() {
        // TODO Auto-generated method stub
        return response;
    }
}

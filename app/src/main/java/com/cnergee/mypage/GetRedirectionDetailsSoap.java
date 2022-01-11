package com.cnergee.mypage;

import com.cnergee.mypage.obj.AuthenticationMobile;
import com.cnergee.mypage.utils.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketException;
import java.net.SocketTimeoutException;

public class GetRedirectionDetailsSoap {

    private String SOAP_URL;
    private String WSDL_TARGET_NAMESPACE;
    private String METHOD_NAME;
    public static String TAG = "GetRedirectionDetailsSoap";
    public String response;
    private String serverMessage;


    public GetRedirectionDetailsSoap(String WSDL_TARGET_NAMESPACE,String SOAP_URL,String METHOD_NAME){
        this.METHOD_NAME = METHOD_NAME;
        this.SOAP_URL = SOAP_URL;
        this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;

    }


//    public String GetRedirectionDetailsSoap(String TrackId) throws SocketException,SocketTimeoutException,Exception {
//
//        Utils.log("" + this.getClass().getSimpleName(), ":" + SOAP_URL);
//        Utils.log("" + this.getClass().getSimpleName(), "WSDL_TARGET_NAMESPACE:" + WSDL_TARGET_NAMESPACE);
//        Utils.log("" + this.getClass().getSimpleName(), "METHOD_NAME:" + METHOD_NAME);
//
//        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
//
//        PropertyInfo pi = new PropertyInfo();
//        pi = new PropertyInfo();
//        pi.setName(AuthenticationMobile.CliectAccessName);
//        pi.setValue(AuthenticationMobile.CliectAccessId);
//        pi.setType(String.class);
//        request.addProperty(pi);
//
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//                SoapEnvelope.VER11);
//
//        envelope.dotNet = true;
//        envelope.setOutputSoapObject(request);
//
//
//        envelope.encodingStyle = SoapSerializationEnvelope.ENC;
//
//        envelope.implicitTypes = true;
//        envelope.addMapping(WSDL_TARGET_NAMESPACE, "",
//                this.getClass());
//        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
//        androidHttpTransport.debug = true;
//
//        try {
//
//
//            androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,envelope);
//
//            Utils.log(this.getClass().getSimpleName()+":","action:"+WSDL_TARGET_NAMESPACE + METHOD_NAME);
//
//            SoapObject result = (SoapObject) envelope.bodyIn;
//            String res = result.getProperty(0).toString();
//
//
//            Utils.log(this.getClass().getSimpleName()+":","request:"+envelope);
//            Utils.log(this.getClass().getSimpleName()+":","response:"+res);
//            response = envelope.getResponse().toString();
//            Utils.log(this.getClass().getSimpleName()+":","response11:"+response);
//
//            return "ok";
//        }
//        catch (SocketTimeoutException e) {
//            // TODO: handle exception
//            return "error";
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return e.toString();
//        }
//
//
//    }


    public String GetRedirectionDetailsSoap(String TrackId)throws Exception, SocketException, SocketTimeoutException {

        String Result = "OK";

//        try {
        Utils.log("" + this.getClass().getSimpleName(), ":" + SOAP_URL);
        Utils.log("" + this.getClass().getSimpleName(), "WSDL_TARGET_NAMESPACE:" + WSDL_TARGET_NAMESPACE);
        Utils.log("" + this.getClass().getSimpleName(), "METHOD_NAME:" + METHOD_NAME);
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

//            pi.setName("ClientAccessId");
//            Utils.log(TAG+"member:",""+MemberId);
//            pi.setValue(MemberId);//78846
//            pi.setType(String.class);
//            request.addProperty(pi);
            Utils.log("TrackId", ":" + TrackId);

            PropertyInfo pi = new PropertyInfo();
            pi.setName(AuthenticationMobile.CliectAccessName);
            pi.setValue("CM0120ARS");
            pi.setType(String.class);
            request.addProperty(pi);
            Utils.log("GetRedirectionDetailsSoap", "request:" + request.toString());
            Utils.log("GetRedirectionDetailsSoap", "AuthenticationMobile.CliectAccessName:" + AuthenticationMobile.CliectAccessName);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = SoapSerializationEnvelope.ENC;
            envelope.implicitTypes = true;
            envelope.addMapping(WSDL_TARGET_NAMESPACE, "",
                    this.getClass());

            HttpTransportSE transport = new HttpTransportSE(SOAP_URL);
            transport.debug = true;


            transport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME, envelope);
        SoapPrimitive resultString = (SoapPrimitive)envelope.getResponse();
        Utils.log("Renewal Response","resultString:"+resultString.toString());

//            Utils.log("GetRedirectionDetailsSoap","requestDump:"+transport.requestDump);
//            Utils.log("GetRedirectionDetailsSoap", "responseDump:" + transport.responseDump);
//
//            response = (String) envelope.getResponse();
//            Utils.log("GetRedirectionDetailsSoap",""+request.toString());
//            Utils.log("GetRedirectionDetailsSoap",""+response.toString());



        Utils.log(this.getClass().getSimpleName(), " Response: "+transport.responseDump);
        if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
            Object response = envelope.getResponse();
            //Log.i(">>>>Response<<<<<",response.toString());
            Utils.log("Renewal Response",":"+response.toString());
            setServerMessage(response.toString());

        } else if (envelope.bodyIn instanceof SoapFault) { // SoapFault =
            // FAILURE
            SoapFault soapFault = (SoapFault) envelope.bodyIn;
            Utils.log("Renewal Response",":"+soapFault.getMessage().toString());

            return soapFault.getMessage().toString();
        }
//
//        }catch(Exception e){
//            Utils.log("Error",":"+e);
//        }
//
//
//
//        return Result;
//
////            try {
//            //Log.i("#####################", " CALL ");
//            transport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,
//                    envelope);
//            if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
//                Object response = envelope.getResponse();
//                //Log.i(">>>>Response<<<<<",response.toString());
//                setServerMessage(response.toString());
//
//				/*String ss = androidHttpTransport.responseDump;
//				Log.i("#####################", ss);
//				*/
//
//            } else if (envelope.bodyIn instanceof SoapFault) { // SoapFault =
//                // FAILURE
//                SoapFault soapFault = (SoapFault) envelope.bodyIn;
//                return soapFault.getMessage().toString();
//            }
//            //Log.i("#####################", " RESPONSE ");
//            //Log.i("#####################", getServerMessage());
//            return "ok";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return e.toString();


//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//                    SoapEnvelope.VER11);
//
//            envelope.dotNet = true;
//            envelope.setOutputSoapObject(request);
//
//
//            envelope.encodingStyle = SoapSerializationEnvelope.ENC;
//
//            envelope.implicitTypes = true;
//            envelope.addMapping(WSDL_TARGET_NAMESPACE, "",
//                    this.getClass());
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
//            androidHttpTransport.debug = true;
//
//
//        if (envelope.bodyIn instanceof SoapFault) {
//            SoapFault soapFault = (SoapFault) envelope.bodyIn;
//            String requestStr = androidHttpTransport.requestDump;
//            Utils.log(this.getClass().getSimpleName() + ":", "requestStr:" + requestStr);
//
//
//        } else {
//            SoapObject soapObject = ((SoapObject) envelope.bodyIn);
//            String requestStr = androidHttpTransport.requestDump;
//            Utils.log(this.getClass().getSimpleName() + ":", "requestStrnu:" + requestStr);
//
//        }
//            try {
//
//
////                androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME, envelope);
//
//                Utils.log(this.getClass().getSimpleName() + ":", "request:" + androidHttpTransport.requestDump);
//                Utils.log(this.getClass().getSimpleName() + ":", "response:" + androidHttpTransport.responseDump);
//                response = envelope.getResponse().toString();
//
//                return "ok";
//            } catch (Exception e) {
//                e.printStackTrace();
//                return e.toString();
//            }
        return response;

    }

    public String getResponse() {
        // TODO Auto-generated method stub
        return response;
    }
    public String getServerMessage() {
        return serverMessage;
    }

        public void setServerMessage(String serverMessage) {
            this.serverMessage = serverMessage;
        }






}

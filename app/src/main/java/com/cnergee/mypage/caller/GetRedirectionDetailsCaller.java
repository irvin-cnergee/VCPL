package com.cnergee.mypage.caller;

import com.cnergee.mypage.MakeMyPayment_Atom;
import com.cnergee.mypage.MakeMyPayment_PayU;
import com.cnergee.mypage.MakeMyPayments;
import com.cnergee.mypage.MakeMyPayments_CCAvenue;
import com.cnergee.mypage.SOAP.GetRedirectionDetailsSOAP;
import com.cnergee.mypage.utils.Utils;

public class GetRedirectionDetailsCaller extends Thread{
    public GetRedirectionDetailsSOAP getRedirectionDetailsSOAP;

    private String WSDL_TARGET_NAMESPACE;
    private String SOAP_URL;
    private String METHOD_NAME;




    public GetRedirectionDetailsCaller(){}

    public GetRedirectionDetailsCaller(String WSDL_TARGET_NAMESPACE, String SOAP_URL, String METHOD_NAME) {

        this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
        this.SOAP_URL = SOAP_URL;
        this.METHOD_NAME = METHOD_NAME;

    }

    public void run() {

        try {
            getRedirectionDetailsSOAP = new GetRedirectionDetailsSOAP(WSDL_TARGET_NAMESPACE, SOAP_URL, METHOD_NAME);
//            if(Utils.is_subpaisa){
//                MakePaymentSubpaisa.rslt = getRedirectionDetailsSOAP.CallComplaintNoSOAP();
//                MakePaymentSubpaisa.MapRedirectionDetails = getRedirectionDetailsSOAP.getMapRedictionDetail();
//            }else
                if(Utils.is_CCAvenue){
                    MakeMyPayments_CCAvenue.rslt = getRedirectionDetailsSOAP.CallComplaintNoSOAP();
                    MakeMyPayments_CCAvenue.MapRedirectionDetails = getRedirectionDetailsSOAP.getMapRedictionDetail();


            }

        }catch (Exception e) {
            e.printStackTrace();

        }
    }

    public GetRedirectionDetailsSOAP getBeforepaymentsoap() {
        return getRedirectionDetailsSOAP;
    }

    public void setRedirectionDetailsSOAP(GetRedirectionDetailsSOAP getRedirectionDetailsSOAP) {
        this.getRedirectionDetailsSOAP = getRedirectionDetailsSOAP;
    }
}

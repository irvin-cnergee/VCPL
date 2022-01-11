package com.cnergee.mypage.obj;

import java.io.Serializable;

public class RedirectionDetailObj implements Serializable {

    private String TremsAndConditions;
    private String PrivacyPolicy;
    private String CancellationAndRefundPolicy;

    public int getEnablePolicies() {
        return EnablePolicies;
    }

    public void setEnablePolicies(int enablePolicies) {
        EnablePolicies = enablePolicies;
    }

    private int EnablePolicies;

    public String getTremsAndConditions() {
        return TremsAndConditions;
    }

    public void setTremsAndConditions(String tremsAndConditions) {
        TremsAndConditions = tremsAndConditions;
    }

    public String getPrivacyPolicy() {
        return PrivacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        PrivacyPolicy = privacyPolicy;
    }

    public String getCancellationAndRefundPolicy() {
        return CancellationAndRefundPolicy;
    }

    public void setCancellationAndRefundPolicy(String cancellationAndRefundPolicy) {
        CancellationAndRefundPolicy = cancellationAndRefundPolicy;
    }
}

package com.demo.employeedatabase.Model;

import android.graphics.Bitmap;

public class PreviewModel {

    String NAME;
    String PHONE;
    String COMPANY_NAME;
    Bitmap PROFILE_IMG;
    String EMAIL;
    String ADDRESS;
    String WEBSITE;
    String COMPANY_DETAILS;
    String USER_NAME;

    public PreviewModel(String NAME, String PHONE, String COMPANY_NAME,
                        Bitmap PROFILE_IMG, String EMAIL, String ADDRESS,
                        String WEBSITE, String COMPANY_DETAILS , String USER_NAME ) {

        this.NAME = NAME;
        this.PHONE=PHONE;
        this.COMPANY_NAME = COMPANY_NAME;
        this.PROFILE_IMG = PROFILE_IMG;
        this.EMAIL = EMAIL;
        this.ADDRESS = ADDRESS;
        this.WEBSITE = WEBSITE;
        this.COMPANY_DETAILS=COMPANY_DETAILS;
        this.USER_NAME = USER_NAME;
    }

    public String getNAME() {
        return NAME;
    }

    public Bitmap getPROFILE_URL() {
        return PROFILE_IMG;
    }

    public String getCOMPANY_NAME() {
        return COMPANY_NAME;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getCOMPANY_DETAILS() {
        return COMPANY_DETAILS;
    }

    public String getWEBSITE() {
        return WEBSITE;
    }

    public String getPHONE() {
        return PHONE;
    }
}

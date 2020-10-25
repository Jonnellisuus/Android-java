package com.example.helloworld;

public class Company {
    String companyName, businessID, companyForm, registrationDate;
    private boolean expandableCompanyList;

    public Company(String companyName, String businessID, String companyForm, String registrationDate) {
        this.companyName = companyName;
        this.businessID = businessID;
        this.companyForm = companyForm;
        this.registrationDate = registrationDate;
        this.expandableCompanyList = false;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getBusinessID() {
        return businessID;
    }

    public String getCompanyForm() {
        return companyForm;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public boolean isExpandableCompanyList() {
        return expandableCompanyList;
    }

    public void setExpandableCompanyList(boolean expandableCompanyList) {
        this.expandableCompanyList = expandableCompanyList;
    }
}
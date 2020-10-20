package com.example.helloworld;

public class Company {
    String companyName;
    String businessID;
    String companyForm;
    String registrationDate;

    public Company(String companyName, String businessID, String companyForm, String registrationDate) {
        this.companyName = companyName;
        this.businessID = businessID;
        this.companyForm = companyForm;
        this.registrationDate = registrationDate;
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
}
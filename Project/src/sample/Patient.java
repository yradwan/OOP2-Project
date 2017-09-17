package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Patient extends Person implements Serializable{

    private int patientNo, phoneNo;

    public ArrayList<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(ArrayList<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public int getPatientNo() {
        return patientNo;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setPatientNo(int patientNo) {
        this.patientNo = patientNo;
    }

    private ArrayList<Invoice> invoiceList;

    public Patient(String name, String address, int pNo, int id){
        super (name, address);
        phoneNo = pNo;
        patientNo = id;
        invoiceList = new ArrayList<Invoice>();
    }
    public Patient(){
        super();
        phoneNo = 0;
        patientNo = 0;
        invoiceList = new ArrayList<Invoice>();
    }
    public String toString(){
        String s = invoiceList.toString();
        s = s.substring(1, s.length() - 1);
        String m = "\nPatient Name: " + getName() + "\nPatient Address: " + getAddress() + "\nPatient Number: " + getPatientNo() + "\nPatient Phone Number: " + getPatientNo() + "\n" + s ;
        if (0 == invoiceList.size())
            m = m + "\nNo Invoices\n";
        return m;
    }
    public Boolean sixMonthsNoPay(){
        Boolean test1, test3;
        Boolean test2 = true;
        for (int i=0; i<invoiceList.size(); i++){
            test1 = invoiceList.get(i).getIsPaid();
            if (test1 == false)
                test2 = false;
        }
        if (test2 == true)
            return true;
        for (int i=0; i<invoiceList.size(); i++){
            test3 = invoiceList.get(i).sixMonthsWithoutPayment();
            if (test3 != false)
                return true;
        }
        if (invoiceList.size() < 1)
            return true;
        return false;
    }
    public double calculateTotalCashOwed(){
        double totalOwed = 0;
        for (int  i =0; i<invoiceList.size(); i++){
            totalOwed = totalOwed + invoiceList.get(i).calculateAmountDue();
        }
        return totalOwed;
    }
}



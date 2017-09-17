package sample;

import java.time.LocalDate;
import java.util.*;
import java.io.Serializable;

import static java.time.temporal.ChronoUnit.DAYS;

public class Invoice implements Serializable{
    //ArrayList<Dentist> students = new ArrayList<>();
    private int invoiceNo;
    private double invoiceAmt;
    private LocalDate invoiceDate;
    private Boolean isPaid;
    private ArrayList<Procedure> procList;
    private ArrayList<Payment> payList;

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public ArrayList<Payment> getPayList() {

        return payList;
    }

    public void setPayList(ArrayList<Payment> payList) {
        this.payList = payList;
    }

    public ArrayList<Procedure> getProcList() {

        return procList;
    }

    public void setProcList(ArrayList<Procedure> procList) {
        this.procList = procList;
    }

    public Invoice(LocalDate date){
        procList = new ArrayList<>();
        payList = new ArrayList<>();
        invoiceDate = date;
        invoiceAmt = 0;
        isPaid = false;
        invoiceNo = 0;


    }
    public int getInvoiceNo() {
        return invoiceNo;
    }
    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    public double getInvoiceAmt() {
        return invoiceAmt;
    }
    public void setInvoiceAmt(double invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }
    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }
    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    public Boolean getIsPaid() {
        return isPaid;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
    public double calculateAmountDue(){
        double amountOwed;
        double totalPayed = 0;
        double totalCost = 0;
        for (int i = 0; i < procList.size(); i++){
            totalCost = totalCost + procList.get(i).getProcCost();
        }
        for (int i = 0; i < payList.size(); i++){
            totalPayed = totalPayed + payList.get(i).getPaymentAmt();
        }
        if (totalPayed > totalCost){
            amountOwed = 0;
            setIsPaid(true);
        }
        else{
            amountOwed = totalCost - totalPayed;
            if (amountOwed == 0)
                setIsPaid(true);
            else
                setIsPaid(false);
        }
        return amountOwed;
    }
    public String toString(){
        String s1 = "List of Procedures: \n\n";
        if (procList.size() == 0){
            s1 = s1 + " No Procedures";
        }
        else {
            for (int i = 0; i < procList.size(); i++)
                s1 = s1 + "\n" + procList.get(i).toString();
        }
        String s2 ="\nList of Payments: \n\n";
        if (payList.size() == 0){
            s2 = s2 + "\nNo Payments\n\n";
        }
        else{
            for (int i = 0; i < payList.size(); i++ ){
                s2 = s2 + "\n" + payList.get(i).toString();
            }}
        String s3 = "\n\nInvoice " + getInvoiceNo() + " Details\n\n" + s1 + "\n" + s2;
        return s3;
    }
    public Boolean sixMonthsWithoutPayment(){
        LocalDate today = LocalDate.now();
        long daysBetween;
        for (int i = 0; i < payList.size(); i++){
            daysBetween = DAYS.between(today, payList.get(i).getPaymentDate());
            if (daysBetween < 0)
                daysBetween = daysBetween * -1;
            if (daysBetween > 180 )
                return false;
        }
        return true;
    }
}

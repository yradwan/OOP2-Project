package sample;

import java.time.LocalDate;
import java.util.Date;
import java.io.Serializable;

public class Payment implements Serializable{
    private int paymentNo;
    private double paymentAmt;
    private LocalDate paymentDate;

    public Payment(int payNo, double payAmt, LocalDate date){
        paymentDate = date;
        paymentNo = payNo;
        paymentAmt = payAmt;
    }
    public String toString() {
        String s = "Payment Number: " + paymentNo + "\nPayment Amount: " + paymentAmt + "\nPayment Date: " + paymentDate;
        return s;
    }
    public int getPaymentNo() {
        return paymentNo;
    }
    public void setPaymentNo(int paymentNo) {
        this.paymentNo = paymentNo;
    }
    public double getPaymentAmt() {
        return paymentAmt;
    }
    public void setPaymentAmt(double paymentAmt) {
        this.paymentAmt = paymentAmt;
    }
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}

package sample;

import java.io.Serializable;

public class Procedure implements Serializable{
    private int procNo;
    private String procName;
    private double procCost;

    public Procedure(String pName, double pCost, int pNo){
        procName = pName;
        procCost = pCost;
        procNo = pNo;
    }

    public int getProcNo() {
        return procNo;
    }

    public void setProcNo(int procNo) {
        this.procNo = procNo;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public double getProcCost() {
        return procCost;
    }

    public void setProcCost(double procCost) {
        this.procCost = procCost;
    }

    @Override
    public String toString() {
        String s = "Procedure Name: " + procName + "\nProdcedure Cost: " + procCost + "\nProcedure Number: " + procNo;
        return s;
    }

}

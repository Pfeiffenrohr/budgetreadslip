package de.lechner.readslip.p2p.bondora;

public class GoGrowAccounts
{
    private String Name;

    private int NetDeposits;

    private double NetProfit;

    private double TotalSaved;

    public void setName(String Name){
        this.Name = Name;
    }
    public String getName(){
        return this.Name;
    }
    public void setNetDeposits(int NetDeposits){
        this.NetDeposits = NetDeposits;
    }
    public int getNetDeposits(){
        return this.NetDeposits;
    }
    public void setNetProfit(double NetProfit){
        this.NetProfit = NetProfit;
    }
    public double getNetProfit(){
        return this.NetProfit;
    }
    public void setTotalSaved(double TotalSaved){
        this.TotalSaved = TotalSaved;
    }
    public double getTotalSaved(){
        return this.TotalSaved;
    }
}
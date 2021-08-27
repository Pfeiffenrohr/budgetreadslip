package de.lechner.readslip.p2p.bondora;

import java.util.List;

public class Payload
{
    private double Balance;

    private int Reserved;

    private int BidRequestAmount;

    private double TotalAvailable;

    private List<GoGrowAccounts> GoGrowAccounts;

    public void setBalance(double Balance){
        this.Balance = Balance;
    }
    public double getBalance(){
        return this.Balance;
    }
    public void setReserved(int Reserved){
        this.Reserved = Reserved;
    }
    public int getReserved(){
        return this.Reserved;
    }
    public void setBidRequestAmount(int BidRequestAmount){
        this.BidRequestAmount = BidRequestAmount;
    }
    public int getBidRequestAmount(){
        return this.BidRequestAmount;
    }
    public void setTotalAvailable(double TotalAvailable){
        this.TotalAvailable = TotalAvailable;
    }
    public double getTotalAvailable(){
        return this.TotalAvailable;
    }
    public void setGoGrowAccounts(List<GoGrowAccounts> GoGrowAccounts){
        this.GoGrowAccounts = GoGrowAccounts;
    }
    public List<GoGrowAccounts> getGoGrowAccounts(){
        return this.GoGrowAccounts;
    }
}
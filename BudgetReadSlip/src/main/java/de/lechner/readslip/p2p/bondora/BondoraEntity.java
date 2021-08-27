package de.lechner.readslip.p2p.bondora;

import javax.persistence.Entity;


public class BondoraEntity
{
    private Payload Payload;

    private boolean Success;

    private String Errors;

    public void setPayload(Payload Payload){
        this.Payload = Payload;
    }
    public Payload getPayload(){
        return this.Payload;
    }
    public void setSuccess(boolean Success){
        this.Success = Success;
    }
    public boolean getSuccess(){
        return this.Success;
    }
    public void setErrors(String Errors){
        this.Errors = Errors;
    }
    public String getErrors(){
        return this.Errors;
    }
}

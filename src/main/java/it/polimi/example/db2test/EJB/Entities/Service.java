package it.polimi.example.db2test.EJB.Entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="services", schema="telco_db")
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int service_id;
    private Type type;
    private float giga;
    private int sms;
    private int minutes;
    private float extraFeeGiga;
    private float extraFeeSMS;
    private float extraFeeMinutes;
    @ManyToMany
    @JoinTable(name="package_service", schema = "telco_db", joinColumns = @JoinColumn (name = "Service_id"), inverseJoinColumns = @JoinColumn (name = "Package_ID"))
    private Collection<Package> packages;
    public Service() {
    }
    public Service(Type type, float giga, int sms, int minutes, float extraFeeGiga, float extraFeeSMS, float extraFeeMinutes){
        this.type=type;
        this.giga=giga;
        this.sms=sms;
        this.minutes=minutes;
        this.extraFeeGiga=extraFeeGiga;
        this.extraFeeMinutes=extraFeeMinutes;
        this.extraFeeSMS=extraFeeSMS;
    }

    public float getGiga() {
        return giga;
    }

    public void setGiga(float giga) {
        this.giga = giga;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public float getExtraFeeGiga() {
        return extraFeeGiga;
    }

    public void setExtraFeeGiga(float extraFeeGiga) {
        this.extraFeeGiga = extraFeeGiga;
    }

    public float getExtraFeeSMS() {
        return extraFeeSMS;
    }

    public void setExtraFeeSMS(float extraFeeSMS) {
        this.extraFeeSMS = extraFeeSMS;
    }

    public float getExtraFeeMinutes() {
        return extraFeeMinutes;
    }

    public void setExtraFeeMinutes(float extraFeeMinutes) {
        this.extraFeeMinutes = extraFeeMinutes;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }
}

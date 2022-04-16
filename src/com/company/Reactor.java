package com.company; //подключили сначала GSON - гугл 2.8.0

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TYPE")
public class Reactor {

    private String classs;
    private Double burnup;
    private Double kpd;
    private Double enrichment;
    private Double termal_capacity;
    private Double electrical_capacity;
    private Double life_time;
    private Double first_load;
    private String sourse;//это сторка чтобы показать из какого файла(расширения) мы это все зачитали

    public Reactor(String c, Double b, Double k, Double e, Double tc, Double ec, Double lt, Double fl, String s) {
        this.classs = c;
        this.burnup = b;
        this.kpd = k;
        this.enrichment = e;
        this.termal_capacity = tc;
        this.electrical_capacity = ec;
        this.life_time = lt;
        this.first_load = fl;
        this.sourse = s;//откуда он зачитан
    }


    public Reactor() {
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public Double getBurnup() {
        return burnup;
    }

    public void setBurnup(Double burnup) {
        this.burnup = burnup;
    }

    public Double getKpd() {
        return kpd;
    }

    public void setKpd(Double kpd) {
        this.kpd = kpd;
    }

    public Double getEnrichment() {
        return enrichment;
    }

    public void setEnrichment(Double enrichment) {
        this.enrichment = enrichment;
    }

    public Double getTermal_capacity() {
        return termal_capacity;
    }

    public void setTermal_capacity(Double termal_capacity) {
        this.termal_capacity = termal_capacity;
    }

    public Double getElectrical_capacity() {
        return electrical_capacity;
    }

    public void setElectrical_capacity(Double electrical_capacity) {
        this.electrical_capacity = electrical_capacity;
    }

    public Double getLife_time() {
        return life_time;
    }

    public void setLife_time(Double life_time) {
        this.life_time = life_time;
    }

    public Double getFirst_load() {
        return first_load;
    }

    public void setFirst_load(Double first_load) {
        this.first_load = first_load;
    }

    public String getSourse() {
        return sourse;
    }

    public void setSourse(String sourse) {
        this.sourse = sourse;
    }




}
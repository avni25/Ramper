package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Flight extends Aerodrome{

    private String name;
    private String bodyType;
    private String registration;
    private String STA;
    private String STD;
    private String depCallSign;
    private String from;
    private String to;
    private String hnd;
    private int terminal=0;
    private Plot plot;
    private String companyName;

    public Flight(){ }
    public Flight(String name, String bodyType, String registration, String STA, String STD,
                  String depCallSign, String from, String to, String hnd, int terminal, Plot plot, String companyName) {
        this.name = name;
        this.bodyType = bodyType;
        this.registration = registration;
        this.STA=STA;
        this.STD = STD;
        this.depCallSign = depCallSign;
        this.from = from;
        this.to = to;
        this.hnd = hnd;
        this.terminal = terminal;
        this.plot = plot;
        this.companyName = companyName;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getSTA() {
        return STA;
    }

    public void setSTA(String STA) {
        this.STA = STA;
    }

    public String getSTD() {
        return STD;
    }

    public void setSTD(String STD) {
        this.STD = STD;
    }

    public String getDepCallSign() {
        return depCallSign;
    }

    public void setDepCallSign(String depCallSign) {
        this.depCallSign = depCallSign;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getHnd() {
        return hnd;
    }

    public void setHnd(String hnd) {
        this.hnd = hnd;
    }

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }
    public void setPlot(String plot){
        Aerodrome ae = new Aerodrome();
        try{
            this.plot = ae.toPlot(plot);
        }catch(Exception e){
            System.out.println("setPlot function doesnt work");
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isDosmesticFlight(){
        domestics = Arrays.asList(domesticAerodromeCodes);
        for(int i=0;i<domestics.size();i++){
            if(domestics.get(i).contains(this.getTo())) return true;
        }
        return false;
    }
    public boolean isCatExist(){
        if(catA.contains(this.getBodyType())) return true;
        if(catB.contains(this.getBodyType())) return true;
        if(catC.contains(this.getBodyType())) return true;
        if(catD.contains(this.getBodyType())) return true;
        if(catE.contains(this.getBodyType())) return true;
        if(catF.contains(this.getBodyType())) return true;
        return false;
    }


    @Override
    public String toString() {
        return "Flight{" +
                "callSign='" + name + '\'' +
                ", bodyType=" + bodyType  +
                ", registration=" + registration  +
                ", STA=" + STA +
                ", STD=" + STD +
                ", depCallSign='" + depCallSign + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", hnd='" + hnd + '\'' +
                ", terminal=" + terminal +
                ", plot=" + plot.getName() +
                ", companyName='" + companyName + '\'' +
                '}';
    }


}

package common;

public class Plot {

    private String name;
    private String bodyType;
    private int terminal;
    private Flight flight;

    public Plot(){
        this.name = "";
        this.bodyType = "";
        this.terminal = 0;
        this.flight = null;
    }
   // String flightName, String flightBodyType, String flightREG,String flightSTA,String flightSTD,String flightFROM,String flightTO,String flightHND
    public Plot(String name, String bodyType, int terminal,Flight flight) {
        this.name = name;
        this.bodyType = bodyType;
        this.terminal = terminal;
        this.flight = flight;
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

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {

        this.flight = flight;
    }

    @Override
    public String toString() {
        return name;
    }

    public String printPlot() {
        return "Plot{" +
                "name='" + name + '\'' +
                ", bodyType='" + bodyType + '\'' +
                ", terminal=" + terminal +
                ", flight=" + flight.getName() +
                '}';
    }


}

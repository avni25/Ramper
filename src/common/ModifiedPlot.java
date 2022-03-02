package common;

import java.util.Objects;

public class ModifiedPlot extends Plot{

    String plotGroup;
    Plot plot;

    public ModifiedPlot(){   }
    public ModifiedPlot(String plotGroup, Plot plot) {
        this.plotGroup = plotGroup;
        this.plot = plot;
    }

    public ModifiedPlot(String name, String bodyType, int terminal, Flight flight, String plotGroup, Plot plot) {
        super(name, bodyType, terminal, flight);
        this.plotGroup = plotGroup;
        this.plot = plot;
    }

    public String getPlotGroup() {
        return plotGroup;
    }

    public void setPlotGroup(String plotGroup) {
        this.plotGroup = plotGroup;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    @Override
    public String toString() {
        return "ModifiedPlot{" +
                "plotGroup='" + plotGroup + '\'' +
                ", plot=" + plot.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModifiedPlot that = (ModifiedPlot) o;
        return Objects.equals(plotGroup, that.plotGroup) &&
                Objects.equals(plot.getName(), that.plot.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(plotGroup, plot.getName());
    }
}

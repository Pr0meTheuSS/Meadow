package Spot;

public interface SpotAgent {
    public String getInfo();
    public void tick();
    public void setSpot(Spot spot);
    public Spot getSpot();
}

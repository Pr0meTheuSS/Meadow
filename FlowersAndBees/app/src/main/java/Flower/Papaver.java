package Flower;


public class Papaver extends Flower {

    private static int instances = 0;

/* Papaver: 20 ticks 
    1 тик - зерно; 
    2-4 - проросток; 
    5-7 взрослая; 
    8-15 - цветение ; 
    16 - 19 - плодоношение;
    20- смерть
*/
public Papaver() {
        instances++;

        state = PlantState.SEEDLING;
        age = 0;
        seedlingTime = 3;
        adultTime = 5;
        bloomingTime = 8;
        fruitingTime = 17;
        lifetime = 20;

        honeydewCount = 20;
    }

    @Override
    public void die() {
        getSpot().removeSpotAgent(this);
    }

    @Override
    public String getInfo() {
        return super.getInfo() + getClass() + " was grown " + instances + "\n";
    }
}

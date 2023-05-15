package Flower;

import java.util.Random;

public class Clower extends Flower {

    private static int instances = 0;

    /*Clover:
        20 ticks 
        1 тик - зерно; 
        2-3 - проросток; 
        4-6 взрослая; 
        7-15 - цветение ; 
        16 - 19 - плодоношение;
        20 - смерть
    
     */
    public Clower() {
        instances++;

        state = PlantState.SEEDLING;
        age = 0;
        seedlingTime = 3;
        adultTime = 4;
        bloomingTime = 7;
        fruitingTime = 16;
        lifetime = 20;

        honeydewCount = 20;
    }

    @Override
    public void die() {
        Random rand = new Random();
        // С вероятностью 1/2 выкидываем перед смертью семя.
        if (rand.nextInt() % 2 == 0) {
            fruit();
        }

        getSpot().removeSpotAgent(this);
    }

    @Override
    public String getInfo() {
        return super.getInfo() + getClass() + " was grown " + instances + "\n";
    }
}

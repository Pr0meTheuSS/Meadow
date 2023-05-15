package Flower;

public class Bellflower extends Flower {

    private static int instances = 0;

    public Bellflower() {
        instances++;

        state = PlantState.SEEDLING;
        age = 0;
        seedlingTime = 2;
        adultTime = 5;
        bloomingTime = 8;
        fruitingTime = 14;
        lifetime = 16;
        honeydewCount = 20;
    }

    @Override
    public void die() {
        fruit();
        getSpot().removeSpotAgent(this);
    }

    @Override
    public String getInfo() {
        return super.getInfo() + getClass() + " was grown " + instances + "\n";
    }
}

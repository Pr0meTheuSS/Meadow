package Flower;

public class Bellflower extends Flower {

    public Bellflower() {
        state = PlantState.BLOOMING;
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
}

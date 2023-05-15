package Bee;

import java.util.Random;

import Hive.Hive;

public class SimpleBee extends Bee {
    public SimpleBee() {
        // Максимальная грузоподъемность.
        honeydewLimit = 20;
        // Текущее количество нектара на пчеле.
        honeydewCurrent = 0;
        // Дальность одной вылазки.
        pathLimit = 5;
        // Количество вылазок за жизнь.
        tripCount = 0;
        // Максимально допустимое количество вылазок за жизнь, потом смерть.
        tripLimit = 2;
        // Начальное состояние при рождении в улье - поиск цветущих.
        state = BeeState.SearchFlower;    
    }

    @Override
    protected void flyHome() {
        currentPath++;
        int homeX = getHive().getSpot().getX();
        int homeY = getHive().getSpot().getY();

        int beeX = getSpot().getX();
        int beeY = getSpot().getY();

        if (homeX == beeX && homeY == beeY) {
            comeHome();
            return;
        }

        int xDirection = homeX - beeX;
        int yDirection = homeY - beeY;
        int xOffset = (xDirection == 0) ? 0 : xDirection / Math.abs(xDirection);
        int yOffset = (yDirection == 0) ? 0 : yDirection / Math.abs(yDirection);

        var oldSpot = getSpot();
        oldSpot.removeSpotAgent(this); 
        oldSpot.getNeighbour(xOffset, yOffset).addSpotAgent(this);
    }

    @Override
    protected void searchFlowers() {
        currentPath++;
        if (currentPath >= pathLimit) {
            state = BeeState.GoHome;
            flyHome();
            return;
        }

        var xOffset = 0;
        var yOffset = 0;

        Random rand = new Random();
        do {
            xOffset = rand.nextInt(-1, 1);
            yOffset = rand.nextInt(-1, 1);
        } while (xOffset == 0 && yOffset == 0);

        var oldSpot = getSpot();
        oldSpot.removeSpotAgent(this);
    
        oldSpot.getNeighbour(xOffset, yOffset).addSpotAgent(this);
    }

    public void setHive(Hive home) {
        this.home = home;
    }
} 

package Spot;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Flower.Flower;
import Hive.Hive;
import Meadow.Meadow;

public class Spot {
    public Spot(Meadow meadow, int x, int y) {
        this.rootMeadow = meadow;
        this.x = x;
        this.y = y;
    }

    public void addSpotAgent(SpotAgent newAgent) {
        newAgent.setSpot(this);
        toAdd.add(newAgent);
    }

    public void tick() {
        tickInhabitant();
    }

    public void removeSpotAgent(SpotAgent agent) {
        toRemove.add(agent);
    }

    public Spot getRandomNaighbour() {
        Random rand = new Random();
        int neighbourX = x + rand.nextInt(-1, 1);
        int neighbourY = y + rand.nextInt(-1, 1);

        return rootMeadow.getSpot(neighbourX, neighbourY);
    }

    public Spot getNeighbour(int xOffset, int yOffset) {
        return rootMeadow.getSpot(x + xOffset, y + yOffset);
    }

    private void tickInhabitant() {
        var iterator = agents.iterator();
        while(iterator.hasNext()) {
            var agent = iterator.next();
            agent.tick();
        }

        agents.addAll(toAdd);
        agents.removeAll(toRemove);
        toAdd.clear();
        toRemove.clear();

        SpotAgentInteractionController.runInteractions(agents);
    }
    
    public boolean plantFlower(Flower flower) {
        // Пробегаемся по всем агентам на этом участке.
        for (var agent: agents) {
            // Если находим цветок или улей, то сообщаем, что взращивание не увенчалось успехом.
            if (agent instanceof Flower || agent instanceof Hive) {
                return false;
            }
        }

        try {
            // Через reflection порождаем сущность того же класса, что и flower. 
            var ctor = flower.getClass().getDeclaredConstructor();
            Flower newFlower = (Flower) ctor.newInstance();
            // "Сажаем" новое растение.
            addSpotAgent(newFlower);
            return true;
        } catch(NoSuchMethodException | SecurityException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // Если в процессе создания нового объекта что-то отвалилось, то выведем в колнсоль много 
            // сообщений об ошибках, но продолжим работу.
            e.printStackTrace();
            return false;
        }
    }

    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        for (var agent: agents) {
            sb.append(agent.getInfo());
        }

        return "In spot " + getX() + ":" + getY() + "  " + sb.toString() + '\n';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<SpotAgent> getAgents() {
        return agents;
    }
    
    private int x = 0;
    private int y = 0;
    Meadow rootMeadow;
    private List<SpotAgent> toRemove = new ArrayList<>();
    private List<SpotAgent> toAdd = new ArrayList<>();
    private List<SpotAgent> agents = new ArrayList<>();
}

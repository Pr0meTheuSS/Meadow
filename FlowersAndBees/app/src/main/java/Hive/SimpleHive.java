package Hive;

import Bee.SimpleBee;

public class SimpleHive extends Hive {

    public SimpleHive() {
        currentHoneydewCount = 10;
        burnNewGenerationFrequency = 10;
    }

    public void burnNewGeneration() {
        // Для каждой новой особи пчелы тратим 5 единиц нектара.
        for (int i = 0; i < currentHoneydewCount / 5; i++) {
            var newBee = new SimpleBee();
            newBee.setHome(this);
            getSpot().addSpotAgent(newBee);
            burnedBees++;
            currentBees++;
            currentHoneydewCount -= 5;
            honeydewUsed += 5;
            generationAmount++;
        }
    }

    @Override
    public String getInfo() {
        var economicsString =  "Hive economics:\n \tcurrent honeydew: " + currentHoneydewCount + "\n" + 
        "\tcollected honedew in life: " + honeydewCollected + "\n" + 
        "\twasted honedew in life: " + honeydewUsed + "\n";

        var demographyString = "Hive demography:\n \tcurrent bees: " + currentBees + "\n" + 
        "\tburned bees: " + burnedBees + "\n" + 
        "\tdead bees: " + deadBees + "\n" + 
        "\tgenerations: " + generationAmount + "\n";

        return demographyString + economicsString;
    }
}

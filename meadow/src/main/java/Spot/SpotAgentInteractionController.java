package Spot;

import Bee.Bee;
import Flower.Flower;
import Hive.Hive;

// Класс отвечает за взаимодействия между
class SpotAgentInteractionController {
    public static void runInteractions(Iterable<?> agents) {
        for (var firstAgent : agents) {
            for (var secondAgent : agents) {
                // Обрабатываем взаимодействия пчелы и растения.
                if (firstAgent instanceof Bee && secondAgent instanceof Flower) {
                    Bee bee = (Bee) firstAgent;
                    Flower flower = (Flower) secondAgent;
                    // Обмениваемся нектаром и пыльцой.
                    swapHoneydew(bee, flower);
                    swapPollen(bee, flower);
                }
                // Обрабатываем взаимодействия пчелы и улья.
                if (firstAgent instanceof Bee && secondAgent instanceof Hive) {
                    Bee bee = (Bee) firstAgent;
                    Hive hive = (Hive) secondAgent;
                    // Если пчела попала в свой дом.
                    if (bee.getHive() == hive) {
                        // Улей принимает родненькую.
                        hive.takeBee(bee);
                    }
                }
            }            
        }    
    }

    private static void swapHoneydew(Bee bee, Flower flower) {
        bee.forageHoneydew(flower.giveAwayHoneydew());
    }

    private static void swapPollen(Bee bee, Flower flower) {
        bee.catchPollen(flower);
        //flower.pollinate(bee);
    }
}

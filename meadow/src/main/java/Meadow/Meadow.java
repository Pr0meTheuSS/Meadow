package Meadow;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Spot.Spot;
import Chronicler.Chronicler;
import Flower.FlowerBuilder;
import Hive.*;

public class Meadow {
    public Meadow(String configFile, String outputFile) {
        // Create properties instance.
        Properties properties = new Properties();
        // Try to load configuration from file in properties instance.
        try (InputStream input = new FileInputStream(configFile)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse field size configurations.
        String fieldWidthString = properties.getProperty("FieldWidth"); // = "5"
        String fieldHeightString = properties.getProperty("FieldHeight");
        width = Integer.parseInt(fieldWidthString);
        height = Integer.parseInt(fieldHeightString);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                spots.add(new Spot(this, x, y));
            }
        }

        // sout for debug mode.
        System.out.println("Field size: " + fieldWidthString + " " + fieldHeightString);

        // Parse the Universe time configurations.
        String lifeTimeString = properties.getProperty("LifeTime");
        String epochTimeString = properties.getProperty("EpochTime");
        lifeTimeOfTheUniverse = Integer.parseInt(lifeTimeString);
        logFrequency = Integer.parseInt(epochTimeString);

        // sout for debug mode.
        System.out.println("The Universe's time configurations: " + lifeTimeOfTheUniverse + " " + logFrequency);

        // Interation by all keys, get all startWith 'Flower' or 'Hive' and instance it on the Field.
        for (var key: properties.keySet()) {
            if (key.toString().startsWith("Flower")) {
                String dataToParseFlowerParams = properties.get(key).toString();
                String[] flowerParams = dataToParseFlowerParams.split("\\|");

                if (flowerParams.length == 3) {
                    String flowerName = flowerParams[0];
                    int flowerPositionX = Integer.parseInt(flowerParams[1]);
                    int flowerPositionY = Integer.parseInt(flowerParams[2]);

                    var spot = getSpot(flowerPositionX, flowerPositionY);
                    // Если на этом месте уже что-то расположено
                    if (spot.getAgents().size() != 0) {
                        // Выводим сообщение об ошибке
                        System.err.println("Collision of implacing in Meadow constructor");
                        // Переходим к обработке следующего ключа.
                        continue;
                    }
                    // Устанавливаем положение нового цветка.
                    spot.addSpotAgent(FlowerBuilder.create(flowerName));
                } else {
                    throw new RuntimeException("Wrong configuration file format in string with Flower");
                }
            } else if (key.toString().startsWith("Hive")) {
                String dataToParseHiveParams = properties.get(key).toString();
                var hiveParams = dataToParseHiveParams.split("\\|");
                if (hiveParams.length == 3) {
                    String hiveName = hiveParams[0];
                    int hivePositionX = Integer.parseInt(hiveParams[1]);
                    int hivePositionY = Integer.parseInt(hiveParams[2]);
                    var hive = HiveBuilder.create(hiveName);
                    var spot = getSpot(hivePositionX, hivePositionY);
                    // Если на этом месте уже что-то расположено
                    if (spot.getAgents().size() != 0) {
                        // Выводим сообщение об ошибке
                        System.err.println("Collision of implacing in Meadow constructor");
                        // Переходим к обработке следующего ключа.
                        continue;
                    }
                    spot.addSpotAgent(hive);
                    hive.burnNewGeneration();
                } else {
                    throw new RuntimeException("Wrong configuration file format in string with Hive");
                }
            }
            
            chronicler = new Chronicler(outputFile);
        }
    }

    public void runSimulation() {
        for (int i = 0; i < lifeTimeOfTheUniverse; i++) {
            runEpoch();
            if (i % logFrequency == 0) {
                chronicler.writeChronics(spots, i);
            }
        }
        chronicler.demote();
    }

    private void runEpoch() {
        for (var spot : spots) {
            spot.tick();
        }
    }

    public Spot getSpot(int x, int y) {
        if (x < 0) {
            x = width + x;
        }
        if (y < 0) {
            y = height + y;
        }
        // В силу тороидальности луга определяем положение участка через остаток от деления.
        x = x % width;
        y = y % height;
        // В линейной структуре List запрашиваем участок, которому соответствуют координаты (x , y).
        return spots.get(y * width + x);
    }

    private int width = 0;
    private int height = 0;
    private List<Spot> spots = new ArrayList<>();
    private int lifeTimeOfTheUniverse = 0;
    private int logFrequency = 0;
    private Chronicler chronicler = null;
}

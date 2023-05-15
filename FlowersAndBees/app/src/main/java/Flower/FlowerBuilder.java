package Flower;

public class FlowerBuilder {
    public static Flower create(String flowerTypeName) {
        if (flowerTypeName.equals("Bellflower")) {
            return new Bellflower();
        }

        if (flowerTypeName.equals("Clower")) {
            return new Clower();
        }
        
        if (flowerTypeName.equals("Papaver")) {
            return new Papaver();
        }

        // Тут надо дописать точно такой же if для других типов цветов.
        // flowerTypeName.equals("yourFlowerType") {return new  yourFlowerType();}

        throw new RuntimeException("This flower type not found: " + flowerTypeName);
    }
}

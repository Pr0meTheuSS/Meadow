package Flower;

public class FlowerBuilder {
    public static Flower create(String flowerTypeName) {
        if (flowerTypeName.equals("Bellflower")) {
            return new Bellflower();
        }
        // Тут надо дописать точно такой же if для других типов цветов.
        // if (...) {return new  ... ;}

        return null;
    }
}

package Hive;

public class HiveBuilder {
    public static Hive create(String hiveNameString) {
        // Оставил интерфейс создания улья через имя типа улья, но фактически тип улья один.
        // При необходимости возможно при расширении типов ульев порождать и другие сущности.
        return new SimpleHive();
    }
}

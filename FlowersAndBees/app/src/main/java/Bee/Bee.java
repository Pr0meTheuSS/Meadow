package Bee;

import java.util.HashMap;
import java.util.Map;

import Flower.Flower;
import Hive.Hive;
import Spot.Spot;
import Spot.SpotAgent;

/**
 * Класс обобщённой пчелы
 * @author klimenko
 *
 */
public abstract class Bee implements SpotAgent {
	
	/**
	 * Ссылка на домашний улей.
	 */
	protected Hive home;
	/**
	 * Ссылка на текущую позицию.
	 */
	protected Spot currentSpot;
	
	/**
	 * Собрать нектар с цветка. 
	 * @param honeyCount - количество единиц нектара.
	 */
	public void forageHoneydew(int honeyCount) {
		honeydewCurrent = Math.min(honeydewLimit, honeyCount + honeydewCurrent);
		if (honeydewCurrent == honeydewLimit) {
			state = BeeState.GoHome;
		}
	}

	public void setHome(Hive home) {
		this.home = home;
	}

	/**
	 * Обмазаться в пыльце.
	 * @param f - цветок
	 */
	public void catchPollen(Flower f) {
		// Проходимся по всему контейнеру с пыльцой на пчеле.
		for (var flower : pollens.keySet()) {
			//  Если находим пыльцу того же типа.
			if (flower.getClass().equals(f.getClass())) {
				// Добавляем ещё этой же пыльцы.
				pollens.put(flower , pollens.get(flower) + f.giveAwayPollen());
				// Закнчиваем процесс переноса пыльцы с растения на пчелу. 
				return;
			}
		}

		// Если данный вид пыльцы пчелой раньше не встречался, то добавим её в любом случае. 
		pollens.put(f, f.giveAwayPollen());
	}
	
	
	public int getPollen(Flower f) {
		// Проходимся по всему контейнеру с пыльцой на пчеле.
		for (var flower : pollens.keySet()) {
			//  Если собирается пыльца такого же типа.
			if (flower.getClass().equals(f.getClass())) {
				// Возвращаем её количество.
				return pollens.get(flower);
			}
		}

		return 0;
	}

	//Внутренняя логика пчелы.	
	/**
	 * Погибнуть.
	 */
	protected  void die() {
		home.mournTheLoss();
		getSpot().removeSpotAgent(this);
	}
	
	/**
	 * Лететь в направлении своего улья.
	 */
	protected abstract void flyHome();
	
	/**
	 * Искать цветок с нектаром
	 */
	protected abstract void searchFlowers();
	
	/**
	 * Выгрузить собранный нектар.
	 */
	public int giveAwayCargo() {
		return honeydewCurrent;
	}

	/**
	 * Получить ссылку на домашний улей.
	 */
	public Hive getHive() {
		return home;
	}

	public void comeHome() {
		if (state != BeeState.GoHome) {
			return;
		}

		// Увеличиваем счетчик ходок.
		tripCount++;
		// Удалаем всю пыльцу, собранную за последнюю ходку.
		pollens.clear();
		
		// Если пробег пчелки больше номинально допустимого - умираем.
		if (tripCount >= tripLimit) {
			die();
			return;
		}
		// Обнуляем статы пчелы для новой ходки.
		state = BeeState.SearchFlower;
		honeydewCurrent = 0;
		currentPath = 0;
	}

	@Override
    public String getInfo() {
		return "Bee " + this + " is in position (" + getSpot().getX() + ", " + getSpot().getY() + ")" +
		"in state " + state.toString() + "with honeydew " + honeydewCurrent + "\n";
	}

	/**
	 * Выполнить все необходимые в ходе текущей итерации действия
	 */
	@Override
    public void tick() {
		if (state == BeeState.GoHome) {
			flyHome();
		} else if (state == BeeState.SearchFlower) {
			searchFlowers();
		}
	}

	/**
	 * Устанавливаем положение цветка.
	 * @param spot - участок для расположения цветка.
	 */
	@Override
	public void setSpot(Spot spot) {
		currentSpot = spot;
	}

	/**
	 * Возвращаем положение цветка.
	 * @return расположение цветка.
	 */
	@Override
	public Spot getSpot() {
		return currentSpot;
	}

	protected int honeydewCurrent = 0;
	protected int honeydewLimit = 20;

	protected int tripCount = 0;
	protected int tripLimit = 3;

	protected int pathLimit = 6;
	protected int currentPath = 0;

	protected BeeState state = BeeState.SearchFlower;
	protected Map<Flower, Integer> pollens = new HashMap<>();
}

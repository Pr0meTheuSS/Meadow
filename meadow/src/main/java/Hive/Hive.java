package Hive;

import Bee.Bee;

/**
 * Абстракный класс улья
 * @author klimenko
 *
 */

 import Spot.*;

public abstract class Hive implements SpotAgent {	
	/**
	 * Выполнить все необходимые в ходе текущей итерации действия.
	 */
	@Override
	public void tick() {
		lifetime++;
		if (lifetime % burnNewGenerationFrequency == 0) {
			burnNewGeneration();
		}
	}


	/**
	 *  Получение нектара.
	 * 
	 */
	public void getHoneydew(int newHoneydew) {
		currentHoneydewCount += newHoneydew;
		honeydewCollected += newHoneydew;
	}

	abstract public void burnNewGeneration();

	/**
	 * Установить ссыклу на область, в которой находится агент.
	 */
	@Override
	public void setSpot(Spot spot) {
		rootSpot = spot;
	}

	/**
	 * Вернуть ссыклу на область, в которой находится агент.
	 */
	@Override
	public Spot getSpot() {
		return rootSpot;
	}

	/**
	 * Вернуть строку с описанием текущего состояния агента.
	 */
	@Override
	abstract public String getInfo();

	public void takeBee(Bee bee) {
		// Выгружаем из родной пчелы нектар.
		getHoneydew(bee.giveAwayCargo());
		bee.comeHome();
	}

	/*
	 * 
	 */
	public void mournTheLoss() {
		deadBees++;
		currentBees--;
	}

	protected Spot rootSpot;

	protected int lifetime;
	protected int honeydewCollected;
	protected int honeydewUsed;
	protected int currentHoneydewCount;
	protected int burnNewGenerationFrequency;
	protected int currentBees;
	protected int burnedBees;
	protected int deadBees;
	protected int generationAmount;
}

package Flower;

import Bee.Bee;
import Spot.Spot;
import Spot.SpotAgent;

/**
 * Класс обобщённого цветкового растения
 * @author klimenko
 */

 public abstract class Flower implements SpotAgent {
	/*
	 * Статическое поле для подсчета числа цветков.
	 */
	private static int instances = 0;

	public Flower() {
		instances++;
	}

	/*
	 * Функция вывода информации о цветке.
	 */
	@Override
    public String getInfo() {
		return "Flower " + getClass() + " in position (" + rootSpot.getX() + ", " + rootSpot.getY() + ")" 
		+ " in state " + state.toString();
	}

	/**
	 * Выполнить все необходимые в ходе текущей итерации действия.
	 */
	@Override
	public void tick() {
        age++;
		// переход от ростка к взрослому растению.
        if (state == PlantState.SEEDLING) {
            if (age >= adultTime) {
                growAdult();
            }
        }

		// переход от взрослого растения к цветению.
        if (state == PlantState.ADULT) {
            if (age >= bloomingTime) {
                bloom();
            }
        }

		// переход от цветения к плодоношению.
		// Допустимо переопределить метод fruit() и изменить поведение при плодоношении
        if (state == PlantState.BLOOMING) {
            if (age >= fruitingTime) {
				fruit();
            }
        }

		// переход к смерти.
		if (age >= lifetime) {
			die();
		}
	}

	/**
	 * Устанавливаем положение цветка.
	 * @param spot - участок для расположения цветка.
	 */
	@Override
	public void setSpot(Spot spot) {
		rootSpot = spot;
	}

	/**
	 * Возвращаем положение цветка.
	 * @return расположение цветка.
	 */
	@Override
	public Spot getSpot() {
		return rootSpot;
	}

	/**
	 * Где растёт.
	 */
	protected Spot rootSpot;

	/**
	 * В каком состоянии находится.
	 */
	protected PlantState state;
		
	/**
	 * Вырасти.
	 */
	protected void growAdult() {
		state = PlantState.ADULT;
	}
	
	/**
	 * Цвести.
	 */
	protected void bloom() {
		state = PlantState.BLOOMING;
	}
	
	/**
	 * Плодоносить и разбрасывать зёрна.
	 */
	protected void fruit() {
		// Запрашиваем у клетки, в которой находимся, случайного соседа, а пытаемся посадить в него семя.
		// Может завершиться неудачей, если в выбранном соседском участке улей или другое растение.
		getSpot().getRandomNaighbour().plantFlower(this);
	}
	
	/**
	 * Погибнуть.
	 */
	protected void die() {
		// Посылаем запрос родительскому участку об удалении.
		getSpot().removeSpotAgent(this);
	}
	
	/**
	 * Отдать накопленный нектар.
	 * @return число единиц переданного нектара.
	 */
	public int giveAwayHoneydew() {
		if (state == PlantState.BLOOMING) {
			return honeydewCount;
		}

		return 0;
	}

	/**
	 * Отдать пыльцу.
	 * @return число единиц переданной пыльцы. По факту на данном этапе имеет бинарную природу - пыльца либо есть (1)б либо нет (0).
	 */
	public int giveAwayPollen() {
		if (state == PlantState.BLOOMING) {
			return 1;
		}

		return 0;
	}

	/**
	 * Метод опыления текущего цветка.
	 * @param b - пчела с пыльцой.
	 */
	public void pollinate(Bee bee) {
		// Если растение цветет.
		if (state == PlantState.BLOOMING) {
			// Если на пчеле есть пыльца того же растения.
			if (bee.getPollen(this) != 0) {
				// Меняем состояние растение на опыленное.
				state = PlantState.POLLINATED;
			}
		}
 }
	/**
	 * Текущий тик жизни цветка.
	*/
	protected int age;

	/**
	 * Возраст, в котором растение переходит к определенному состоянию.
	 * */
	protected int seedlingTime = 1;
	protected int adultTime;
	protected int bloomingTime;
	protected int fruitingTime;
	protected int lifetime;

	/**
	 * Количество единиц нектара, которое отдаёт цветущее растение.
	 * */
	protected int honeydewCount = 10;
}

/**
 * Перечисление состояний цветка: проросток, взрослое растение, цветущее растение, опылённое растение.
 * @author klimenko
 *
 */
enum PlantState {
	SEEDLING, 
	ADULT, 
	BLOOMING, 
	POLLINATED 
}

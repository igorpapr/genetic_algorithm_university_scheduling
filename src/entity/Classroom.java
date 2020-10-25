package entity;

public class Classroom {

	private final String name;

	private final int numberOfSeats;

	public Classroom(int numberOfSeats, String name) {
		this.numberOfSeats = numberOfSeats;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	@Override
	public String toString() {
		return "Classroom: ['" + name + '\'' +
				", number of seats=" + numberOfSeats +
				']';
	}
}

package entity;

import java.time.LocalTime;

public class ClassTime implements Comparable<ClassTime> {

	private final String name;

	private final LocalTime start;
	private final LocalTime end;

	public ClassTime(String name, LocalTime start, LocalTime end) {
		if (end.isBefore(start)){
			throw new IllegalArgumentException("Wrong time: start time must be before end time of the same day");
		}
		this.name = name;
		this.start = start;
		this.end = end;
	}

	public String getName() {
		return name;
	}

	public LocalTime getStart() {
		return start;
	}

	public LocalTime getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(ClassTime o) {
		return this.start.compareTo(o.getStart());
	}
}

package entity;

public class Subject {

	private String name;

	private Teacher lecturer;

	private Teacher[] practicesTeachers;

	private Student[] students;

	private int numberOfPracticesInAWeek;

	public Subject(String name, Teacher lecturer, Teacher[] practicesTeachers, Student[] students, int numberOfPracticesInAWeek) {
		this.name = name;
		this.lecturer = lecturer;
		this.practicesTeachers = practicesTeachers;
		this.students = students;
		this.numberOfPracticesInAWeek = numberOfPracticesInAWeek;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getLecturer() {
		return lecturer;
	}

	public void setLecturer(Teacher lecturer) {
		this.lecturer = lecturer;
	}

	public Teacher[] getPracticesTeachers() {
		return practicesTeachers;
	}

	public void setPracticesTeachers(Teacher[] practicesTeachers) {
		this.practicesTeachers = practicesTeachers;
	}

	public Student[] getStudents() {
		return students;
	}

	public void setStudents(Student[] students) {
		this.students = students;
	}

	public int getNumberOfPracticesInAWeek() {
		return numberOfPracticesInAWeek;
	}

	public void setNumberOfPracticesInAWeek(int numberOfPracticesInAWeek) {
		this.numberOfPracticesInAWeek = numberOfPracticesInAWeek;
	}

	@Override
	public String toString() {
		return "Subject ['" + name + "', lecturer: " + lecturer +
				']';
	}
}

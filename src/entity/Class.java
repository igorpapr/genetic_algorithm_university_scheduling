package entity;

//means university class (a.k.a lesson)
public class Class {

	private Subject subject;

	private ClassTime classTime;

	private DayOfTheWeek dayOfTheWeek;

	private boolean isLection;

	private Teacher teacher;

	private Student[] students;

	private Classroom classroom;

	public Class() {
	}

	public Class(Subject subject, ClassTime classTime, DayOfTheWeek dayOfTheWeek, boolean isLection, Teacher teacher, Student[] students, Classroom classroom) {
		this.subject = subject;
		this.classTime = classTime;
		this.dayOfTheWeek = dayOfTheWeek;
		this.isLection = isLection;
		this.teacher = teacher;
		this.students = students;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public ClassTime getClassTime() {
		return classTime;
	}

	public void setClassTime(ClassTime classTime) {
		this.classTime = classTime;
	}

	public DayOfTheWeek getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(DayOfTheWeek dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public boolean isLection() {
		return isLection;
	}

	public void setLection(boolean lection) {
		isLection = lection;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Student[] getStudents() {
		return students;
	}

	public void setStudents(Student[] students) {
		this.students = students;
	}

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
}

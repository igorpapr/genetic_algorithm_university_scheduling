import entity.Student;
import entity.Subject;
import entity.Teacher;
import schedule.Schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static final List<Teacher> TEACHERS = Arrays.asList(
			new Teacher("Vozniuk Yaroslav"),
			new Teacher("Dr.Vasia Pupkin"),
			new Teacher("Fizruk Zabukhovich"),
			new Teacher("Trudovik Alkoholevich"),

			new Teacher("Pidpyshys' Na"),
			new Teacher("Troshu @trosha_b")
	);
	public static final List<Student> STUDENTS = Arrays.asList(
			new Student("Petro Petrov"),
			new Student("Andrii Andriev"),
			new Student("Ihor Igorischevich"),
			new Student("Grisha Grigoriev"),
			new Student("Vasil Vasiliov"),
			new Student("Slava Ukraini"),
			new Student("Heroyam Slava"),
			new Student("Please follow"),
			new Student("My instagram"),
			new Student("@daaamn.paprocya"),

			new Student("Salo Sila"),
			new Student("Sport Kyiv-Mohyla")
	);

	public static final List<Subject> SUBJECTS = Arrays.asList(

	);


	public static void main(String[] args){
		List<Subject> subjects = new ArrayList<>();
		//subjects.add(new Subject("English"));
		Schedule schedule = new Schedule(subjects);

	}
}
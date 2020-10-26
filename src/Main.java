import entity.Student;
import entity.Subject;
import entity.Teacher;
import schedule.Schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Teacher t1 = new Teacher("Vozniuk Yaroslav");
        Teacher t2 = new Teacher("Dr.Vasia Pupkin");

        Teacher t3 = new Teacher("Fizruk Zabukhovich");
        Teacher t4 = new Teacher("Trudovik Alkoholevich");

        Teacher t5 = new Teacher("Pidpyshys' Na");
        Teacher t6 = new Teacher("Troshu @trosha_b");

        Teacher t7 = new Teacher("A takozh na");
        Teacher t8 = new Teacher("@alex.lzv");
//        List<Teacher> TEACHERS = Arrays.asList(t1, t2, t3, t4, t5, t6);

        Student st1 = new Student("Petro Petrov");
        Student st2 = new Student("Andrii Andriev");
        Student st3 = new Student("Ihor Igorischevich");
        Student st4 = new Student("Grisha Grigoriev");
        Student st5 = new Student("Vasil Vasiliov");
        Student st6 = new Student("Slava Ukraini");
        Student st7 = new Student("Heroyam Slava");
        Student st8 = new Student("Please follow");
        Student st9 = new Student("My instagram");
        Student st10 = new Student("@daaamn.paprocya");
//        Student st11 = new Student("Salo Sila");
//        Student st12 = new Student("Sport Kyiv-Mohyla");
//        List<Student> STUDENTS = Arrays.asList(st1, st2, st3, st4, st5, st6, st7, st8, st9, st10);

        Subject s1 = new Subject(
                "Analysis",
                t1,
                new Teacher[]{t1, t2},
                new Student[]{st1, st2, st3, st4},
                1,
                1
        );

        Subject s2 = new Subject(
                "Proga",
                t3,
                new Teacher[]{t3, t4},
                new Student[]{st3, st4, st5, st6},
                2,
                1
        );

        Subject s3 = new Subject(
                "Fizra",
                t5,
                new Teacher[]{t5, t6},
                new Student[]{st5, st6, st7, st8},
                1,
                0
        );

        Subject s4 = new Subject(
                "Algebra",
                t7,
                new Teacher[]{t7, t8},
                new Student[]{st7, st8, st9, st10},
                2,
                1
        );


        List<Subject> subjects = Arrays.asList(s1, s2, s3, s4);
        Schedule schedule = new Schedule(subjects);
        schedule.findSolution();
    }
}
package entity;

import schedule.Schedule;
import utils.Logger;
import utils.ScheduleUtils;

import java.util.*;

public class Chromosome {

    //likelihood (in percent) of the mutation
    public static final double MUTATION_LIKELIHOOD = .5;

    //array of genes
    private Class[] genome;

    //fitness
    private double fitness;


    private List<Subject> subjectList;
    private Integer genesCount;

    public Chromosome(List<Subject> subjects, Integer genesCount) {
        this.subjectList = subjects;
        this.genesCount = genesCount;
    }

    /*
     * Fills a chromosome with random genes.
     * */
    public void fillChromosomeWithRandomGenes(){
        genome = new Class[genesCount];
        Set<Integer> used = new HashSet<>();
        int counter = 0;
        int index = 0;
        while(counter != genesCount){
            while(used.contains(index)){
                index = ScheduleUtils.getRandomInt(0,subjectList.size()-1);
            }
            used.add(index);
            Subject s = subjectList.get(index);
            for(int i = 0; i < subjectList.get(index).getNumberOfLectionsInAWeek(); i++){
                genome[counter++] = fillClassWithData(s, true);
            }
            for (int j = 0; j < subjectList.get(index).getNumberOfPracticesInAWeek(); j++) {
                genome[counter++] =fillClassWithData(subjectList.get(index), false);
            }
        }
    }

    /*
      This crossover gives birth to 2 children
    */
    public Chromosome[] doubleCrossover(  Chromosome chromosome  ){
        Logger.log( "Starting DOUBLE crossover operation..." );
        Logger.log( "THIS chromo:"+this );
        Logger.log( "ARG chromo:"+chromosome );

        int crossoverline = ScheduleUtils.getRandomInt(0, genesCount - 2);
        Chromosome[] result = new Chromosome[2];
        result[0]=new Chromosome(subjectList, genesCount);
        result[1]=new Chromosome(subjectList, genesCount);

        Class[] resultGenome0 = new Class[genesCount];
        Class[] resultGenome1 = new Class[genesCount];

        //filling the first part of resultGenome0
        System.arraycopy(this.genome, 0, resultGenome0,0, crossoverline);
        //filling the first part of resultGenome1
        System.arraycopy(chromosome.getGenome(), 0, resultGenome1, 0,crossoverline);
        //filling the second part of resultGenome0
        System.arraycopy(chromosome.getGenome(), crossoverline, resultGenome0, crossoverline,genesCount - crossoverline);
        //filling the second part of resultGenome0
        System.arraycopy(this.genome, crossoverline, resultGenome1,crossoverline, genesCount - crossoverline);

        result[0].setGenome(resultGenome0);
        result[1].setGenome(resultGenome1);

        Logger.log( "RESULTING chromo #0:\n"+result[0] );
        Logger.log( "RESULTING chromo #1:\n"+result[1] );
        Logger.log( "DOUBLE crossover operation is finished..." );
        return result;
    }

    /*
        This crossover gives birth to 1 child.
        To perform that, it calls doubleCrossover() and then
        randomly selects one of the 2 children.
    */
    public Chromosome singleCrossover(Chromosome chromosome){
        Chromosome[] children = doubleCrossover(  chromosome  );
        int childNumber = ScheduleUtils.getRandomInt(0, 1);
        Logger.log( "SINGLE crossover operation is finished" );
        return children[childNumber];
    }

    public void mutateWithGivenLikelihood(){
        for (int i=0;i<genesCount;++i){
            double randomPercent = ScheduleUtils.getRandomFloat(0,100);
            if (randomPercent < MUTATION_LIKELIHOOD ){
                Logger.log( " Genome before the mutation "+genome[i]);
                genome[i] = fillClassWithData(genome[i].getSubject(), genome[i].isLection());
                Logger.log( " Genome of the mutated chromosome "+genome[i]);
            }
        }
    }

    public Class fillClassWithData(Subject s, boolean isLection){
        Class res = new Class();
        res.setLection(isLection);
        res.setDayOfTheWeek(DayOfTheWeek.values()[ScheduleUtils.getRandomInt(0,DayOfTheWeek.values().length-1)]);
        res.setClassTime(Schedule.CLASS_TIMES.get(ScheduleUtils.getRandomInt(0,Schedule.CLASS_TIMES.size() - 1)));
        res.setSubject(s);
        res.setClassroom(Schedule.CLASSROOMS.get(ScheduleUtils.getRandomInt(0,Schedule.CLASSROOMS.size() - 1)));
        res.setTeacher(isLection?s.getLecturer():s.getPracticesTeachers()[ScheduleUtils.getRandomInt(0,s.getPracticesTeachers().length-1)]);

        if(isLection){
            res.setStudents(s.getStudents());
        }else{
            //choosing students randomly because no better approach were found
            int groupAmount = s.getStudents().length / s.getNumberOfPracticesInAWeek();
            Student[] studentsRandomed = new Student[groupAmount];
            for (int i = 0; i < groupAmount; i++){
                studentsRandomed[i] = s.getStudents()[ScheduleUtils.getRandomInt(0,s.getStudents().length - 1)];
            }
            res.setStudents(studentsRandomed);
        }
        return res;
    }


    /*
        - The auditory with less than 8 seats can't be used for lections
        - A single teacher can't conduct more than one class in one time
        - A single student can't be on a more than one class in one time
        - A classroom must have enough seats for the class
     */
    public void  calculateFitness(){
        fitness = 0;
        for (Class itemCurrent : genome) {
            for (Class itemThat : genome) {
                if (itemCurrent != itemThat) {
                    boolean equalDayAndTime = itemCurrent.getDayOfTheWeek().equals(itemThat.getDayOfTheWeek())
                            && itemCurrent.getClassTime().equals(itemThat.getClassTime());
                    if (!equalDayAndTime){
                        fitness += 5; //adding 5 for every class in not equal time and day
                    } else if(itemCurrent.getClassroom().equals(itemThat.getClassroom())){
                        fitness -= 2;  //else removing 2 if classroom is the same
                    }
                    if(itemCurrent.getTeacher().equals(itemThat.getTeacher())){
                        fitness -= 1; // removing 1 if teacher is the same
                    }
                    if (equalDayAndTime) {
                        fitness -= countEqualStudents(itemCurrent, itemThat);
                        //removing the number of students with the same classes
                    }
                }
            }

            if(itemCurrent.getClassroom().getNumberOfSeats() <
                    itemCurrent.getStudents().length)
                fitness-=8;
            if(itemCurrent.isLection() &&
                    itemCurrent.getClassroom().getNumberOfSeats() < 8)
                fitness-=10;
        }
    }

    private int countEqualStudents(Class a, Class b){
        int res = 0;
        for (Student student: a.getStudents()){
            for (Student student1: b.getStudents()){
                if (student.equals(student1)){
                    res += 1;
                }
            }
        }
        return res;
    }


    public Class[] getGenome() {
        return genome;
    }

    public void setGenome(Class[] genome) {
        this.genome = genome;
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Chromosome:\n");
        HashMap<DayOfTheWeek, LinkedHashMap<ClassTime, Class>> map = new HashMap<>();
        for(DayOfTheWeek day: DayOfTheWeek.values()){
            map.put(day, new LinkedHashMap<>());
        }
        for (Class class1: genome){
            map.get(class1.getDayOfTheWeek()).put(class1.getClassTime(), class1);
        }
        for(DayOfTheWeek day1: DayOfTheWeek.values()){
            res.append(day1);
            res.append(": \n");
            for(ClassTime time: Schedule.CLASS_TIMES){
                if(map.get(day1).containsKey(time)){
                    res.append(time);
                    res.append(": \n");
                    res.append(map.get(day1).get(time));
                }
            }
            res.append("----------");
        }
        res.append(",\nfitness=").append(fitness).append(",\ngenesCount=").append(genesCount).append("]\n");
        return res.toString();
    }
}
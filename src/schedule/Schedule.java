package schedule;

import entity.Chromosome;
import entity.ClassTime;
import entity.Classroom;
import entity.Subject;
import utils.Logger;
import utils.ScheduleUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Schedule {

    //number of genes in a genome
    private Integer GENES_COUNT = 0;

    //number of individuals in population
    public static final int POPULATION_COUNT = 200;

    //number of individuals participating in tournament selection
    public static final int TOURNAMENT_PARTICIPANTS_COUNT = 5;

    //number of "generations".
    public static final int MAX_ITERATIONS = 200;

    public static final List<ClassTime> CLASS_TIMES = Arrays.asList(
            new ClassTime("1st class", LocalTime.of(8,30),LocalTime.of(9,50)),
            new ClassTime("2nd class", LocalTime.of(10,0), LocalTime.of(11,20)),
            new ClassTime("3nd class", LocalTime.of(11,40), LocalTime.of(13,0)),
            new ClassTime("4nd class", LocalTime.of(13,30), LocalTime.of(14,50)),
            new ClassTime("5nd class", LocalTime.of(15,0), LocalTime.of(16,20)),
            new ClassTime("6nd class", LocalTime.of(16,30), LocalTime.of(17,50))
    );

    public static final List<Classroom> CLASSROOMS = Arrays.asList(
            new Classroom(5,"1-222"),
            new Classroom(10,"1-111"),
            new Classroom(5,"3-310"),
            new Classroom(8,"1-313"),
            new Classroom(8,"1-331"),
            new Classroom(5,"1-202"),
            new Classroom(6,"1-140")
    );

    //array of individuals (Chromosomes)
    private Chromosome[] population =new Chromosome[POPULATION_COUNT];

    private List<Subject> subjects;

    public Schedule(List<Subject> subjects) {
        this.subjects = subjects;
        for (Subject subject: subjects){
            if(subject != null){
                GENES_COUNT += subject.getNumberOfLectionsInAWeek() + subject.getNumberOfPracticesInAWeek();
            }
        }
    }

    /*
     * Creates an initial population
     * */
    public void createInitialPopulation(){
        Logger.log("*** Started creating initial population...");
        for (int i = 0; i<POPULATION_COUNT;++i){
            Logger.log("Creating chromosome number "+i);
            population[i]=new Chromosome(subjects, GENES_COUNT);
            population[i].fillChromosomeWithRandomGenes();
        }
        Logger.log("*** FINISHED creating initial population...");
    }

    /*
     * Iterate through all chromosomes and fill their "fitness" property
     * */
    private void fillChromosomesWithFitnesses(){
        Logger.log( "***Started to create FITNESSES for all chromosomes. " );
        for ( int i=0; i<POPULATION_COUNT;++i ){
            Logger.log("Filling with fitness population number "+i);
            population[i].calculateFitness();
            Logger.log("Fitness: "+population[i].getFitness());
        }
        Logger.log( "***Finished to create FITNESSES for all chromosomes. " );
    }

    /*
     * Returns pairs for the crossover operations.
     * [0][0] mates with [0][1]
     * [1][0] mates with [1][1]
     * etc. etc.
     * */
    private int[][] getPairsForCrossover(){
        Logger.log("*** Started looking for pairs for crossover");
        int[][] pairs = new int[POPULATION_COUNT][2];
        for (int i = 0; i<POPULATION_COUNT;++i){
            Logger.log("Looking for pair number "+i+"...");
            int firstChromosome =  findIndividualForCrossoverByTournament()   ;
            Logger.log("First individual...  corresponding chromosome:"+firstChromosome+
                    "; chromosome's fitness*100: "+population[firstChromosome].getFitness()*100);
            int secondChromosome;
            do{
                secondChromosome =  findIndividualForCrossoverByTournament()   ;
            }while (firstChromosome==secondChromosome) ;  //prevent individual's crossover with itself :)

            Logger.log("Second individual...  corresponding chromosome:"+secondChromosome+
                    "; chromosome's fitness*100: "+population[secondChromosome].getFitness()*100);
            pairs[i][0] = firstChromosome;
            pairs[i][1] = secondChromosome;
        }
        Logger.log("*** Finished looking for pairs for crossover");
        return pairs;
    }

    /*
     * Performs a tournament between TOURNAMENT_PARTICIPANTS_COUNT individuals,
     * selects the best of them and returns its index in population[] array
     * */
    private int findIndividualForCrossoverByTournament(){

        Logger.log( "starting findIndividualForCrossoverByTournament()" ) ;

        int bestIndividualNumber=0;
        double bestFitness=0;

        for ( int i=0;i<TOURNAMENT_PARTICIPANTS_COUNT;++i ){
            int currIndividualNumber = ScheduleUtils.getRandomInt( 0 , POPULATION_COUNT-1);

            if ( population[ currIndividualNumber ].getFitness() > bestFitness    ){
                bestFitness = population[ currIndividualNumber ].getFitness();
                bestIndividualNumber = currIndividualNumber;
            }

            Logger.log( "i="+i+"; currIndividualNumber="+currIndividualNumber+
                    "; bestFitness="+bestFitness+";bestIndividualNumeber="+bestIndividualNumber   );

        }

        return bestIndividualNumber;
    }

    private  Chromosome[] performCrossoverAndMutationForThePopulationAndGetNextGeneration(  int[][] pairs ){

        Chromosome[] nextGeneration =new Chromosome[POPULATION_COUNT];

        Logger.log("*******************************");
        Logger.log("Starting performing Crossover operation For The Population...");

        //the best individual goes to the next generation in any case.
        //Please note that because of this we start the next loop from 1, not from 0
        nextGeneration[0] = findIndividualWithMaxFitness();

        for (int i = 1; i<POPULATION_COUNT;++i){
            Logger.log(" Starting crossover #"+i);
            Chromosome firstParent = population[  pairs[i][0]  ];
            Chromosome secondParent = population[  pairs[i][1]  ];
            Logger.log("First parent (#"+pairs[i][0]+")\n" + firstParent );
            Logger.log("Second parent (#"+pairs[i][1]+")\n" + secondParent );

            Chromosome result = firstParent.singleCrossover( secondParent );
            nextGeneration[i]=result;
            Logger.log( "Resulting (child) chromosome BEFORE the mutation:\n"+ nextGeneration[i]);

            nextGeneration[i].mutateWithGivenLikelihood();

            Logger.log( "Resulting (child) chromosome AFTER the mutation:\n"+ nextGeneration[i]);
            Logger.log(" Finished crossover #"+i);
        }

        Logger.log("Finished performing Crossover operation For The Population...");
        return nextGeneration;
    }

    public Chromosome findIndividualWithMaxFitness(){
        double currMaxFitness = 0;
        Chromosome result = population[0];
        for (int i = 0; i<POPULATION_COUNT;++i){
            if ( population[i].getFitness() > currMaxFitness ){
                result = population[i];
                currMaxFitness = population[i].getFitness();
            }
        }
        return result;
    }

    public Chromosome[] getPopulation() {
        return population;
    }

    public void setPopulation(Chromosome[] population) {
        this.population = population;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    //main algorithm function
    public void findSolution(){
        Logger.log("POPULATION_COUNT=" + POPULATION_COUNT);
        Logger.log("GENES_COUNT=" + GENES_COUNT);
        createInitialPopulation();
        long iterationNumber = 0;
        do {
            fillChromosomesWithFitnesses();
            System.out.println( "-=-=========== Finished iteration #" + iterationNumber  );
            System.out.println( "Best individual: " + findIndividualWithMaxFitness()  );
            int[][] pairs = getPairsForCrossover();
            Chromosome[] nextGeneration;
            nextGeneration = performCrossoverAndMutationForThePopulationAndGetNextGeneration(  pairs );
            setPopulation(nextGeneration);
        } while ( iterationNumber++<MAX_ITERATIONS);
        System.out.println("============================\n" +
                "RESULT: \nBEST GENERATION FITNESS: " + getPopulation()[0].getFitness() +
                "\n ITERATIONS: " + iterationNumber +
                "\n BEST GENERATION: "+ getPopulation()[0]);
    }

    @Override
    public String toString() {
        return "Schedule[\n" +
                  Arrays.toString(population) +
                ']';
    }
}
import entity.Chromosome;
import entity.Subject;

import java.util.List;
import java.util.Random;

public class Schedule {

    //number of genes in a genome
    public Integer GENES_COUNT;

    //number of individuals in population
    public static final int POPULATION_COUNT = 200;

    //likelihood (in percent) of the mutation
    public static final double MUTATION_LIKELIHOOD = .5;

    //number of individuals participating in tournament selection
    public static final int TOURNAMENT_PARTICIPANTS_COUNT = 5;

    //number of "generations".
    public static final int MAX_ITERATIONS = 200;

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
     * Writes a string to the log
     * */
    public static void log(String message){
        System.out.println( message );
    }

    /*
     * Creates an initial population
     * */
    public void createInitialPopulation(){
        log("*** Started creating initial population...");
        for (int i = 0; i<POPULATION_COUNT;++i){
            log("Creating chromosome number "+i);
            population[i]=new Chromosome();
            Chromosome.fillChromosomeWithRandomGenes(population[i]);
        }
        log("*** FINISHED creating initial population...");
    }

    /*
     * Iterate through all chromosomes and fill their "fitness" property
     * */
    private void fillChromosomesWithFitnesses(){
        log( "***Started to create FITNESSES for all chromosomes. " );
        for ( int i=0; i<POPULATION_COUNT;++i ){
            log("Filling with fitness population number "+i);
            double currentFitness = population[i].calculateFitness();
            population[i].setFitness(currentFitness);
            log("Fitness: "+population[i].getFitness());

        }
        log( "***Finished to create FITNESSES for all chromosomes. " );
    }

    /*
     * Returns pairs for the crossover operations.
     * [0][0] mates with [0][1]
     * [1][0] mates with [1][1]
     * etc. etc.
     * */
    private int[][] getPairsForCrossover(){
        log("*** Started looking for pairs for crossover");
        int[][] pairs = new int[POPULATION_COUNT][2];
        for (int i = 0; i<POPULATION_COUNT;++i){
            log("Looking for pair number "+i+"...");
            int firstChromosome =  findIndividualForCrossoverByTournament()   ;
            log("First individual...  corresponding chromosome:"+firstChromosome+
                    "; chromosome's fitness*100: "+population[firstChromosome].getFitness()*100);
            int secondChromosome;
            do{
                secondChromosome =  findIndividualForCrossoverByTournament()   ;
            }while (firstChromosome==secondChromosome) ;  //prevent individual's crossover with itself :)

            log("Second individual...  corresponding chromosome:"+secondChromosome+
                    "; chromosome's fitness*100: "+population[secondChromosome].getFitness()*100);
            pairs[i][0] = firstChromosome;
            pairs[i][1] = secondChromosome;
        }
        log("*** Finished looking for pairs for crossover");
        return pairs;
    }

    /*
     * Performs a tournament between TOURNAMENT_PARTICIPANTS_COUNT individuals,
     * selects the best of them and returns its index in population[] array
     * */
    private int findIndividualForCrossoverByTournament(){

        log( "starting findIndividualForCrossoverByTournament()" ) ;

        int bestIndividualNumber=0;
        double bestFitness=0;

        for ( int i=0;i<TOURNAMENT_PARTICIPANTS_COUNT;++i ){
            int currIndividualNumber = getRandomInt( 0 , POPULATION_COUNT-1);

            if ( population[ currIndividualNumber ].getFitness() > bestFitness    ){
                bestFitness = population[ currIndividualNumber ].getFitness();
                bestIndividualNumber = currIndividualNumber;
            }

            log( "i="+i+"; currIndividualNumber="+currIndividualNumber+
                    "; bestFitness="+bestFitness+";bestIndividualNumeber="+bestIndividualNumber   );

        }

        return bestIndividualNumber;
    }

    private  Chromosome[] performCrossoverAndMutationForThePopulationAndGetNextGeneration(  int[][] pairs ){

        Chromosome[] nextGeneration =new Chromosome[POPULATION_COUNT];

        log("*******************************");
        log("Starting performing Crossover operation For The Population...");

        //the best individual goes to the next generation in any case.
        //Please note that because of this we start the next loop from 1, not from 0
        nextGeneration[0] = findIndividualWithMaxFitness();

        for (int i = 1; i<POPULATION_COUNT;++i){
            log(" Starting crossover #"+i);
            Chromosome firstParent = population[  pairs[i][0]  ];
            Chromosome secondParent = population[  pairs[i][1]  ];
            log("First parent (#"+pairs[i][0]+")\n" + firstParent );
            log("Second parent (#"+pairs[i][1]+")\n" + secondParent );

            Chromosome result = firstParent.singleCrossover( secondParent );
            nextGeneration[i]=result;
            log( "Resulting (child) chromosome BEFORE the mutation:\n"+ nextGeneration[i]);

            nextGeneration[i]=nextGeneration[i].mutateWithGivenLikelihood();

            log( "Resulting (child) chromosome AFTER the mutation:\n"+ nextGeneration[i]);
            log(" Finished crossover #"+i);
        }

        log("Finished performing Crossover operation For The Population...");
        return nextGeneration;
    }

    /*
     * Returns random integer number between min and max ( all included :)  )
     * */
    public static int getRandomInt( int min, int max ){
        Random randomGenerator;
        randomGenerator = new Random();
        return  randomGenerator.nextInt( max+1 ) + min ;
    }

    /*
     * Returns random float number between min (included) and max ( NOT included :)  )
     * */
    public static float getRandomFloat( float min, float max ){
        return  (float) (Math.random()*max + min) ;
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
        Schedule.log("POPULATION_COUNT=" + POPULATION_COUNT);
        Schedule.log("GENES_COUNT=" + GENES_COUNT);
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
        } while ( iterationNumber++<MAX_ITERATIONS );
    }

}
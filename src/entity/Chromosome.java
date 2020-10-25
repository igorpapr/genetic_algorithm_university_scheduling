package entity;

import schedule.Schedule;
import utils.Logger;
import utils.ScheduleUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        //TODO

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
        //...
        return null;
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

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public Integer getGenesCount() {
        return genesCount;
    }

    public void setGenesCount(Integer genesCount) {
        this.genesCount = genesCount;
    }
}
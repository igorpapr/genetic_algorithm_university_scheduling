package entity;

public class Chromosome {

	//array of genes
	private Class[] genome;

	//fitness
	private double fitness;

	public Chromosome() {
	}

	public Chromosome(Class[] genome, double fitness) {
		this.genome = genome;
		this.fitness = fitness;
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


}

package se.umu.cs.geneticReanimation;

import java.util.*;

public class GeneticAlgoritm {
	private int genotypeSize;
	private int populationSize; 
	private double crossoverRate;
	private double mutationRate;
	private List<Creature> population;
	
	
	public GeneticAlgoritm (int genotypeSize, int populationSize, double crossoverRate, double mutationRate) {
		this.genotypeSize = genotypeSize;
		this.populationSize = populationSize;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
	}
	
	/**
	 * Creates a new population
	 * @param popSize int for size of the new population
	 * @return a List of the new population
	 */
	public List createPopulation(){
		population = new ArrayList<Creature>();
		for (int i = 1; i<=populationSize; i++) {
			population.add(createNewCreature());
		}
		return population;
	}
	
	/**
	 * Creates a new generation
	 * 
	 * @param oldPopulation List over the old population
	 * @return
	 */
	public List createNextGeneration(List oldPopulation) {
		oldPopulation = population;
		ArrayList newPopulation = new ArrayList<Creature>();
		
		Creature parent1;
		Creature parent2;
		int index = 0;
		for (int i = 0; i <=(populationSize*crossoverRate); i++) {
			parent1 = tournamentSelection();
			parent2 = tournamentSelection();
			newPopulation.add(crossover(parent1, parent2));
			newPopulation.add(crossover(parent2, parent1));
			index = i;
		}
		
		for (int i = index; i<=(populationSize); i++) {
			parent1 = tournamentSelection();
			parent2 = tournamentSelection();
			newPopulation.add(parent1);
			newPopulation.add(parent2);
		}
		
		
		
		population = newPopulation;
		return population;
	}
	
	private Creature crossover(Creature parent1, Creature parent2) {
		double[] parent2Genotype = parent2.getGenotype();
		
		double[] childGenotype = parent1.getGenotype();
		for (int i = genotypeSize/2; i<genotypeSize; i++) {
			childGenotype[i]=parent2Genotype[i];
		}
		Creature child = new Creature(childGenotype.length);
		return child;
	}

	private Creature tournamentSelection() {
		Creature bestParent = null;
		for (int i = 0; i<3; i++) {
			Creature parent = population.get((int)Math.random()*populationSize);
			if (bestParent == null || parent.getFitness() >= bestParent.getFitness()) {
				bestParent = parent;
			}
		}
		return bestParent;
	}

	private Creature createNewCreature() {
		double[] newGenotype = new double[genotypeSize];
		for (int i = 0; i<genotypeSize; i++) {
			newGenotype[i] = ((Math.random()*2)-1);
		}
		return new Creature(newGenotype.length);
	}
	
}

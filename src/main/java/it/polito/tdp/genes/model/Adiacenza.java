package it.polito.tdp.genes.model;

public class Adiacenza  implements Comparable<Adiacenza> {
	
	private Genes g1;
	private Genes g2;
	private Double peso;
	
	
	public Adiacenza(Genes g1, Genes g2, double peso) {
		super();
		this.g1 = g1;
		this.g2 = g2;
		this.peso = peso;
	}


	public Genes getG1() {
		return g1;
	}


	public Genes getG2() {
		return g2;
	}


	public double getPeso() {
		return peso;
	}


	@Override
	public int compareTo(Adiacenza o) {
		
		return - this.peso.compareTo(o.getPeso());
	}
	
	

}

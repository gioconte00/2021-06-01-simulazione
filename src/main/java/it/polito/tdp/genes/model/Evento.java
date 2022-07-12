package it.polito.tdp.genes.model;

public class Evento implements Comparable<Evento>{

	private int time;
	private int nIng;
	private Genes gene;
	
	public Evento(int time, int nIng, Genes gene) {
		super();
		this.time = time;
		this.nIng = nIng;
		this.gene = gene;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getnIng() {
		return nIng;
	}

	public void incrementnIng() {
		this.nIng ++;
	}

	public Genes getGene() {
		return gene;
	}

	public void setGene(Genes gene) {
		this.gene = gene;
	}

	@Override
	public int compareTo(Evento o) {
		return this.time-o.time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gene == null) ? 0 : gene.hashCode());
		result = prime * result + time;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (gene == null) {
			if (other.gene != null)
				return false;
		} else if (!gene.equals(other.gene))
			return false;
		if (time != other.time)
			return false;
		return true;
	}	
	
}
	
	



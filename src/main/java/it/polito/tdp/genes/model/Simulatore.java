package it.polito.tdp.genes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {

	//coda eventi
	private PriorityQueue<Evento> coda;
	
	//input
	private int nInizialeIng;
	private Genes geneIniziale;
	
	//output
	private Map<Genes, Integer> result;
	
	//stato mondo 
	Map<Genes, Integer> geniNuovi = new HashMap<>();
	private Graph<Genes, DefaultWeightedEdge> grafo;
	
	
	//costruttore
	public Simulatore(Graph<Genes, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}
	
	//inizializzazione
	public void init(int nInizialeIng, Genes geneIniziale) {
		
		this.nInizialeIng = nInizialeIng;
		this.geneIniziale = geneIniziale;
		this.result = new HashMap<>();
		
		this.coda = new PriorityQueue<>();
		this.coda.add(new Evento(1, this.nInizialeIng, this.geneIniziale));
	}
	
	//run
	public void run() {
		
		while (!this.coda.isEmpty()) {
			Evento e = this.coda.poll();
			processEvent(e);
		}
		
		
	}

	private void processEvent(Evento e) {
		
		if (e.getTime()<12*3) {
			int ingNonCambiano = 0;
			
			for (int i=0; i<e.getnIng(); i++) {
				double prob = Math.random()*100;
				if (prob<30) 
					ingNonCambiano ++;
				else {
					Genes g = calcolaGene(e.getGene());
					if (!geniNuovi.containsKey(g))
						geniNuovi.put(g, 1);
					else {
						int v = geniNuovi.get(g);
						geniNuovi.replace(g, v++);
					}
					
				}
			}
			
			if (ingNonCambiano>0) 
				this.coda.add(new Evento(e.getTime()+1, ingNonCambiano, e.getGene()));
			for (Genes ge: geniNuovi.keySet())
				this.coda.add(new Evento(e.getTime()+1, geniNuovi.get(ge), ge));
			
		} else if (e.getTime()==12*3) {
			this.result.put(e.getGene(), e.getnIng());
		}
	}

	private Genes calcolaGene(Genes genes) {

		double prob = Math.random();
		double p = 0;
		Genes gene = null;
		List<Genes> vicini = Graphs.neighborListOf(this.grafo, genes);
		for (Genes g: vicini) {
			if (prob>p && p<=p + this.grafo.getEdgeWeight(this.grafo.getEdge(genes, g)))
				gene = g;
			p += this.grafo.getEdgeWeight(this.grafo.getEdge(genes, g));
		}
		
		return gene;
	}
	
	public Map<Genes, Integer> getRisultatoSim() {
		return this.result;
	}
}
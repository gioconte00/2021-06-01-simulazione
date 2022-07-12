package it.polito.tdp.genes.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	private Graph<Genes, DefaultWeightedEdge> grafo;
	private Map<String, Genes> idMap;
	
	private Simulatore s;
	
	
	public Model() {
		
		this.dao = new GenesDao();
		this.idMap = new HashMap<>();
	}
	
	public String creaGrafo(String essentials) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.getVertici(essentials, idMap);
		
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		for(Adiacenza a : this.dao.getArchi(essentials, idMap)) {
			if(!this.grafo.containsEdge(a.getG1(), a.getG2())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getG1(), a.getG2(), a.getPeso());
			}
		}
		
		return "Grafo creato con "+this.grafo.vertexSet().size()+" vertici e "+this.grafo.edgeSet().size()+
				" archi";
	}
	
	public Set<Genes> getVertici() {
		return this.grafo.vertexSet();
	}
	
	public List<Adiacenza> getAdiacenti(Genes gene) {
		
		List<Genes> adiacenti = Graphs.neighborListOf(this.grafo, gene);
		List<Adiacenza> result = new LinkedList<Adiacenza>();
		
		for(Genes g : adiacenti) {
			
			result.add(new Adiacenza(gene, g, this.grafo.getEdgeWeight(this.grafo.getEdge(gene, g))));
			Collections.sort(result);
		}
		return result;
	}
	
	public Map<Genes, Integer> simula(int IngIniziale, Genes g) {
		
		Simulatore s = new Simulatore(this.grafo);
		s.init(IngIniziale, g);
		s.run();
		return s.getRisultatoSim();
	}
	
	
}

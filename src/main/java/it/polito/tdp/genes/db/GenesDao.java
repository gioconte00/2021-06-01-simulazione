package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public void getVertici (String essential, Map<String, Genes> idMap) {
		
		String sql = "SELECT distinct GeneID, Essential, Chromosome "
				+ "FROM genes "
				+ "WHERE Essential=? ";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, essential);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				
				idMap.put(genes.getGeneId(), genes);
			}
			
			res.close();
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
	}
	}

	public List<Adiacenza> getArchi(String essentials, Map<String, Genes> idMap) {
		
		String sql = "SELECT g1.GeneID AS id1, g2.GeneID AS id2, ABS(i.Expression_Corr) AS peso "
				+ "FROM interactions i, genes g1, genes g2 "
				+ "WHERE i.GeneID1 = g1.GeneID AND i.GeneID2 = g2.GeneID AND g1.GeneID<>g2.GeneID "
				+ "	AND g1.Essential = ? AND g1.Essential=g2.Essential "
				+ "GROUP BY g1.GeneID, g2.GeneID ";
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, essentials);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				//se hanno lo stesso cromosoma peso doppio
				if(idMap.get(res.getString("id1")).getChromosome() == 
						idMap.get(res.getString("id2")).getChromosome()) {
					result.add(new Adiacenza (idMap.get(res.getString("id1")), idMap.get(res.getString("id2")),
							res.getDouble("peso")*2));
				} else {
					result.add(new Adiacenza (idMap.get(res.getString("id1")), idMap.get(res.getString("id2")),
							res.getDouble("peso")));
				}
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

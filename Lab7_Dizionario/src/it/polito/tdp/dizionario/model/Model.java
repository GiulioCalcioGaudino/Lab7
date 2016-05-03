package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.dizionario.db.ParolaDAO;

public class Model {
	
	private int len=0 ;

	private List<Parola> dict ;
	
	private UndirectedGraph<Parola, DefaultEdge> graph ;
	
	public int getLen() {
		return len;
	}

	
	public void caricaParole(int len) {
		if (len != this.len) { // altrimenti ce l'ho già
			this.len = len ;
			
			ParolaDAO dao = new ParolaDAO() ;
			this.dict = dao.searchByLength(len) ;
		}
	}
	
	public List<Parola> getDict() {
		return this.dict ;
	}
	
	public void buildGraph() {
		
		graph = new SimpleGraph<Parola, DefaultEdge>(DefaultEdge.class) ;
		
		Graphs.addAllVertices(graph, this.dict) ;
		
		for( Parola p1: this.dict ) {
			for( Parola p2: this.dict ) {
				if( simili(p1, p2) ) {
					graph.addEdge(p1, p2) ;
				}
			}
		}
		
		//System.out.println(graph);
		
	}

	public List<Parola> getVicini(String s) {
		// find Parola from String
		Parola p = findParola(s) ;
		if(p==null) 
			return null ;
		
		return Graphs.neighborListOf(graph, p) ;
	}
	
	public List<Parola> getTutti(String s) {
		// find Parola from String
		Parola p = findParola(s) ;
		if(p==null) 
			return null ;
		
		BreadthFirstIterator<Parola, DefaultEdge>bfs = new BreadthFirstIterator<>(graph, p) ;
		
		List<Parola> tutti = new ArrayList<>() ;
		while(bfs.hasNext()) {
			tutti.add(bfs.next()) ;
		}
		
		return tutti ;
	}

	
	private boolean simili(Parola p1, Parola p2) {
		int diffs = 0 ;
		
		String s1 = p1.getNome() ;
		String s2 = p2.getNome() ;
		
		if(s1.length()!=s2.length()) {
			System.out.println("Something's wrong");
			return false ;
		}
		
		for( int i=0; i< s1.length(); i++ ) {
			if ( s1.charAt(i) != s2.charAt(i) )
				diffs++ ;
		}
		
		if(diffs==1)
			return true ;
		
		return false ;
		
	}
	
	private Parola findParola(String s) {
		
		//TODO optimize lookup with Hash or Tree
		for(Parola p : dict) {
			if(p.getNome().equals(s))
				return p ;
		}
		
		return null ;
	}

}

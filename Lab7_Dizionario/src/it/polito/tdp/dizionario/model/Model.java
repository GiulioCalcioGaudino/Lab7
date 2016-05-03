package it.polito.tdp.dizionario.model;

import java.util.List;

import it.polito.tdp.dizionario.db.ParolaDAO;

public class Model {
	
	private int len=0 ;
	private List<Parola> dict ;
	
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
		
	}

}

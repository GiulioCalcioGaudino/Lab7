package it.polito.tdp.dizionario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.dizionario.model.Parola;

public class ParolaDAO {

	public List<Parola> readAll() {
		Connection conn = DBConnect.getConnection();

		String sql = "SELECT id, nome FROM parola ORDER BY id ;";

		List<Parola> result = new ArrayList<>();

		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {

				Parola p = new Parola(res.getInt("id"), res.getString("nome"));

				result.add(p);
			}

			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public List<Parola> searchByLength(int length) {
		Connection conn = DBConnect.getConnection();

		String sql = "SELECT id, nome FROM parola WHERE CHAR_LENGTH(nome) = ? ORDER BY id ;";

		List<Parola> result = new ArrayList<>();

		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);

			st.setInt(1, length);

			ResultSet res = st.executeQuery();

			while (res.next()) {

				Parola p = new Parola(res.getInt("id"), res.getString("nome"));

				result.add(p);
			}

			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		ParolaDAO dao = new ParolaDAO();

		List<Parola> dict = dao.readAll();
		System.out.println(dict.size());

		for (int len = 1; len < 30; len++) {
			dict = dao.searchByLength(len);
			System.out.format("Len %d: words %d\n", len, dict.size());
		}
	}

	public List<Parola> paroleSimili(Parola p) {

		List<Parola> risultato = new LinkedList<>();

		for (int i = 0; i < p.getNome().length(); i++) {
			risultato.addAll(paroleSimiliInPosizione(p, i));
		}

		return risultato;
	}

	private List<Parola> paroleSimiliInPosizione(Parola p, int pos) {

		String sql = "select id, nome from parola where nome LIKE ?";

		String pattern = p.getNome().substring(0, pos) + "_" + 
				p.getNome().substring(pos + 1) ;

		Connection conn = DBConnect.getConnection() ;
		try {
			List<Parola> result = new LinkedList<>();

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setString(1, pattern);
			
			ResultSet res = st.executeQuery() ;
			
			while (res.next()) {

				Parola p2 = new Parola(res.getInt("id"), res.getString("nome"));

				result.add(p2);
			}

			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		
	}

}

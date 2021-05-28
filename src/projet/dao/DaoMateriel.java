package projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.jdbc.UtilJdbc;
import projet.data.Materiel;


public class DaoMateriel {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoMateriel	daoMateriel;

	
	// Actions

	public int inserer( Materiel materiel )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le materiel
			sql = "INSERT INTO materiel (nom_materiel, quantite_materiel) VALUES ( ?, ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setObject(	1, materiel.getNom_materiel() );
			stmt.setObject(	2, materiel.getQuantite_materiel() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			materiel.setId_materiel( rs.getObject( 1, Integer.class ) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

	
		// Retourne l'identifiant
		return materiel.getId_materiel();
	}

	
	public void modifier( Materiel materiel )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le materiel
			sql = "UPDATE materiel SET nom_materiel = ? quantite_materiel= ? WHERE id_materiel =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, materiel.getNom_materiel() );
			stmt.setObject(	2, materiel.getQuantite_materiel() );
			stmt.setObject( 3, materiel.getId_materiel() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

	}

	
	public void supprimer( int idMateriel )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;


		try {
			cn = dataSource.getConnection();

			// Supprime le materiel
			sql = "DELETE FROM materiel WHERE id_materiel = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idMateriel );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Materiel retrouver( int idMateriel )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM materiel WHERE id_materiel = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idMateriel);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireMateriel(rs, true );
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	
	public List<Materiel> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM materiel ORDER BY nom_materiel";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Materiel> materiels = new ArrayList<>();
			while (rs.next()) {
				materiels.add( construireMateriel(rs, true) );
			}
			return materiels;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	/*
	private void DiminuerMateriel( Materiel materiel, int quantite ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE materiel m, materieldecollecte ms  SET quantite_materiel = ? - ? WHERE id_materiel= ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, materiel.getQuantite_materiel() );
			stmt.setObject( 2, quantite );
			stmt.setObject( 2, materiel.getId_materiel() );
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
*/
	
	public List<Materiel> listerParMateriel( String Materiel )   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			//sql = "SELECT * FROM Materiel WHERE id_profession = ? ORDER BY nom, prenom";
			sql= "SELECT * FROM Materiel p INNER JOIN Materiel c ON p.id_profession = c.id_profession WHERE c.libelle = ? ORDER BY nom, prenom";
			stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, Materiel );
			rs = stmt.executeQuery();
			
			List<Materiel> materiels = new ArrayList<>();
			while (rs.next()) {
				materiels.add( construireMateriel(rs, false) );
			}
			return materiels;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Materiel> listerParCollecte( int id_collecte )   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

	
			sql= "SELECT * FROM Materiel p INNER JOIN materielDeCollecte c ON p.id_materiel = c.id_materiel WHERE c.id_collecte = ? ORDER BY nom, prenom";
			stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, id_collecte );
			rs = stmt.executeQuery();
			
			List<Materiel> materiels = new ArrayList<>();
			while (rs.next()) {
				materiels.add( construireMateriel(rs, true) );
			}
			return materiels;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

    
    public int compterPourMateriel( int idMateriel ) {
    	
		Connection			cn		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM materiel WHERE id_materiel = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setObject( 1, idMateriel );
            rs = stmt.executeQuery();

            rs.next();
            return rs.getInt( 1 );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
    }
	
	
	// Méthodes auxiliaires
	
	private Materiel construireMateriel( ResultSet rs, boolean flagComplet ) throws SQLException {

		Materiel materiel = new Materiel();
		materiel.setId_materiel(rs.getObject( "id_materiel", Integer.class ));
		materiel.setNom_materiel(rs.getObject( "nom_materiel", String.class ));	
		materiel.setQuantite_materiel(rs.getObject( "quantite_materiel", Integer.class ));	
		return materiel;
	}
	
}

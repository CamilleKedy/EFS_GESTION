package projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.jdbc.UtilJdbc;
import projet.data.Collecte;


public class DaoCollecte {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoSite_de_collecte daoSite_de_collecte;

	
	// Actions

	public int inserer( Collecte collecte ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO collecte ( qte_collation, date_debut, date_fin, nbre_infirmiers, nbre_medecins, nbre_secretaire, nbre_agents_collation, horaire_debut, horaire_fin, id_site ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, collecte.getQte_collation());
			stmt.setObject( 2, collecte.getDate_debut());
			stmt.setObject( 3, collecte.getDate_fin());
			stmt.setObject( 4, collecte.getNbre_infirmiers());
			stmt.setObject( 5, collecte.getNbre_medecins());
			stmt.setObject( 6, collecte.getNbre_secretaire());
			stmt.setObject( 7, collecte.getNbre_agents_collation());
			stmt.setObject( 8, collecte.getHoraire_debut());
			stmt.setObject( 9, collecte.getHoraire_fin());
			stmt.setObject( 10, collecte.getSite_de_collecte().getId_site_de_collecte());	
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			collecte.setId_collecte( rs.getObject( 1, Integer.class) );
			
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
		return collecte.getId_collecte();
	}


	public void modifier( Collecte collecte ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE collecte SET qte_collation= ? , date_debut= ?, date_fin= ?, nbre_infirmiers= ?, nbre_medecins= ?, nbre_secretaire=?, nbre_agents_collation= ?, horaire_debut= ?, horaire_fin=?, id_site = ? WHERE idcollecte = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, collecte.getQte_collation());
			stmt.setObject( 2, collecte.getDate_debut());
			stmt.setObject( 3, collecte.getDate_fin());
			stmt.setObject( 4, collecte.getNbre_infirmiers());
			stmt.setObject( 5, collecte.getNbre_medecins());
			stmt.setObject( 6, collecte.getNbre_secretaire());
			stmt.setObject( 7, collecte.getNbre_agents_collation());
			stmt.setObject( 8, collecte.getHoraire_debut());
			stmt.setObject( 9, collecte.getHoraire_fin());
			stmt.setObject( 10, collecte.getSite_de_collecte().getId_site_de_collecte());
			stmt.setObject( 11, collecte.getId_collecte());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimer( int id_collecte ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM collecte WHERE id_collecte = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, id_collecte );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Collecte retrouver( int id_collecte ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM collecte WHERE id_collecte = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, id_collecte);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireCollecte( rs, true );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Collecte> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM collecte ORDER BY id_collecte";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Collecte> collectes = new ArrayList<>();
			while (rs.next()) {
				collectes.add( construireCollecte( rs, true ) );
			}
			return collectes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	  public int compterPourSite( int id_site ) {
	    	
			Connection			cn		= null;
			PreparedStatement	stmt 	= null;
			ResultSet 			rs		= null;

			try {
				cn = dataSource.getConnection();
	            String sql = "SELECT COUNT(*) FROM personne WHERE id_site = ?";
	            stmt = cn.prepareStatement( sql );
	            stmt.setObject( 1, id_site );
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
	
	private Collecte construireCollecte( ResultSet rs,  boolean flagComplet ) throws SQLException {
		Collecte collecte = new Collecte();
		collecte.setId_collecte( rs.getObject( "id_collecte", Integer.class ) );
		collecte.setQte_collation( rs.getObject( "qte_collation", Integer.class ) );
		collecte.setDate_debut(rs.getObject("date_debut", LocalDate.class ) );
		collecte.setDate_fin(rs.getObject("date_fin", LocalDate.class ) );
		collecte.setNbre_infirmiers( rs.getObject( "nbre_infirmiers", Integer.class ) );
		collecte.setNbre_medecins( rs.getObject( "nbre_medecins", Integer.class ) );
		collecte.setNbre_secretaire(rs.getObject("nbre_secretaire", Integer.class ));
		collecte.setNbre_agents_collation(rs.getObject("nbre_agents_collation", Integer.class ));
		collecte.setHoraire_debut(rs.getObject("horaire_debut", LocalTime.class ) );
		collecte.setHoraire_fin(rs.getObject("horaire_fin", LocalTime.class ) );
		if ( flagComplet ) {
			collecte.setSite_de_collecte( daoSite_de_collecte.retrouver( rs.getObject("id_site", Integer.class) ) );
		}
		else
		{
			System.out.println("Flag incomplet");
		}

		return collecte;
	}

}

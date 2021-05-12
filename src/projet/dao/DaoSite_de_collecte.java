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
import projet.data.Site_de_collecte;


public class DaoSite_de_collecte {

	
	// Champs

	@Inject
	private DataSource		dataSource;

	
	// Actions

	public int inserer( Site_de_collecte site_de_collecte ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO site_de_collecte ( ville, nbr_lits, adresse ) VALUES( ?,?,? ) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, site_de_collecte.getVille());
			stmt.setObject( 2, site_de_collecte.getNbr_lits());
			stmt.setObject( 3, site_de_collecte.getAdresse());
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			site_de_collecte.setId_site_de_collecte( rs.getObject( 1, Integer.class) );
			return site_de_collecte.getId_site_de_collecte();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public void modifier( Site_de_collecte site_de_collecte ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE site_de_collecte SET ville = ?, nbr_lits= ?, adresse= ? WHERE id_site =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, site_de_collecte.getVille() );
			stmt.setObject( 2, site_de_collecte.getNbr_lits());
			stmt.setObject( 3, site_de_collecte.getAdresse());
			stmt.setObject( 4, site_de_collecte.getId_site_de_collecte());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimer( int idSite_de_collecte ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM site_de_collecte WHERE id_site = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idSite_de_collecte );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Site_de_collecte retrouver( int idSite_de_collecte ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM site_de_collecte WHERE id_site = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, idSite_de_collecte);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireSite_de_collecte( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Site_de_collecte> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM site_de_collecte ORDER BY id_site";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Site_de_collecte> site_de_collectes = new ArrayList<>();
			while (rs.next()) {
				site_de_collectes.add( construireSite_de_collecte( rs ) );
			}
			return site_de_collectes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	// Méthodes auxiliaires
	
	private Site_de_collecte construireSite_de_collecte( ResultSet rs ) throws SQLException {
		Site_de_collecte site_de_collecte = new Site_de_collecte();
		site_de_collecte.setId_site_de_collecte( rs.getObject( "id_site", Integer.class ) );
		site_de_collecte.setVille( rs.getObject( "ville", String.class ) );
		site_de_collecte.setNbr_lits( rs.getObject( "nbr_lits", Integer.class ) );
		site_de_collecte.setAdresse( rs.getObject( "adresse", String.class ) );
		return site_de_collecte;
	}

}

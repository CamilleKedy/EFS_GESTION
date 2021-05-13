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
import projet.data.Profession;


public class DaoProfession {

	
	// Champs

	@Inject
	private DataSource		dataSource;

	
	// Actions

	public int inserer( Profession profession ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO profession ( libelle ) VALUES( ? ) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, profession.getLibelle() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			profession.setId( rs.getObject( 1, Integer.class) );
			return profession.getId();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public void modifier( Profession profession ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE profession SET libelle = ? WHERE id_profession =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, profession.getLibelle() );
			stmt.setObject( 2, profession.getId() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimer( int idProfession ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM profession WHERE id_profession = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idProfession );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Profession retrouver( int idProfession ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM profession WHERE id_profession = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, idProfession);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireProfession( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Profession> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM profession ORDER BY libelle";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Profession> professions = new ArrayList<>();
			while (rs.next()) {
				professions.add( construireProfession( rs ) );
			}
			return professions;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Profession> listerNonGestionnaire() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM profession WHERE libelle <> 'Gestionnaire de site' ORDER BY libelle";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Profession> professions = new ArrayList<>();
			while (rs.next()) {
				professions.add( construireProfession( rs ) );
			}
			return professions;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	// Méthodes auxiliaires
	
	private Profession construireProfession( ResultSet rs ) throws SQLException {
		Profession profession = new Profession();
		profession.setId( rs.getObject( "id_profession", Integer.class ) );
		profession.setLibelle( rs.getObject( "libelle", String.class ) );
		return profession;
	}

}

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
import projet.data.Personnel;


public class DaoPersonnel {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoProfession	daoProfession;

	
	// Actions

	public int inserer( Personnel personnel )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le personnel
			sql = "INSERT INTO personnel ( id_profession, nom, prenom, adresse ) VALUES ( ?, ?, ?, ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setObject(	1, personnel.getProfession().getId() );
			stmt.setObject(	2, personnel.getNom() );
			stmt.setObject(	3, personnel.getPrenom() );
			stmt.setObject(	4, personnel.getAdresse() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			personnel.setId( rs.getObject( 1, Integer.class ) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

	
		// Retourne l'identifiant
		return personnel.getId();
	}

	
	public void modifier( Personnel personnel )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personnel
			sql = "UPDATE personnel SET id_profession = ?, nom = ?, prenom = ?, adresse = ? WHERE id_personnel =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, personnel.getProfession().getId() );
			stmt.setObject( 2, personnel.getNom() );
			stmt.setObject( 3, personnel.getPrenom() );
			stmt.setObject( 4, personnel.getAdresse() );
			stmt.setObject( 5, personnel.getId() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

	}

	
	public void supprimer( int idPersonnel )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;


		try {
			cn = dataSource.getConnection();

			// Supprime le personnel
			sql = "DELETE FROM personnel WHERE id_personnel = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idPersonnel );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Personnel retrouver( int idPersonnel )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM personnel WHERE id_personnel = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idPersonnel);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construirePersonnel(rs, true );
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	
	public List<Personnel> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM personnel ORDER BY nom, prenom";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Personnel> personnels = new ArrayList<>();
			while (rs.next()) {
				personnels.add( construirePersonnel(rs, true) );
			}
			return personnels;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	
	public List<Personnel> listerParProfession( String Profession )   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			//sql = "SELECT * FROM Personnel WHERE id_profession = ? ORDER BY nom, prenom";
			sql= "SELECT * FROM Personnel p INNER JOIN Profession c ON p.id_profession = c.id_profession WHERE c.libelle = ? ORDER BY nom, prenom";
			stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, Profession );
			rs = stmt.executeQuery();
			
			List<Personnel> personnels = new ArrayList<>();
			while (rs.next()) {
				personnels.add( construirePersonnel(rs, false) );
			}
			return personnels;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

    
    public int compterPourProfession( int idProfession ) {
    	
		Connection			cn		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM personnel WHERE id_profession = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setObject( 1, idProfession );
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
	
	private Personnel construirePersonnel( ResultSet rs, boolean flagComplet ) throws SQLException {

		Personnel personnel = new Personnel();
		personnel.setId(rs.getObject( "id_personnel", Integer.class ));
		personnel.setNom(rs.getObject( "nom", String.class ));
		personnel.setPrenom(rs.getObject( "prenom", String.class ));
		personnel.setAdresse(rs.getObject( "adresse", String.class ));

		if ( flagComplet ) {
			personnel.setProfession( daoProfession.retrouver( rs.getObject("id_profession", Integer.class) ) );
		}
		
		return personnel;
	}
	
}

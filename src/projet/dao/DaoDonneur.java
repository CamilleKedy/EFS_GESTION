package projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.jdbc.UtilJdbc;
import projet.data.Donneur;
import projet.data.Site_de_collecte;


public class DaoDonneur {

	
	// Champs

	@Inject
	private DataSource		dataSource;

	
	// Actions

	public int inserer( Donneur donneur )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le donneur
			sql = "INSERT INTO Donneur ( nom_donneur, prenom_donneur, date_naissance,sexe_donneur, telephone_donneur,email_donneur, ville_donneur, adresse_donneur, demande_carte) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
//			stmt.setObject(	1, donneur.getId() );
			stmt.setObject(	1, donneur.getNom() );
			stmt.setObject(	2, donneur.getPrenom() );
			stmt.setObject(	3, donneur.getDateNaissance() );
			stmt.setObject(	4, donneur.getSexe() );
			stmt.setObject(	5, donneur.getTelephone());
			stmt.setObject(	6, donneur.getEmail() );
			stmt.setObject(	7, donneur.getVille() );
			stmt.setObject(	8, donneur.getAdresse() );
			stmt.setObject(	9, donneur.getDemandeCarte() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			donneur.setId( rs.getObject( 1, Integer.class ) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

		
		// Retourne l'identifiant
		return donneur.getId();
	}

	
	public void modifier( Donneur donneur )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le donneur
			sql = "UPDATE donneur SET nom_donneur = ?, prenom_donneur = ?, date_naissance = ?,sexe_donneur = ?, telephone_donneur=?,email_donneur= ?, ville_donneur = ?, adresse_donneur = ?, demande_carte = ? WHERE id_donneur =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, donneur.getNom() );
			stmt.setObject( 2, donneur.getPrenom() );
			stmt.setObject( 3, donneur.getDateNaissance() );
			stmt.setObject(	4, donneur.getSexe() );
			stmt.setObject(	5, donneur.getTelephone());
			stmt.setObject(	6, donneur.getEmail() );
			stmt.setObject(	7, donneur.getVille() );
			stmt.setObject(	8, donneur.getAdresse() );
			stmt.setObject(	9, donneur.getDemandeCarte() );
			stmt.setObject( 10, donneur.getId() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

		
	}

	
	public void supprimer( int idDonneur )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;


		try {
			cn = dataSource.getConnection();

			// Supprime le donneur
			sql = " DELETE FROM dossierMedical WHERE dossierMedical.id_donneur = ?;DELETE FROM donneur WHERE id_donneur = ? ;";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idDonneur );
			stmt.setObject( 2, idDonneur );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Donneur retrouver( int idDonneur )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM donneur WHERE id_donneur = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idDonneur);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireDonneur(rs, true );
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	public List<Donneur> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM donneur ORDER BY nom_donneur, prenom_donneur";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Donneur> donneurs = new ArrayList<>();
			while (rs.next()) {
				donneurs.add( construireDonneur(rs, false) );
			}
			return donneurs;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Donneur> listerPourSite(Site_de_collecte site)   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			//sql ="select distinct d.* from ((donneur d inner join rdv r on d.id_donneur = r.id_donneur) "
			//		+ "inner join collecte c on r.id_collecte = c.id_collecte) where  c.id_site = ?";
			sql ="select distinct d.* from ((donneur d left join rdv r on d.id_donneur = r.id_donneur) "
					+ "inner join collecte c on r.id_collecte = c.id_collecte) where  c.id_site = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, site.getId_site_de_collecte());
			rs = stmt.executeQuery();
			
			List<Donneur> donneurs = new ArrayList<>();
			while (rs.next()) {
				donneurs.add( construireDonneur(rs, false) );
			}
			return donneurs;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	// Méthodes auxiliaires
	
	private Donneur construireDonneur( ResultSet rs, boolean flagComplet ) throws SQLException {

		Donneur donneur = new Donneur();
		donneur.setId(rs.getObject( "id_donneur", Integer.class ));
		donneur.setNom(rs.getObject( "nom_donneur", String.class ));
		donneur.setPrenom(rs.getObject( "prenom_donneur", String.class ));
		donneur.setSexe(rs.getObject( "sexe_donneur", String.class ));
		donneur.setTelephone(rs.getObject( "telephone_donneur", String.class ));
		donneur.setEmail(rs.getObject( "email_donneur", String.class ));
		donneur.setVille(rs.getObject( "ville_donneur", String.class ));
		donneur.setAdresse(rs.getObject( "adresse_donneur", String.class ));

		if ( flagComplet ) {
			donneur.setDateNaissance(rs.getObject( "date_naissance", LocalDate.class ));
			donneur.setDemandeCarte(rs.getObject( "demande_carte", String.class ));
		}
		
		return donneur;
	}
	
}

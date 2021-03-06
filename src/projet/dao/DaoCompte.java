package projet.dao;

import java.sql.CallableStatement;
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
import projet.data.Collecte;
import projet.data.Compte;
import projet.data.Site_de_collecte;


public class DaoCompte {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoRole			daoRole;
	@Inject
	private DaoPersonnel	daoPersonnel;
	@Inject
	private DaoCollecte 	daoCollecte;
	@Inject
	private DaoSite_de_collecte 	daoSite;

	
	// Actions

	public int inserer( Compte compte )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le compte
			sql = "INSERT INTO connexion ( login, motDePasse, id_personnel ) VALUES ( ?, ?, ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS ); 
			stmt.setObject( 1, compte.getPseudo() );
			stmt.setObject( 2, compte.getMotDePasse() );
			stmt.setObject( 3, compte.getPersonnel().getId() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			compte.setId( rs.getObject( 1, Integer.class) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}

		// Insère les rôles
		daoRole.insererPourCompte( compte );
		
		// Retourne l'identifiant
		return compte.getId();
	}
	

	public void modifier( Compte compte )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le compte
			sql = "UPDATE connexion SET login = ?, motDePasse = ?, id_personnel = ? WHERE id_connexion =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, compte.getPseudo() );
			stmt.setObject( 2, compte.getMotDePasse() );
			stmt.setObject( 3, compte.getPersonnel().getId() );
			stmt.setObject( 4, compte.getId() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

		// Modifie les rôles
		daoRole.supprimerPourCompte( compte.getId() );
		daoRole.insererPourCompte( compte );

	}
	

	public void supprimer( int idCompte )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		// Supprime les rôles
		daoRole.supprimerPourCompte( idCompte );

		try {
			cn = dataSource.getConnection();

			// Supprime le compte
			sql = "DELETE FROM connexion WHERE id_connexion = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idCompte );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	

	public Compte retrouver( int idCompte )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM connexion WHERE id_connexion = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setObject( 1, idCompte );
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireCompte( rs , true);
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	

	public List<Compte> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM connexion ORDER BY login";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Compte> comptes = new ArrayList<>();
			while ( rs.next() ) {
				comptes.add( construireCompte(rs, true) );
			}
			return comptes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public Compte validerAuthentification( String pseudo, String motDePasse )  {
		
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM connexion WHERE login = ? AND motDePasse = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, pseudo );
			stmt.setObject( 2, motDePasse );
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireCompte( rs, true );			
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public boolean verifierUnicitePseudo( String pseudo, Integer idCompte )   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		if ( idCompte == null ) idCompte = 0;
		
		try {
			cn = dataSource.getConnection();

			sql = "SELECT COUNT(*) = 0 AS unicite"
					+ " FROM connexion WHERE login = ? AND id_connexion <> ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(	1, pseudo );
			stmt.setObject(	2, idCompte );
			rs = stmt.executeQuery();
			
			rs.next();
			return rs.getBoolean( "unicite" );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	// Méthodes auxiliaires
	
	private Compte construireCompte( ResultSet rs, boolean flagComplet ) throws SQLException {
		Compte compte = new Compte();
		compte.setId( rs.getObject( "id_connexion", Integer.class ) );
		compte.setPseudo( rs.getObject( "login", String.class ) );
		compte.setMotDePasse( rs.getObject( "motDePasse", String.class ) );
		compte.getRoles().setAll( daoRole.listerPourCompte( compte ) );
		
		if ( flagComplet ) {
			compte.setPersonnel( daoPersonnel.retrouver( rs.getObject("id_personnel", Integer.class) ) );
		}
		return compte;
	}
	
	private Collecte retrouverInfoSurCollecte(int idCompte) {
		Compte compte = new Compte();
		compte = retrouver(idCompte);
		
		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "{CALL personnel_retrouver_collecte(?)}";
			stmt = cn.prepareCall( sql );
			stmt.setObject(	1, compte.getPersonnel().getId() );
			rs = stmt.executeQuery();

			return daoCollecte.retrouver(rs.getObject("id_collecte", Integer.class));

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
	}
	
	public Site_de_collecte retrouverInfoSurSiteDeCollecte(int idCompte) {
		
		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			
			cn = dataSource.getConnection();
			Collecte collecte =retrouverInfoSurCollecte(idCompte);
			sql = "{CALL personnel_retrouver_site_collecte(?)}";
			stmt = cn.prepareCall( sql );
			stmt.setObject(	1, collecte.getId_collecte());
			rs = stmt.executeQuery();

			return daoSite.retrouver(rs.getObject("id_site", Integer.class)) ;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
	}
}

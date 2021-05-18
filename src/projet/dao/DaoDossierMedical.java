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
import projet.data.DossierMedical;


public class DaoDossierMedical {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoDonneur		daoDonneur;

	
	// Actions

	public int inserer( DossierMedical dossierMedical )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le dossierMedical
			sql = "INSERT INTO DossierMedical ( groupe_sanguin, rhesus, poids, inaptitude_temporaire, id_donneur) VALUES ( ?, ?, ?, ?, ?)";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );

			stmt.setObject(	1, dossierMedical.getId() );
			stmt.setObject(	2, dossierMedical.getGroupeSanguin() );
			stmt.setObject(	3, dossierMedical.getRhesus() );
			stmt.setObject(	4, dossierMedical.getPoids() );
			stmt.setObject(	5, dossierMedical.getInaptitude() );
			
			if(dossierMedical.getDonneur().getId() == null)
				stmt.setObject(	5, null );
			else
				stmt.setObject(5, dossierMedical.getDonneur().getId());
			
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			dossierMedical.setId( rs.getObject( 1, Integer.class ) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

		
		// Retourne l'identifiant
		return dossierMedical.getId();
	}

	
	public void modifier( DossierMedical dossierMedical )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le dossierMedical
			sql = "UPDATE dossierMedical SET  groupe_sanguin = ?, rhesus = ?, poids = ?, inaptitude_temporaire = ?, id_donneur = ? WHERE id_dossier =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, dossierMedical.getGroupeSanguin() );
			stmt.setObject( 2, dossierMedical.getRhesus() );
			stmt.setObject( 3, dossierMedical.getPoids() );
			stmt.setObject( 4, dossierMedical.getInaptitude() );
			stmt.setObject( 5, dossierMedical.getDonneur().getId() );
			stmt.setObject( 6, dossierMedical.getId() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

		
	}

	
	public void supprimer( int idDossierMedical )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;


		try {
			cn = dataSource.getConnection();

			// Supprime le dossierMedical
			sql = "DELETE FROM dossierMedical WHERE id_dossier = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idDossierMedical );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public DossierMedical retrouver( int idDossierMedical )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM dossierMedical WHERE id_dossier = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idDossierMedical);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireDossierMedical(rs, true );
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	
	public List<DossierMedical> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM dossierMedical ORDER BY id_donneur";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<DossierMedical> dossierMedicals = new ArrayList<>();
			while (rs.next()) {
				dossierMedicals.add( construireDossierMedical(rs, false) );
			}
			return dossierMedicals;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

/*
	public List<DossierMedical> listerPourDossierMedical( int idDossier )   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM dossierMedical p INNER JOIN concerner c ON p.id_dossier = c.id_dossier WHERE c.idmemo = ? ORDER BY nom, prenom";
			stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idDossier );
			rs = stmt.executeQuery();
			
			List<DossierMedical> dossierMedicals = new ArrayList<>();
			while (rs.next()) {
				dossierMedicals.add( construireDossierMedical(rs, false) );
			}
			return dossierMedicals;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
*/
    
    public int compterPourDossierMedical( int idDossier ) {
    	
		Connection			cn		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM dossierMedical WHERE id_dossier = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setObject( 1, idDossier );
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
	
	private DossierMedical construireDossierMedical( ResultSet rs, boolean flagComplet ) throws SQLException {

		DossierMedical dossierMedical = new DossierMedical();
		dossierMedical.setId(rs.getObject( "id_dossier", Integer.class ));
		dossierMedical.setInaptitude(rs.getObject( "inaptitude_temporaire", String.class ));

		dossierMedical.setPoids(rs.getObject( "poids", Float.class ));
		dossierMedical.setGroupeSanguin(rs.getObject( "groupe_sanguin", String.class ));
		dossierMedical.setRhesus(rs.getObject( "rhesus", String.class ));
		
		if ( flagComplet ) {
			dossierMedical.setDonneur( daoDonneur.retrouver( rs.getObject("id_donneur", Integer.class) ) );

			//dossierMedical.getGroupeSanguin().addAll( daoDonneur.listerGroupeSanguinPourDossierMedical(dossierMedical));
			//dossierMedical.getRhesus().addAll( daoDonneur.listerRhesusPourDossierMedical(dossierMedical));

			
//			dossierMedical.getGroupeSanguin().addAll( daoDonneur.listerGroupeSanguinPourDossierMedical(dossierMedical));
//			dossierMedical.getRhesus().addAll( daoDonneur.listerRhesusPourDossierMedical(dossierMedical));}
		}
		else
		{
			System.out.println("Flag incomplet");
		}
		
			
		return dossierMedical;
	}
	
}
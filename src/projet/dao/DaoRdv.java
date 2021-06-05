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
import projet.data.Rdv;

public class DaoRdv {
	
	@Inject
	private DataSource dataSource;
	@Inject
	private DaoDonneur daoDonneur;
	@Inject
	private DaoCollecte daoCollecte;
	

	public int inserer( Rdv rdv ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();/*,id_collecte, id_donneur*//*, ?, ?*/
			sql = "INSERT INTO RDV ( heure_rdv, prise_de_sang, date_rdv, qte_sang_donnee ,id_collecte , id_donneur) VALUES( ?, ?, ?, ?,?,?) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, rdv.getHeure_rdv());
			stmt.setObject( 2, rdv.getPrise_de_sang());
			stmt.setObject( 3, rdv.getDate_rdv());
			stmt.setObject( 4, rdv.getQte_sang());
			stmt.setObject( 5, rdv.getCollecte().getId_collecte());
			stmt.setObject( 6, rdv.getDonneur().getId());
	
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			rdv.setId( rs.getObject( 1, Integer.class) );
		
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
		return rdv.getId();
	}

	public int modifier( Rdv rdv ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE RDV SET  heure_rdv =?, prise_de_sang=?, date_rdv=?, qte_sang_donnee=?,id_collecte =?, id_donneur =? WHERE id_rdv = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, rdv.getHeure_rdv());
			stmt.setObject( 2, rdv.getPrise_de_sang());
			stmt.setObject( 3, rdv.getDate_rdv());
			stmt.setObject( 4, rdv.getQte_sang());
			stmt.setObject( 5, rdv.getCollecte().getId_collecte());
			stmt.setObject( 6, rdv.getDonneur().getId());
			stmt.setObject( 7, rdv.getId());
	
			stmt.executeUpdate();

		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
		return rdv.getId();
	}
	
	public void supprimer( int id_rdv) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM RDV WHERE id_rdv = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, id_rdv);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	public Rdv retrouver( int id_rdv ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM RDV WHERE id_rdv = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, id_rdv);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireRDV( rs, true );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Rdv> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM RDV ORDER BY id_rdv";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Rdv> rdv = new ArrayList<>();
			while (rs.next()) {
				rdv.add( construireRDV( rs, true ) );
			}
			return rdv;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	private Rdv construireRDV( ResultSet rs,  boolean flagComplet ) throws SQLException {
		Rdv rdv = new Rdv();
		rdv.setId( rs.getObject( "id_rdv", Integer.class ) );
		rdv.setHeure_rdv( rs.getObject( "heure_rdv", LocalTime.class ) );
		rdv.setPrise_de_sang(rs.getObject("prise_de_sang", String.class ) );
		rdv.setDate_rdv(rs.getObject("date_rdv", LocalDate.class ) );
		rdv.setQte_sang( rs.getObject( "qte_sang_donnee", Integer.class ) );
		if ( flagComplet ) {
			rdv.setCollecte( daoCollecte.retrouver( rs.getObject("id_collecte", Integer.class) ) );
			rdv.setDonneur( daoDonneur.retrouver( rs.getObject("id_donneur", Integer.class) ) );
		}
		else
		{
			System.out.println("Flag incomplet");
		}

		return rdv;
	}


}

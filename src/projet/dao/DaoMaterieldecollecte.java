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
import projet.data.Collecte;
import projet.data.Materieldecollecte;

public class DaoMaterieldecollecte {

	// Champs

	@Inject
	private DataSource dataSource;
	@Inject
	private DaoMateriel daoMateriel;
	@Inject
	private DaoCollecte daoCollecte;

	// Actions

	public int inserer(Materieldecollecte Materieldecollecte) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// Insère le Materieldecollecte
			sql = "INSERT INTO materieldecollecte (id_collecte, id_materiel, quantité) VALUES ( ?, ?, ? )";
			stmt = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setObject(1, Materieldecollecte.getCollecte().getId_collecte());
			stmt.setObject(2, Materieldecollecte.getMateriel().getId_materiel());
			stmt.setObject(3, Materieldecollecte.getQuantite());
			stmt.executeUpdate();
			/*
			 * // Récupère l'identifiant généré par le SGBD rs = stmt.getGeneratedKeys();
			 * rs.next(); Materieldecollecte.setId( rs.getObject( 1, Integer.class ) );
			 */
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, cn);
		}

		// Retourne l'identifiant
		return Materieldecollecte.getMateriel().getId_materiel();
	}

	public void insererPourCollecte(Collecte collecte) {
		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// Insère le Materieldecollecte
			sql = "INSERT INTO materieldecollecte (id_collecte, id_materiel, quantite) VALUES ( ?, ?, ? )";
			stmt = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			for (Materieldecollecte m : collecte.getMateriel()) {

				stmt.setObject(1, collecte.getId_collecte());
				stmt.setObject(2, m.getMateriel().getId_materiel());
				stmt.setObject(3, m.getQuantite());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, cn);
		}
	}

	public void modifierPourCollecte(Collecte collecte) {
		supprimerPourCollecte(collecte.getId_collecte());
		insererPourCollecte(collecte);
	}

	public void supprimerPourCollecte(int id_collecte) {
		Connection cn = null;
		PreparedStatement stmt = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le Materieldecollecte
			sql = "DELETE FROM materieldecollecte WHERE id_collecte = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, id_collecte);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, cn);
		}
	}

	public void modifier(Materieldecollecte Materieldecollecte) {

		Connection cn = null;
		PreparedStatement stmt = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le Materieldecollecte
			sql = "UPDATE materieldecollecte SET  quantite = ? WHERE id_collecte = ? AND id_materiel= ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, Materieldecollecte.getQuantite());
			stmt.setObject(2, Materieldecollecte.getCollecte().getId_collecte());
			stmt.setObject(3, Materieldecollecte.getMateriel().getId_materiel());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, cn);
		}

	}

	public void supprimer(Materieldecollecte Materieldecollecte) {

		Connection cn = null;
		PreparedStatement stmt = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le Materieldecollecte
			sql = "DELETE FROM materieldecollecte WHERE id_collecte = ? AND id_materiel= ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, Materieldecollecte.getCollecte().getId_collecte());
			stmt.setObject(2, Materieldecollecte.getMateriel().getId_materiel());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(stmt, cn);
		}
	}

	public Materieldecollecte retrouver(Materieldecollecte Materieldecollecte) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM materieldecollecte WHERE id_collecte = ? AND id_materiel= ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, Materieldecollecte.getCollecte().getId_collecte());
			stmt.setObject(2, Materieldecollecte.getMateriel().getId_materiel());
			rs = stmt.executeQuery();

			if (rs.next()) {
				return construireMaterieldecollecte(rs, true, null);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public List<Materieldecollecte> listerTout() {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Materieldecollecte ORDER BY quantite";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();

			List<Materieldecollecte> Materieldecollectes = new ArrayList<>();
			while (rs.next()) {
				Materieldecollectes.add(construireMaterieldecollecte(rs, true, null));
			}
			return Materieldecollectes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public List<Materieldecollecte> listerParMaterieldecollecte(String Materieldecollecte) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			// sql = "SELECT * FROM Materieldecollecte WHERE id_profession = ? ORDER BY nom,
			// prenom";
			sql = "SELECT * FROM Materieldecollecte p INNER JOIN Materieldecollecte c ON p.id_profession = c.id_profession WHERE c.libelle = ? ORDER BY nom, prenom";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, Materieldecollecte);
			rs = stmt.executeQuery();

			List<Materieldecollecte> Materieldecollectes = new ArrayList<>();
			while (rs.next()) {
				Materieldecollectes.add(construireMaterieldecollecte(rs, false, null));
			}
			return Materieldecollectes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public List<Materieldecollecte> listerParCollecte(Collecte collecte) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Materieldecollecte WHERE id_collecte = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1,collecte.getId_collecte());
			rs = stmt.executeQuery();

			List<Materieldecollecte> Materieldecollectes = new ArrayList<>();
			while (rs.next()) {
				Materieldecollectes.add(construireMaterieldecollecte(rs, true, collecte));
			}
			return Materieldecollectes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	public int compterPourMaterieldecollecte(Materieldecollecte Materieldecollecte) {

		Connection cn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			cn = dataSource.getConnection();
			String sql = "SELECT COUNT(*) FROM Materieldecollecte WHERE id_collecte = ? AND id_materiel= ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, Materieldecollecte.getCollecte().getId_collecte());
			stmt.setObject(2, Materieldecollecte.getMateriel().getId_materiel());
			rs = stmt.executeQuery();

			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(rs, stmt, cn);
		}
	}

	// Méthodes auxiliaires

	private Materieldecollecte construireMaterieldecollecte(ResultSet rs, boolean flagComplet, Collecte collecte) throws SQLException {

		Materieldecollecte Materieldecollecte = new Materieldecollecte();
		Materieldecollecte.setQuantite(rs.getObject("quantite", Integer.class));
		if(collecte == null) {
			Materieldecollecte.setCollecte(daoCollecte.retrouver(rs.getObject("id_collecte", Integer.class)));
		}
		else {
			Materieldecollecte.setCollecte(collecte);
		}
		
		if (flagComplet) {
			Materieldecollecte.setMateriel(daoMateriel.retrouver(rs.getObject("id_materiel", Integer.class)));
		}

		return Materieldecollecte;
	}

}

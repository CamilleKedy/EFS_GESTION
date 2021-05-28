package projet.commun;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public final class Roles {
	
	// Champs statiques
	
	public static final String GESTIONAIRE	= "GESTIONNAIRE";
	public static final String SECRETAIRE	= "SECRETAIRE";
	
	
	private static final List<String>	roles = Collections.unmodifiableList( Arrays.asList( 
			GESTIONAIRE,			
			SECRETAIRE
	) );

	private static final String[]	 	libelles = new String[] {
			"Gestionnaire",
			"Secretaire",
	};
	
	
	// Constructeur privé qui empêche l'instanciation de la classe
	private Roles() {
	}
	
	
	// Actions

	public static List<String> getRoles() {
		return roles;
	}
	
	public static String getLibelle( String role ) {
		int index = roles.indexOf( role );
		if ( index == -1 ) {
			throw new IllegalArgumentException();
		} 
		return libelles[index];
	}

}

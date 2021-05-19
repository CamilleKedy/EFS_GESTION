package projet.commun;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import projet.data.Categorie;
import projet.data.Collecte;
import projet.data.Compte;
import projet.data.Donneur;
import projet.data.DossierMedical;
import projet.data.Memo;
import projet.data.Personne;
import projet.data.Personnel;
import projet.data.Profession;
import projet.data.Service;
import projet.data.Site_de_collecte;


@Mapper
public interface IMapper {  
	
	static IMapper INSTANCE = Mappers.getMapper( IMapper.class );
	
	Compte update( @MappingTarget Compte target, Compte source  );
	
	Donneur update ( @MappingTarget Donneur target, Donneur source);
	
	Categorie update( @MappingTarget Categorie target, Categorie source );

	@Mapping( target="categorie", expression="java( source.getCategorie() )" )
	Personne update( @MappingTarget Personne target, Personne source );

	@Mapping( target="categorie", expression="java( source.getCategorie() )" )
	Memo update( @MappingTarget Memo target, Memo source );

	Service update( @MappingTarget Service target, Service source );

	DossierMedical update(@MappingTarget DossierMedical target, DossierMedical source);
	
	//@Mapping( target="libelle", expression="java( source.getLibelle() )" )
	Profession update( @MappingTarget Profession target, Profession source );
	
	Personnel update( @MappingTarget Personnel target, Personnel source );
	
	@Mapping( target="site_de_collecte", expression="java( source.getSite_de_collecte() )" )
	
	Collecte update( @MappingTarget Collecte target, Collecte source  );
	
	Site_de_collecte update( @MappingTarget Site_de_collecte target, Site_de_collecte source  );
	
}

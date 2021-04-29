package test.git;

public class Test_NNB {
	
	
	public void bonjour() {
		System.out.println( "Le max ultime" );
	}
	
	
	private String[] adresses = {
			"14 Rue Mozart, Paris",
			"43 Rue Sainte Anne, Limoges", 
			"16 Rue Jean Antoine de Baïf, Limoges",
	};

	
	public String getAdresse( int i ) {

		if ( 0 <= i && i < adresses.length ) {
			return adresses[i];
		} else {
			return null;
		}
	}
	
}

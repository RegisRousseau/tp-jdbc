package org.rousseau.jdbc.exo1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommuneCSVImporter implements CommuneImporter {
	
	List<String> communesInCSVLine = null;
	
	

	public CommuneCSVImporter(String path) {
		
		importCommune(path);
	}



	// ********************
	// Questions 7 :
	// ********************
	@Override
	public void importCommune(String path) {
		
		CommuneSQLService communeSQLSerive = new CommuneSQLService();
		CommuneSQLService.dropDBCommune();
		CommuneSQLService.createDBCommune();
	
		Path pathCSV = Path.of(path);
		try (Stream<String> lines = Files.lines(pathCSV);) {
			
			communesInCSVLine = lines.skip(1).collect(Collectors.toList());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Nombre de commune: "+communesInCSVLine.stream().count());
		
		for (String line : communesInCSVLine) {
			// ********************
			// Questions 8 :
			// ********************
			communeSQLSerive.writeCommune(importCommune.apply(line));
			//communeSQLSerive.writeCommuneWithBatch(importCommune.apply(line));
		}
		
		communeSQLSerive.isLastCommune();//Permet d'envoyer les dernières communes contenues dans le batch
	}
	
	// ********************
	// Question 5 :
	// ********************
	static Function<String, Commune> importCommune = ligne -> {
		String[] elementLigne = ligne.split(";");
		return new Commune(elementLigne[0], // codeINSEE
				elementLigne[1], // nomCommune
				elementLigne[2], // codePostal
				elementLigne[4] // libelleAcheminement
		);
	};
	
	public static void testQuestion5() {
		String ligneTest = "01001;L ABERGEMENT CLEMENCIAT;01400;;L ABERGEMENT CLEMENCIAT;46.1534255214,4.92611354223";
		// Code_commune_INSEE;Nom_commune;Code_postal;Ligne_5;Libellé_d_acheminement;coordonnees_gps
		Commune communeTest = importCommune.apply(ligneTest);
		System.out.println(communeTest);
	}

}

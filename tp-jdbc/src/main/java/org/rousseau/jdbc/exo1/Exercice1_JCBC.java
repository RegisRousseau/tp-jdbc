package org.rousseau.jdbc.exo1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;


public class Exercice1_JCBC  {

	//********************
	// Mysql information login :
	// ********************

	
	static final String fileName ="src/main/resources/laposte_hexasmal.csv";

	public static void main(String[] args) {
		
		//CommuneCSVImporter.testQuestion5(); //Test de la fonction ligneCSV -> Commune : OK!
		
		
		
		testQuestion6();
		
		//****************
		//Test de la question 7 :
//		CommuneCSVImporter communeCSVImporter = new CommuneCSVImporter(fileName); // OK!
		
		
		testQuestion9();
		
		testQuestion10();

		
		// dropDBCommune(); //Suppression de la table commune : OK!
		
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

	static void testQuestion6() {
		CommuneSQLService communeSQLSerive = new CommuneSQLService();
		//communeSQLSerive.writeCommune(communeTest);	//Ecriture d'une commune en DB : OK!
		Commune communeTest2 = communeSQLSerive.getCommuneById("02722"); //Trouver commune par ID (codeINSEE) : OK!
		//Valeur de commune attendu : 'SOISSONS'
		System.out.println("Get commune by ID : "+communeTest2); //Valeur correcte
	}
	
	static void testQuestion9() {
		
		CommuneSQLService communeSQLSerive = new CommuneSQLService();
		Commune communeTest = communeSQLSerive.getCommuneByName("SOISSONS"); //'SOISSONS'
		System.out.println("Get commune by name : "+communeTest);//Valeur correcte
	}
	
	static void testQuestion10() {
		String CPCommuneTest = "12";
		CommuneSQLService communeSQLSerive = new CommuneSQLService();
		int nbCommune = communeSQLSerive.countCommuneByCP(CPCommuneTest);
		System.out.println("Number of commune in "+CPCommuneTest+" : "+nbCommune);
	}
	

}

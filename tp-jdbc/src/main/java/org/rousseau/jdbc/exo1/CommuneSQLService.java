package org.rousseau.jdbc.exo1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommuneSQLService implements CommuneDBService{
	
	//********************
	// Mysql information login :
	// ********************
	static final String URL = "jdbc:mysql://localhost:3306/tp_jdbc";
	static final String USER = "jdbc_user";
	static final String PWD = "jdbc";
	//********************
	// Variable pour gestion du Batch d'importation des communes :
	// ********************
	private int compteuCommuneDansrBatch = 0;
	private static Boolean lastCommune = false;
	// ********************
	// Questions 6 :
	// ********************
	@Override
	public void writeCommune(Commune commune) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PWD);
				PreparedStatement prStatement = conn.prepareStatement("INSERT INTO communes(codeINSEE, nomCommune, codePostal,libelleAcheminement) VALUES (?,?,?,?);");) {
			
			
			prStatement.setString(1, commune.getCodeINSEE());
			prStatement.setString(2, commune.getNomCommune());
			prStatement.setString(3, commune.getCodePostal());
			prStatement.setString(4, commune.getLibelleAcheminement());

			prStatement.executeUpdate();
			//System.out.println("SUCCES : INSERTION COMMUNE !");
		} catch (SQLException e) {
			System.out.println("ERREUR : INSERTION COMMUNE !");
			e.printStackTrace();
		}

	}

	@Override
	public void writeCommuneWithBatch(Commune commune) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PWD);
				PreparedStatement prStatement = conn.prepareStatement("INSERT INTO communes(codeINSEE, nomCommune, codePostal,libelleAcheminement) VALUES (?,?,?,?);");) {
			
			
			prStatement.setString(1, commune.getCodeINSEE());
			prStatement.setString(2, commune.getNomCommune());
			prStatement.setString(3, commune.getCodePostal());
			prStatement.setString(4, commune.getLibelleAcheminement());

			prStatement.addBatch();
			compteuCommuneDansrBatch++;
			if(compteuCommuneDansrBatch>20 || lastCommune ) {
				prStatement.addBatch();
				compteuCommuneDansrBatch=0;
				lastCommune=false; 
			}
			//System.out.println("SUCCES : INSERTION COMMUNE !");
		} catch (SQLException e) {
			System.out.println("ERREUR : INSERTION COMMUNE !");
			e.printStackTrace();
		}

	}
	
	@Override
	public Commune getCommuneById(String id) {

		try (Connection conn = DriverManager.getConnection(URL, USER, PWD);
				PreparedStatement prStatement = conn.prepareStatement("SELECT * FROM communes WHERE codeINSEE= ?");) {
			
			prStatement.setString(1, id);
			ResultSet resultSet = prStatement.executeQuery();
			resultSet.next();
			Commune commune = new Commune(
					resultSet.getString("CodeINSEE"),
					resultSet.getString("nomCommune"),
					resultSet.getString("codePostal"),
					resultSet.getString("libelleAcheminement")
					);
			return commune;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}


	}
	
	

	// ********************
	// Questions 9 :
	// ********************

	@Override
	public Commune getCommuneByName(String name) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PWD);) {
			PreparedStatement prStatement = conn.prepareStatement("SELECT * FROM communes WHERE nomCommune= ?");
			prStatement.setString(1, name);
			ResultSet resultSet = prStatement.executeQuery();
			resultSet.next();
			Commune commune = new Commune(
					resultSet.getString("CodeINSEE"),
					resultSet.getString("nomCommune"),
					resultSet.getString("codePostal"),
					resultSet.getString("libelleAcheminement")
					);
			return commune;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	// ********************
	// Questions 10 :
	// ********************

	@Override
	public int countCommuneByCP(String codePostal) {
		if(codePostal.length()==1)
			codePostal="0"+codePostal;
		if(codePostal.length()>2)
			return 0;
		try (Connection conn = DriverManager.getConnection(URL, USER, PWD);) {
			PreparedStatement prStatement = conn.prepareStatement("SELECT * FROM communes WHERE codePostal LIKE ?");
			prStatement.setString(1,""+ codePostal+"%");
			ResultSet resultSet = prStatement.executeQuery();
			
			if(resultSet.last())
				return resultSet.getRow();//Retourne la position du résultat, donc le nombre de résultats
			
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	// ********************
	// Méthodes supplémentaires non demandées :
	// ********************
	
	
	public static void createDBCommune() {

		try (Connection conn = DriverManager.getConnection(URL, USER, PWD);) {

			Statement statement = conn.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS communes (" + "codeINSEE varchar(6) PRIMARY KEY,"
					+ "nomCommune varchar(40) NOT NULL DEFAULT ' '," + "codePostal varchar(6) NOT NULL DEFAULT '0',"
					+ "libelleAcheminement varchar(40)" + ") ");
			System.out.println("SUCCES : CREATION TABLE !");
		} catch (SQLException e) {
			System.out.println("ERREUR : CREATION TABLE !");
			e.printStackTrace();
		}

	}

	public static void dropDBCommune() {

		try (Connection conn = DriverManager.getConnection(URL, USER, PWD);) {

			Statement statement = conn.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS communes");
			System.out.println("SUCCES : DROP TABLE !");
		} catch (SQLException e) {
			System.out.println("ERREUR : DROP TABLE !");
			e.printStackTrace();
		}

	}
	
	public static void isLastCommune() {
		lastCommune=true;
	}


}

package org.rousseau.jdbc.exo1;

public interface CommuneDBService {
	void writeCommune(Commune commune);
	Commune getCommuneById(String id);
	Commune getCommuneByName(String name);
	void writeCommuneWithBatch(Commune commune);
	int countCommuneByCP(String codePostal);
}


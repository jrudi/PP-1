package parser;

import parser.exceptions.DataParseException;
import model.DataItem;

public abstract class Parser {
	protected String filename;
	
	public Parser(String filename){
		this.filename = filename;
	}
	
	/* Daten in einem Feld zurueckgeben */
	public abstract DataItem[] getData();
		
	
	/* Datei mit Name this.filename zeilenweise einlesen und verarbeiten */
	public abstract void parseFile();
	
	/* Auswertung der Datenzeilen und Speicherung in einer Liste */
	protected abstract void parseLine(String line) throws DataParseException;
}

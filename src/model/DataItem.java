package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataItem implements Comparable<DataItem>{
	private double level;
	private Calendar date;
	
	public DataItem(Calendar cal, double value){
		this.date = cal;
		this.level = value;
	}

	public double getLevel() {
		return this.level;
	}

	public Calendar getDate() {
		return date;
	}

	public int compareTo(DataItem item) {
		return this.date.compareTo(item.date);
	}
	
	/* Berechnet den Unterschied zwischen dem Zeitpunkt der aktuellen
	 * Instanz und dem Zeitpunkt des als Parameter übergebenen Zeitpunkts
	 * in Anuahl von Tagen.
	 */
	public int dayDiff(DataItem d2){
		int millisPerDay = 1000 * 60 * 60 * 24;
		long diff = (this.date.getTimeInMillis() - d2.date.getTimeInMillis())/  millisPerDay;
		return (int) diff;
	}
	
	public String toString(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(this.date.getTime()) + ": " + this.level;
	}
}

package general;

public class Settings {
	public double minX, maxX; // minimaler/maximaler Wert auf der x-Achse (unskaliert)
	public double minY, maxY; // minimaler/maximaler Wert auf der y-Achse (unskaliert)
	// skalierte Werte für Minimum undMaximum auf der x- und y- Achse
	public double minScX, minScY,maxScX,maxScY;
	// Skalierungsfaktor in x- und in y-Richtung
	public double scaleX = 1.0, scaleY = 1.0;
	
	public Settings(){
		this.maxX = 400;
		this.maxY = 400;
	}
	
	public Settings(int width, int height){
		this.maxX = width;
		this.maxY = height;
	}
	
	public Settings(double minX, double maxX, double minY, double maxY, double scaleX, double scaleY){
		this.minX = minX; 
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	public String toString(){
		return "minX = " + minX + "\n" + 
			   "maxX = " + maxX + "\n" + 
			   "minY = " + minY + "\n" +
			   "maxY = " + maxY + "\n" +
			   "min scaled X = " + minScX + "\n" + 
			   "max scaled X = " + maxScX + "\n" + 
			   "min scaled Y = " + minScY + "\n" +
			   "max scaled Y = " + maxScY + "\n" +
			   "scaleX = " + scaleX + "\n" +
			   "scaleY = " + scaleY + "\n" ;
	}

}

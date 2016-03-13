package gui;

import general.Settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

import javax.swing.JPanel;

import model.DataItem;

public class CurvesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public final static int NO_CURVE = -1;
	public final static int ORIG_CURVE = 0;
	public final static int L_CURVE = 1;
	private int currentCurve = NO_CURVE; // Anzeige-Status: initial keine Kurve
											// anzeigen
	private double width = 1000, height = 500; // Initialwerte fuer die Groesse
												// der Anzeige
	private Settings s1 = null, s2 = null; // Settings fuer die zwei versch.
											// Kurven
	private Shape path_1 = null, path_2 = null; // skalierte Pfade (zum Zeichnen
												// in paint-Methode)
	private GeneralPath p1 = null, p2 = null; // Pfade mit Original-Daten
												// (unskaliert)
	private Settings currentSetting = new Settings(0.0, width, 0.0, height, 1.0, 1.0); // initiale
																						// Settings

	public CurvesPanel() {
		this.setPreferredSize(new Dimension((int) this.width, (int) this.height));
		this.s1 = new Settings(0, this.width, 0, this.height, 1.0f, 1.0f); // Voreinstellungen
		this.s2 = new Settings(0, this.width, 0, this.height, 1.0f, 1.0f); // Voreinstellungen
	}

	public void createCurves(DataItem[] data) {
		this.mapDataToCurve(data); // 1. Kurve (Levels) berechnen
		this.calcRelVerlusteCurve(data); // 1. Kurve (rel. Verluste) berechnen
		this.updateSettings(); // Settings neu berechnen
		this.repaint();
	}

	public void setState(int state) {
		this.currentCurve = state;
	}

	public void paint(Graphics g) {
		this.setBackground(new Color(100,100,100));
		switch (this.currentCurve) {
		case ORIG_CURVE:
			currentSetting = s1;
			break;
		case L_CURVE:
			currentSetting = s2;
			break;
		default:
			currentSetting = new Settings(0, width, 0, height, 1.0f, 1.0f);
			break;
		}
		
		Graphics2D g2 = (Graphics2D) g;	
		
		//Draw the Background
		g2.setColor(Color.CYAN.brighter().brighter());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		AffineTransform at = new AffineTransform();
		
		switch(this.currentCurve){
		case ORIG_CURVE:

			at.setToScale(currentSetting.scaleX*2,currentSetting.scaleY);
			path_1 = at.createTransformedShape(p1);
			
			g2.setColor(Color.GRAY);
			g2.draw(path_1);
			break;
		case L_CURVE:
			
			g2.setColor(Color.BLACK);
			g2.drawLine(0, this.getHeight()/2*(int)currentSetting.scaleY, this.getWidth(), this.getHeight()/2*(int)currentSetting.scaleY);
						
			at.setToScale(currentSetting.scaleX,currentSetting.scaleY);
			path_2 = at.createTransformedShape(p2);

			g2.setColor(Color.GRAY);
			g2.draw(path_2);
			
			break;
		default:
			
			break;
		}
			
		}
		
	

	public void mapDataToCurve(DataItem[] data) {
		double max = 0;
		double min = 0;
		for (DataItem item :data){
			if (item.getLevel()>max) max = item.getLevel();
			if (item.getLevel()<min) min = item.getLevel();
		}
		
		
		GeneralPath p = new GeneralPath();
		p.moveTo(0,max/data[0].getLevel());
		
		
		for (int i=1;i<data.length;i++) {
			
			p.lineTo(i, max-data[i].getLevel());
			i++;
		}
		p.moveTo(0,max-data[0].getLevel());
		System.out.println(this.getHeight() + " width: " + this.getWidth());
		
		p.closePath();
		this.p1 = p;
	}

	private void calcRelVerlusteCurve(DataItem[] data) {
		GeneralPath p = new GeneralPath();
		p.moveTo(0,data[0].getLevel()+this.getHeight()/2);
		for (int i = 1; i < data.length; i++) {
			p.lineTo(i, (data[i].getLevel() - data[i - 1].getLevel())+this.getHeight()/2);

		}
		p.moveTo(0,data[0].getLevel()+this.getHeight()/2);
		p.closePath();
		this.p2 = p;
	}

	private void updateSettings() {
		if (path_1 != null) {
			double maxY = p1.getBounds().getMaxY();
			double minY = p1.getBounds().getMinY();
			double maxX = p1.getBounds().getMaxX();
			double minX = p1.getBounds().getMinX();
			System.out.println("maxy: " + maxY + "minY: " + minY + "\n maxX: " + maxX + "minX: " + minX);
			double panWid = this.getWidth();
			double panLen = this.getHeight();
			s1 = new Settings(minX,maxX,minY,maxY,panLen/(maxX),panWid/(maxY-minY));
			}
		

		if (p2 != null) {
			double maxY = p1.getBounds().getMaxY();
			double minY = p1.getBounds().getMinY();
			double maxX = p1.getBounds().getMaxX();
			double minX = p1.getBounds().getMinX();
			System.out.println("maxy: " + maxY + "minY: " + minY + "\n maxX: " + maxX + "minX: " + minX);
			
			double panLen = this.getHeight();
			s2 = new Settings(minX,maxX,minY,maxY,2,1);
		}
	}

}

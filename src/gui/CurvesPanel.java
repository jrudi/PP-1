package gui;

import general.Settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

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
		this.setBackground(new Color(0,0,0));
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
		AffineTransform at = new AffineTransform();
		at.setToScale(3,5);
		path_1 = at.createTransformedShape(p1);
		path_2 = at.createTransformedShape(p2);
		g2.draw(path_1);
	}

	public void mapDataToCurve(DataItem[] data) {
		GeneralPath p = new GeneralPath();
		p.moveTo(0,data[0].getLevel());
				
		for (int i=1;i<data.length;i++) {
			p.lineTo(i/*data[i].dayDiff(data[i-1])*/, data[i].getLevel());
			i++;
		}
		p.closePath();
		this.p1 = p;
	}

	private void calcRelVerlusteCurve(DataItem[] data) {
		GeneralPath p = new GeneralPath();
		p.moveTo(0, 0);
		for (int i = 1; i < data.length; i++) {
			p.lineTo(i, data[i].getLevel() - data[i - 1].getLevel());

		}
		p.closePath();
		this.p2 = p;
	}

	private void updateSettings() {
		if (path_1 != null) {
			Rectangle2D r1 = path_1.getBounds2D();
			Double minY = r1.getMinY();
			Double maxY = r1.getMaxY();
			Double minX = r1.getMinX();
			Double maxX = r1.getMaxX();
			this.currentSetting = new Settings(minX,maxX,minY,maxY,1,1);
			//skalierung berechnen und wieder ändern
		}

		if (p2 != null) {

		}
	}

}

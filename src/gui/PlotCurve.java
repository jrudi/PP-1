package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import parser.Parser;
import parser.StoxxParser;
import model.DataItem;
//import parser.Parser;
//import parser.StoxxParser;

public class PlotCurve extends JFrame {
	private static final long serialVersionUID = 1L;

	private CurvesPanel cp;   // Anzeigeflaeche fuer Kurve
	private ControlPanel control; // Panel mit Buttons zur Auswahl der Kurve
	private DataItem[] data;  // Feld mit Daten

	public PlotCurve(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.createComponents();
		this.createMenu();
		this.pack();
	}

	/* generiert Panels und baut Oberflaeche zusammen.*/
    private void createComponents(){
    	this.cp = new CurvesPanel();
    	this.getContentPane().add(cp,BorderLayout.CENTER);
    	this.cp.setVisible(true);
    	this.control = new ControlPanel();
    	this.getContentPane().add(control,BorderLayout.SOUTH);
    }
	
    /* Klasse fuer Panel mit Buttons zur Auswahl, ob Daten oder relative Aenderungen 
     * als Kurve angezeigt werden sollen Test
     */
    private class ControlPanel extends JPanel{
    	private static final long serialVersionUID = 1L;
    	private JRadioButton b1,b2;

    	/* Panel mit zwei Radio-Buttons, mit denen der Anzeige-Modus
    	 * geaendert werden kann. Der Modus wird ueber die Methode
    	 * setState in der aktuellenInstanz von CurvesPanel gesetzt.
    	 * und ein Neuzeichnen erzwungen.
    	 */
    	public ControlPanel(){
    		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
    		this.setAlignmentX(LEFT_ALIGNMENT);
    		this.setPreferredSize(new Dimension(300,50));
    		
    		b1 = new JRadioButton("Show Data");
    		b1.setSelected(true);
    		b1.setActionCommand("B1");
    		b2 = new JRadioButton("Show relative change");
    		b2.setActionCommand("B2");

    		ButtonGroup bg = new ButtonGroup();
    		bg.add(b1);
    		bg.add(b2);
    		this.add(b1);
    		this.add(Box.createHorizontalStrut(50));
    		this.add(b2);
    		ActionListener buttonListener = new ActionListener(){
    			public void actionPerformed(ActionEvent e){
    				JRadioButton rb = (JRadioButton)(e.getSource()); 
    				switch(rb.getActionCommand()) {
    				case "B1": PlotCurve.this.cp.setState(CurvesPanel.ORIG_CURVE); break;
    				case "B2": PlotCurve.this.cp.setState(CurvesPanel.L_CURVE); break;
    				default: PlotCurve.this.cp.setState(CurvesPanel.NO_CURVE); break;
    				}
    				PlotCurve.this.cp.repaint();

    			}
    		};
    		b1.addActionListener(buttonListener);
    		b2.addActionListener(buttonListener);
    	}
    	
    	/* bringt den Anzeigemodus in Erfahrung (ohne auf CurvesPanel zuzugreifen,
    	 *  sondern ueber den selektierten RadioButton
    	 */
    	public int getCurveState(){
    		return (this.b1.isSelected()) ? CurvesPanel.ORIG_CURVE : CurvesPanel.L_CURVE;
    	}
    	
    }
    
    /*  ruft die Methoden der aktuellen CurvesPanel-Instanz auf, die
     *  die beiden Kurven (neu) berechnet (Inklusives Settings Objekte) und
     *  den Zustand aktualisiert (Anzeige-Modus) sowie das Neuzeichnen des
     *  Panels erwirkt.
     */
    private void updateCurvesPanel(){
    	PlotCurve.this.cp.createCurves(data);
    	PlotCurve.this.cp.setState(PlotCurve.this.control.getCurveState());
		PlotCurve.this.cp.repaint();
    }

    private void createMenu(){
    	JMenuBar menubar = new JMenuBar();
    	JMenu menu = new JMenu("File");
    	JMenuItem load = new JMenuItem("Load data (csv)");
    	load.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    			JFileChooser chooser = new JFileChooser();
    			// nur csv-Dateien anzeigen
    			FileNameExtensionFilter filter = new FileNameExtensionFilter("csv","CSV");
    			chooser.setFileFilter(filter);
    			int returnVal = chooser.showOpenDialog(PlotCurve.this);
    			if(returnVal == JFileChooser.APPROVE_OPTION) {
    				Parser parser = new StoxxParser(chooser.getSelectedFile().getAbsolutePath());
    				parser.parseFile();
    				PlotCurve.this.data = parser.getData();
    				PlotCurve.this.updateCurvesPanel();
    			}
    		}
    	});
    	
    	JMenuItem crTestData = new JMenuItem("Create Test Data");
    	crTestData.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    			
    			try {
					model.TestData.createData();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		
    			PlotCurve.this.data = model.TestData.getData();
    			PlotCurve.this.updateCurvesPanel();
    		}
    	});
    	
    	menu.add(load);
    	menu.add(crTestData);
    	menubar.add(menu);
    	this.setJMenuBar(menubar);
    }
	


	public static void main(String[] args) {
		PlotCurve curve = new PlotCurve();
		curve.setVisible(true);
	}

}

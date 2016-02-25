package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import model.DataItem;
import parser.exceptions.*;


public class StoxxParser extends Parser{
	
	
	ArrayList<String> parseList,dateList,valueList;
	ArrayList<DataItem> dataList;
	public StoxxParser(String filename) {
		super(filename);
		this.parseList = new ArrayList<String>();
		this.dateList = new ArrayList<String>();
		this.valueList = new ArrayList<String>();
		this.dataList = new ArrayList<DataItem>();
	}

	@Override
	public DataItem[] getData(){
		DataItem[] dataArray = new DataItem[this.dataList.size()];
		return this.dataList.toArray(dataArray);
		 
	}

	@Override
	public void parseFile() {
		Scanner s= null;
		try {
			s = new Scanner(new File(this.filename));
		} catch (FileNotFoundException e) {
			System.out.println("'" + this.filename + "' was not found.");
			e.printStackTrace();
			
		}
		while(s.hasNextLine()){
			parseList.add(s.next());
		}
	}

	@Override
	protected void parseLine(String line) throws DataParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		Date dd = null; 
		GregorianCalendar gcal = new GregorianCalendar();

		String regex = "\\d{1,2}/\\d{1,2}/\\d{1,2},\\d+.\\d{1,2}";
		if(!line.matches(regex)) throw new DataParseException("not matching");
		
		String[] segs = line.split(",");
		double f = Double.parseDouble(segs[1]);
		
		try {
			dd = sdf.parse(segs[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		gcal.setTime(dd);
		this.dataList.add(new DataItem(gcal, f));
		
	}

}

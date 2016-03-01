package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TestData {

	private static List<DataItem> dataList;
	
	public static void createData() throws ParseException{
		dataList = new ArrayList<DataItem>();
		GregorianCalendar gcal = new GregorianCalendar();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
	    Date start = sdf.parse("2015.01.01");
	    Date peak = sdf.parse("2015.09.08");
	    Date end = sdf.parse("2016.05.15");
	    gcal.setTime(start);
	    int i = 1;
	    while (gcal.getTime().before(peak)) {
	        
			dataList.add(new DataItem(gcal, 102 + i*2));
			gcal.add(Calendar.DAY_OF_YEAR, 1);
			i++;
	    }
	    i=1;
	    while (gcal.getTime().before(end)) {
	    	gcal.add(Calendar.DAY_OF_YEAR, 1);
			dataList.add(new DataItem(gcal, 600 - i*2));
			i++;
	    }
		
	}
	
	public static DataItem[] getData(){
		DataItem[] dIarray = new DataItem[dataList.size()];
		dataList.toArray(dIarray);
		return dIarray;
	}
	
	/*public static void main(String[] args) {
		parser.StoxxParser sP = new parser.StoxxParser("ESTOXX50.csv");
		sP.parseFile();
		int i=1;
		for(DataItem a : sP.dataList){
			System.out.println(a + "   " + i);
			i++;
		}
	}*/
	
}

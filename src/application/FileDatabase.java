package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class FileDatabase {

	public static List<Integer> getKilledList(List<String> recordList) {

		List<Integer> numKilled= new ArrayList<Integer>();
		List<Integer>  numBullet= new ArrayList<Integer>();
		List<Integer>longTime= new ArrayList<Integer>();
		

		for (int i = 0; i < recordList.size(); i++) {
			// System.out.println(recordList.get(i));
			StringTokenizer st = new StringTokenizer(recordList.get(i), ",");

			if (st.hasMoreTokens()) {
				
				numKilled.add(Integer.parseInt(st.nextToken()));
				numBullet.add(Integer.parseInt(st.nextToken()));
				longTime.add(Integer.parseInt(st.nextToken()));
			}

		}

		return numKilled;

	}

	public static List<Integer> getnumBulletList(List<String> recordList) {

		List<Integer> numKilled= new ArrayList<Integer>();
		List<Integer>  numBullet= new ArrayList<Integer>();
		List<Integer>longTime= new ArrayList<Integer>();
		
		for (int i = 0; i < recordList.size(); i++) {
			// System.out.println(recordList.get(i));
			StringTokenizer st = new StringTokenizer(recordList.get(i), ",");

			if (st.hasMoreTokens()) {
				
				
				numKilled.add(Integer.parseInt(st.nextToken()));
				numBullet.add(Integer.parseInt(st.nextToken()));
				longTime.add(Integer.parseInt(st.nextToken()));
			}

		}

		return numBullet ;

	}
	
	public static List<Integer> getLongTimeList(List<String> recordList) {

		List<Integer> numKilled= new ArrayList<Integer>();
		List<Integer>  numBullet= new ArrayList<Integer>();
		List<Integer>longTime= new ArrayList<Integer>();
		
		for (int i = 0; i < recordList.size(); i++) {
			// System.out.println(recordList.get(i));
			StringTokenizer st = new StringTokenizer(recordList.get(i), ",");

			if (st.hasMoreTokens()) {
				
				
				numKilled.add(Integer.parseInt(st.nextToken()));
				numBullet.add(Integer.parseInt(st.nextToken()));
				longTime.add(Integer.parseInt(st.nextToken()));
			}

		}

		return longTime;

	}
	
	

	public static void writeRecord(int k, int b, int t) {
		
		String numkilled = String.valueOf(k);
		String numbullet = String.valueOf(b);
		String numlongtime = String.valueOf(t);
		
		File file = null;
		FileWriter fw = null;
		
		try {
			file = new File("record.txt");
			if (file.createNewFile()) {
				System.out.println("File1" + file.getAbsolutePath());
				fw = new FileWriter(file, true);
				fw.write(numkilled+ ","+ numbullet +","+ numlongtime + "\n");
			}else {
				System.out.println("File1" + file.getAbsolutePath());
				fw = new FileWriter(file, true);
				fw.append(numkilled+ ","+ numbullet +","+ numlongtime + "\n");
			
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Finish writing records.");
	}

	public static List<String> readRecords() {
		List<String> historyList = new ArrayList<String>();
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			file = new File("record.txt");
			System.out.println("File: " + file.getAbsolutePath());
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				historyList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Finish reading file.");
		return historyList;
	}

}


package model;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SudokuExtract implements Runnable {
    public static BufferedReader getHTML(String link) throws IOException {
	URL url = new URL(link);
	URLConnection uc = url.openConnection();
	BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
	return br;
    }
    
    public static Semaphore maxThread = new Semaphore(10);
    public static ArrayList<String> sudokus = new ArrayList<String>();
    public String url;
    public String sudoku = "";

    public SudokuExtract(int i) {
	this.url = "http://www.menneske.no/sudoku/5/eng/solve.html?number=" + Integer.toString(i);
    }

    public static void main(String[] args) throws IOException {
	long startTime = System.currentTimeMillis();
	for (int i = 1; i < 2001; i++) {
	    new Thread(new SudokuExtract(i)).start();
	}

	while (Thread.activeCount() > 1) {
	}
	;

	FileWriter fw = new FileWriter("sudokus.25", true);
	for (String sudoku : sudokus) {
	    fw.write(sudoku + "\n"); 
	}
	long endTime = System.currentTimeMillis();
	long totalTime = (endTime - startTime) / 1000;
	System.out.println("Completed. Run time: " + Double.toString(totalTime) + " seconds.");
	fw.close();
    }

    @Override
    public void run() {
	// TODO Auto-generated method stub
	try {
	    maxThread.acquire();
	    BufferedReader br = getHTML(url);
	    maxThread.release();
	    String line;
	    while ((line = br.readLine()) != null) {
		if (line.matches("<td class=\"(.+)\">(.+)</td>")) {
		    String number = line.substring(line.indexOf("\">") + 2, line.indexOf("</"));
		    if (!number.matches("[0-9]+"))
			number = "0";
		    sudoku = sudoku + number + " ";
		}
	    }
	    System.out.println(this.url.substring(this.url.indexOf("=") + 1));
	    synchronized (sudokus) {
		sudokus.add(sudoku);
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
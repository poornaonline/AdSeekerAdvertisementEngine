package com.research.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FilterWordList {
	
	private static ArrayList<String> wordSet = null;
	
	private static String wordListFilePath = "/Users/Poorna/Documents/Eclipse Workspace/adseeker/wordset.txt";
	
	
	private FilterWordList() {
		
		if ( wordSet == null ) {
			
			try {
					File file = new File(wordListFilePath);
					FileReader fileReader = new FileReader(file);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					String line;
					wordSet = new ArrayList<String>();
				
					while ((line = bufferedReader.readLine()) != null) {
						wordSet.add(line);
				}
				
				fileReader.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			System.out.println("WordSet ArrayList already populated");
		}
	}
	
	
	public static ArrayList<String> getWordListArray()
	{
		if (wordSet == null)
		{
			FilterWordList filterWordList = new FilterWordList();
		}
		
		return wordSet;
	}
		

}

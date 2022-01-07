package de.wi2020sebgroup1.cinema.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HTMLService {
	
	public static String read(String fileName, String username) {
		
		String s = null;
		String filePath = new File("").getAbsolutePath();
		String path = filePath+"\\src\\main\\resources\\html\\"+fileName;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();
            StringBuilder sb = new StringBuilder();

            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }

            s = sb.toString();
        } catch (IOException e) {
			e.printStackTrace();
		}
		
		s = s.replace("KINO-USERNAME", username);
		
		return s;
	}
	
}
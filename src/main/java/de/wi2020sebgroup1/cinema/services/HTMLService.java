package de.wi2020sebgroup1.cinema.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;

import de.wi2020sebgroup1.cinema.configurationObject.EmailVariablesObject;

@Service
public class HTMLService {
	
	public String read(String fileName, EmailVariablesObject evo) {
		
		String s = null;
		URL url = null;
		
		try {
			url = new URL("https://raw.githubusercontent.com/DrBackmischung/Kino-Backend/dev/src/main/resources/html/"+fileName);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String line = br.readLine();
            StringBuilder sb = new StringBuilder();

            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }

            s = sb.toString();
        } catch (Exception e) {
			e.printStackTrace();
		}

		s = s.replace("KINO-USERNAME", evo.getUsername());
		s = s.replace("KINO-FIRSTNAME", evo.getFirstName());
		s = s.replace("KINO-LASTNAME", evo.getLastName());
		s = s.replace("KINO-FILE", evo.getFile());
		s = s.replace("KINO-LINK", evo.getLink());
		s = s.replace("KINO-SDATE", evo.getShowDate());
		s = s.replace("KINO-STIME", evo.getShowTime());
		s = s.replace("KINO-STITLE", evo.getShowTitle());
		s = s.replace("KINO-S1", evo.getString1());
		s = s.replace("KINO-S2", evo.getString2());
		s = s.replace("KINO-S3", evo.getString3());
		
		return s;
	}
	
}

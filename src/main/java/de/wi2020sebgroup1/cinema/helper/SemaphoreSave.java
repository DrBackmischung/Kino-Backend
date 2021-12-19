package de.wi2020sebgroup1.cinema.helper;

import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Semaphore;

import org.springframework.stereotype.Service;

@Service
public class SemaphoreSave {
	
	public static TreeMap<UUID, Semaphore> semaphores = new TreeMap<>();
	
	public static Semaphore getSemaphore(UUID id) {
		
		if(semaphores.containsKey(id)) {
			return semaphores.get(id);
		}else {
			Semaphore newSemaphore = new Semaphore(1, true);
			semaphores.put(id, newSemaphore);
			return newSemaphore;
		}
	}

}

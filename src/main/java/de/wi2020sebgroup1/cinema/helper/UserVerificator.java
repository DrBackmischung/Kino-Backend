package de.wi2020sebgroup1.cinema.helper;

import java.util.UUID;

public class UserVerificator {
	
	public static final int KEY_OFFSET = 4;
	
	public static boolean verify(String userID, int key) {
		int k = key-KEY_OFFSET;
		UUID u = UUID.fromString(userID);
		int hash = u.hashCode();
		return k == hash;
	}
	
}

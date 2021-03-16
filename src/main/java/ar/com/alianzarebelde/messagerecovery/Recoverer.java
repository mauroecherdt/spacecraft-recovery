package ar.com.alianzarebelde.messagerecovery;

import java.util.Arrays;

import ar.com.alianzarebelde.messagerecovery.exceptions.InvalidDistanceException;
import ar.com.alianzarebelde.messagerecovery.models.Coordinate;
import ar.com.alianzarebelde.messagerecovery.resolvers.LocationResolver;
import ar.com.alianzarebelde.messagerecovery.resolvers.MessageResolver;

public class Recoverer {


    public static void main(String[] args) {
    	
    	Coordinate location;
		try {
			location = LocationResolver.getLocation(608.27f, 707.10f, 948.68f);
			System.out.println(location);
		} catch (InvalidDistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
//    	String[] s1 = {"", "", "", "este", "", "un", "", "lindo"};
//    	String[] s2 = {"", "este", "", "un", "mensaje", ""};
//    	String[] s3 = {"", "es", "un", "mensaje", ""};
//    	
//    	String[] res = MessageResolver.getMessage(s1, s2, s3);
//    	System.out.println(Arrays.toString(res));
    	
    }
	

}

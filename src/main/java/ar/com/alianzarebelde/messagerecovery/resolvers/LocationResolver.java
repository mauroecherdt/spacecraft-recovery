package ar.com.alianzarebelde.messagerecovery.resolvers;

import ar.com.alianzarebelde.messagerecovery.exceptions.InvalidDistanceException;
import ar.com.alianzarebelde.messagerecovery.models.Coordinate;

public class LocationResolver {

	public static Coordinate KENOBI = new Coordinate(-500, -200);
	
	public static Coordinate SKYWALKER = new Coordinate(100, -100);
	
	public static Coordinate SATO = new Coordinate(500, 100);
	
	
	
	/**
	 * Obtiene las coordendas del emisor.
	 * 
	 * @param d1 -> Distancia del emisor a Kenobi
	 * @param d2 -> Distancia del emisor a Skywalker
	 * @param d3 -> Distancia del emisor a Sato
	 * @return
	 * @throws InvalidDistanceException 
	 */
	public static Coordinate getLocation(float d1, float d2, float d3) throws InvalidDistanceException {
		if(d1 <= 0 || d2 <= 0 || d3 <= 0) {
			throw new InvalidDistanceException("Las distancias deben ser mayor a cero.");
		}
		return resolveCoordinate(d1, d2, d3);
	}	
	
	
	/**
	 * 
	 * Obtiene cordenadas X, Y
	 * 
	 * 
	 * @param d1 
	 * @param d2
	 * @param d3
	 * @return
	 */
	private static Coordinate resolveCoordinate(float d1, float d2, float d3) {
		
		float varA = resolveVarA(d1, d2);
		float varB = resolveVarB(d2, d3);
		
		return new Coordinate(resolveX(varA, varB), resolveY(varA, varB));
		
	}
	
	
	/**
	 * 
	 * Obtiene cordenada X
	 * 
	 *       4A  -  2B
	 * X = --------------
	 *         3200
	 *         
	 *        
	 * @param varA
	 * @param varB
	 * @return
	 */
	private static float resolveX(float varA, float varB) {
		return (4*varA - 2*varB) / 3200;
	}
	
	
	
	/**
	 * 
	 * Obtiene cordenada  Y
	 * 
	 *        
	 *      12A  -  8A  
	 * Y = --------------       
	 *         3200
	 * 
	 * 
	 * @param varA
	 * @param varB
	 * @return
	 */
	private static float resolveY(float varA, float varB) {
		return (12*varB - 8*varA) / 3200;
	}

	
	/**
	 * 
	 * Obtiene variable A
	 * 
	 * varA = d1^2 -d2^2 - 270000
	 * 
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	private static float resolveVarA(float d1, float d2) {
		return power(d1, 2) - power(d2, 2) - 270000;
	}
	
	
	/**
	 * 
	 * Obtiene variable B
	 * 
	 * varB = 240000 + d2^2 - d3^2
	 * 
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	private static float resolveVarB(float d2, float d3) {
		return 240000 + power(d2, 2) - power(d3, 2);
	}
	
	
	private static float power(final float base, final int power) {
	    float result = 1;
	    for( int i = 0; i < power; i++ ) {
	        result *= base;
	    }
	    return result;
	}
	
}

package ar.com.alianzarebelde.messagerecovery.resolvers;

import ar.com.alianzarebelde.messagerecovery.exceptions.InvalidDistanceException;
import ar.com.alianzarebelde.messagerecovery.models.Coordinate;

public class LocationResolver {

	private static float TOLERANCE_PERCENTAGE = 0.5f;
	
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
	 * @throws InvalidDistanceException 
	 */
	private static Coordinate resolveCoordinate(float d1, float d2, float d3) throws InvalidDistanceException {
		
		float varA = resolveVarA(d1, d2);
		float varB = resolveVarB(d2, d3);
		Coordinate coordinate = new Coordinate(resolveX(varA, varB), resolveY(varA, varB));
		
		verification(coordinate.getX(), coordinate.getY(), d1);
		return coordinate;
		
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
	
	
	/**
	 * 
	 * Verificacion del resultado.
	 * 
	 * x^2 + y^2 + 1000*x + 400*y + 290000 == d1^2
	 * 
	 * @param x
	 * @param y
	 * @param d1
	 * @throws InvalidDistanceException
	 */
	private static void verification(float x, float y, float d1) throws InvalidDistanceException {
		float distance = power(x,2) + power(y,2) + 1000*x + 400*y + 290000;
		if(!approximatelyEqual(distance, power(d1, 2), TOLERANCE_PERCENTAGE)) {
			throw new InvalidDistanceException("Distancias inválidas.");
		}
	}
	
	/**
	 * Calculo si es "aproximadamente" igual. 
	 * 
	 * @param desiredValue
	 * @param actualValue
	 * @return
	 */
	public static boolean approximatelyEqual(float desiredValue, float actualValue, float tolerancePercentage) {
	    float diff = Math.abs(desiredValue - actualValue);         
	    float tolerance = tolerancePercentage/100 * desiredValue;  
	    return diff < tolerance;                                   
	}
	
	/**
	 * Calculo exponencial.
	 * 
	 * @param base
	 * @param power
	 * @return
	 */
	private static float power(final float base, final int power) {
	    float result = 1;
	    for( int i = 0; i < power; i++ ) {
	        result *= base;
	    }
	    return result;
	}
}

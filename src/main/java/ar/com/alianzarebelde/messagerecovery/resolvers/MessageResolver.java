package ar.com.alianzarebelde.messagerecovery.resolvers;

import java.util.Arrays;
import java.util.List;

import ar.com.alianzarebelde.messagerecovery.exceptions.InvalidMessageException;

public class MessageResolver {

	/**
	 * Obtiene el mensaje del emisor.
	 * 
	 * 
	 * @param message1
	 * @param message2
	 * @param message3
	 * @return
	 * @throws InvalidMessageException
	 */
	public static String getMessage(String[] message1, String[] message2, String[] message3)
			throws InvalidMessageException {

		if (isEmptyMessage(message1) || isEmptyMessage(message2) || isEmptyMessage(message3)) {
			throw new InvalidMessageException("La cantidad de palabras del mensaje debe ser mayor a cero.");
		}

		String[] decodeMessage = null;

		int lengthMessage1 = message1.length;
		int lengthMessage2 = message2.length;
		int lengthMessage3 = message3.length;

		if ((lengthMessage1 == lengthMessage2) && (lengthMessage2 == lengthMessage3)) {
			decodeMessage = MessageResolver.decodeMessage(message1, message2, message3);
		} else {

			int maxLength = getMaxLength(lengthMessage1, lengthMessage2, lengthMessage3);

			String[] messageAligned1 = alignArray(message1, maxLength);
			String[] messageAligned2 = alignArray(message2, maxLength);
			String[] messageAligned3 = alignArray(message3, maxLength);

			decodeMessage = MessageResolver.decodeMessage(messageAligned1, messageAligned2, messageAligned3);

		}

		return String.join(" ", decodeMessage);
	}

	private static boolean isEmptyMessage(String[] arrays) {
		return arrays == null || arrays.length == 0;
	}

	private static int getMaxLength(int n1, int n2, int n3) {
		return Math.max(n1, Math.max(n2, n3));
	}

	/**
	 * 
	 * Alinea el array hasta la longitud n completando con "" al comienzo. Si n <
	 * array.length se devuelve el array original.
	 * 
	 * @param array
	 * @param n
	 * @return
	 */
	private static String[] alignArray(String[] array, int n) {

		String[] res = null;

		int diff = n - array.length;
		if (diff > 0) {
			res = new String[n];
			for (int i = 0; i < diff; i++) {
				res[i] = "";
			}

			System.arraycopy(array, 0, res, diff, array.length);

		} else {
			res = array;
		}

		return res;
	}

	private static String[] decodeMessage(String[] message1, String[] message2, String[] message3) {

		int length = message1.length;

		String[] decodeMessage = new String[length];
		for (int i = 0; i < length; i++) {

			String decodeString = null;

			String s1 = message1[i];
			String s2 = message2[i];
			String s3 = message3[i];

			long distinct = getDistinct(s1, s2, s3);
			if (distinct == 1) {
				decodeString = s1;
			} else if (distinct == 3) {
				decodeString = "";
			} else {
				decodeString = decodeValidString(s1, s2, s3);
			}

			decodeMessage[i] = decodeString;
		}

		return decodeMessage;
	}

	/**
	 * 
	 * Dados 3 strings, devuelve la cantidad de "distintos".
	 * 
	 * @param s1
	 * @param s2
	 * @param s3
	 * @return
	 */
	private static long getDistinct(String s1, String s2, String s3) {
		List<String> strings = Arrays.asList(s1, s2, s3);
		return strings.stream().distinct().count();
	}

	private static String decodeValidString(String s1, String s2, String s3) {

		String result = getValidString(s1, s2, s3);
		if (result == null) {
			result = getValidString(s1, s3, s2);
			if (result == null) {
				result = getValidString(s2, s3, s1);
				if (result == null) {
					result = "";
				} 
			}
		}

		return result;
	}

	private static String getValidString(String compareS1, String compareS2, String rest) {

		String result = null;

		if (compareS1.equals(compareS2)) {
			if (compareS1.isEmpty()) {
				result = rest;
			} else {
				result = compareS1;
			}
		}

		return result;
	}

}

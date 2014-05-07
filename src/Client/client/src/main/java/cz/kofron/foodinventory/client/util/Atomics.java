/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.util;

// TODO: Auto-generated Javadoc
/**
 * The Class Atomics.
 *
 * @author kofee
 */
public class Atomics
{

	/**
	 * Integer to bytes.
	 *
	 * @param i the i
	 * @param bytes the bytes
	 */
	public static void integerToBytes(int i, byte[] bytes)
	{
		bytes[0] = (byte) ((i >> 24) & 0xFF);
		bytes[1] = (byte) ((i >> 16) & 0xFF);
		bytes[2] = (byte) ((i >> 8) & 0xFF);
		bytes[3] = (byte) (i & 0xFF);
	}

	/**
	 * Integer from bytes.
	 *
	 * @param bytes the bytes
	 * @return the int
	 */
	public static int integerFromBytes(byte[] bytes)
	{
		return ((bytes[0] & 0xFF) << 24) + ((bytes[1] & 0xFF) << 16) + ((bytes[2] & 0xFF) << 8) + (bytes[3] & 0xFF);
	}
}

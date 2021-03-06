/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.protocol;

import java.io.IOException;
import java.io.InputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class BufferReader.
 *
 * @author kofee
 */
public class BufferReader
{
	
	/**
	 * Read fully.
	 *
	 * @param bytes the bytes
	 * @param len the len
	 * @param is the is
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void readFully(byte[] bytes, int len, InputStream is) throws IOException
	{
		int got = 0;

		while (len > got)
		{
			int ret = is.read(bytes, got, len - got);
			if (ret <= 0 || ret > (len - got))
			{
				throw new IOException("Read error!");
			}
			got += ret;
		}

	}
}

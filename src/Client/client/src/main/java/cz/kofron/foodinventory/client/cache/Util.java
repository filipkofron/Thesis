package cz.kofron.foodinventory.client.cache;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 28.3.14.
 */
public class Util
{
	
	/** The Constant IO_BUFFER_SIZE. */
	public static final int IO_BUFFER_SIZE = 8 * 1024;

	/**
	 * Instantiates a new util.
	 */
	private Util()
	{
	}

	;

	/**
	 * Checks if is external storage removable.
	 *
	 * @return true, if is external storage removable
	 */
	public static boolean isExternalStorageRemovable()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
		{
			return Environment.isExternalStorageRemovable();
		}
		return true;
	}

	/**
	 * Gets the external cache dir.
	 *
	 * @param context the context
	 * @return the external cache dir
	 */
	public static File getExternalCacheDir(Context context)
	{
		if (hasExternalCacheDir())
		{
			return context.getExternalCacheDir();
		}

		// Before Froyo we need to construct the external cache dir ourselves
		final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
	}

	/**
	 * Checks for external cache dir.
	 *
	 * @return true, if successful
	 */
	public static boolean hasExternalCacheDir()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/** The Constant US_ASCII. */
	static final Charset US_ASCII = Charset.forName("US-ASCII");
	
	/** The Constant UTF_8. */
	static final Charset UTF_8 = Charset.forName("UTF-8");

	/**
	 * Read fully.
	 *
	 * @param reader the reader
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static String readFully(Reader reader) throws IOException {
		try {
			StringWriter writer = new StringWriter();
			char[] buffer = new char[1024];
			int count;
			while ((count = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, count);
			}
			return writer.toString();
		} finally {
			reader.close();
		}
	}

	/**
	 * Deletes the contents of {@code dir}. Throws an IOException if any file
	 * could not be deleted, or if {@code dir} is not a readable directory.
	 *
	 * @param dir the dir
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static void deleteContents(File dir) throws IOException
	{
		File[] files = dir.listFiles();
		if (files == null) {
			throw new IOException("not a readable directory: " + dir);
		}
		for (File file : files) {
			if (file.isDirectory()) {
				deleteContents(file);
			}
			if (!file.delete()) {
				throw new IOException("failed to delete file: " + file);
			}
		}
	}

	/**
	 * Close quietly.
	 *
	 * @param closeable the closeable
	 */
	static void closeQuietly(/*Auto*/Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (RuntimeException rethrown) {
				throw rethrown;
			} catch (Exception ignored) {
			}
		}
	}

}

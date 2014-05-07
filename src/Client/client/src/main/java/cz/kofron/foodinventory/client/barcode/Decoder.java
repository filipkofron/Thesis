package cz.kofron.foodinventory.client.barcode;

import android.graphics.Rect;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.HybridBinarizer;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 9.3.14.
 */
public class Decoder
{
	
	/** The call back. */
	private ResultCallback callBack;
	
	/** The multi format reader. */
	private MultiFormatReader multiFormatReader;
	
	/** The rotation data buffer. */
	private byte[] rotationDataBuffer;

	/**
	 * Instantiates a new decoder.
	 *
	 * @param callBack the call back
	 */
	public Decoder(ResultCallback callBack)
	{
		this.callBack = callBack;

		multiFormatReader = new MultiFormatReader();
		multiFormatReader.setHints(DecodeHintProvider.provideHints());
	}

	/**
	 * Builds the luminance source.
	 *
	 * @param data the data
	 * @param width the width
	 * @param height the height
	 * @param rotated the rotated
	 * @return the planar yuv luminance source
	 */
	public synchronized PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height, boolean rotated)
	{
		if (rotated)
		{
			if (rotationDataBuffer == null)
			{
				rotationDataBuffer = new byte[data.length];
			}

			for (int y = 0; y < height; y++)
			{
				for (int x = 0; x < width; x++)
				{
					rotationDataBuffer[x * height + height - y - 1] = data[x + y * width];
				}
			}
			int tmp = width;
			width = height;
			height = tmp;
		}

		Rect rect = new Rect();
		rect.top = 0;
		rect.left = 0;
		rect.right = width;
		rect.bottom = height;

		return new PlanarYUVLuminanceSource(rotated ? rotationDataBuffer : data, width, height, rect.left, rect.top,
				rect.width(), rect.height(), false);
	}

	/**
	 * Decode.
	 *
	 * @param data the data
	 * @param width the width
	 * @param height the height
	 * @param rotated the rotated
	 */
	public void decode(byte[] data, int width, int height, boolean rotated)
	{
		String someResult = "";

		PlanarYUVLuminanceSource source = buildLuminanceSource(data, width, height, rotated);

		Result rawResult = null;
		if (source != null)
		{
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			try
			{
				rawResult = multiFormatReader.decodeWithState(bitmap);
			}
			catch (ReaderException re)
			{
				// continue
			}
			finally
			{
				multiFormatReader.reset();
			}
		}

		if (rawResult != null)
		{
			ParsedResult parsedResult = ResultParser.parseResult(rawResult);

			someResult = new String(parsedResult.getDisplayResult().replace("\r", ""));

			callBack.presentResult(someResult);
		}
	}
}

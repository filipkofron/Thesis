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

/**
 * Created by kofee on 9.3.14.
 */
public class Decoder
{
	private ResultCallback callBack;
	private MultiFormatReader multiFormatReader;
	private byte[] rotationDataBuffer;

	public Decoder(ResultCallback callBack)
	{
		this.callBack = callBack;

		multiFormatReader = new MultiFormatReader();
		multiFormatReader.setHints(DecodeHintProvider.provideHints());
	}

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

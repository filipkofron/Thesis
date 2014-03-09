package cz.kofron.foodinventory.client.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by kofee on 9.3.14.
 */
public class DecodeHintProvider
{
	public static final Set<BarcodeFormat> PRODUCT_FORMATS;

	static
	{
		PRODUCT_FORMATS = EnumSet.of(BarcodeFormat.UPC_A,
				BarcodeFormat.UPC_E,
				BarcodeFormat.EAN_13,
				BarcodeFormat.EAN_8,
				BarcodeFormat.RSS_14,
				BarcodeFormat.RSS_EXPANDED);
	}

	public static Map<DecodeHintType,Object>  provideHints()
	{
		Map<DecodeHintType,Object> hints = new EnumMap<>(DecodeHintType.class);
		Collection<BarcodeFormat> decodeFormats = EnumSet.noneOf(BarcodeFormat.class);
		decodeFormats.addAll(PRODUCT_FORMATS);

		hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

		/*
		hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, new ResultPointCallback()
		{
			@Override
			public void foundPossibleResultPoint(ResultPoint point)
			{

			}
		});*/

		return hints;
	}
}

package cz.kofron.foodinventory.prototype;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.client.android.DecodeFormatManager;
import com.google.zxing.client.android.PreferencesActivity;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * This thread does all the heavy lifting of decoding the images.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class DecodeWorker extends Thread {

        public static final String BARCODE_BITMAP = "barcode_bitmap";
        public static final String BARCODE_SCALED_FACTOR = "barcode_scaled_factor";

        private final ScanFragment scanFragment;
        private final Map<DecodeHintType,Object> hints;
        private Handler handler;
        private final CountDownLatch handlerInitLatch;

        public DecodeWorker(ScanFragment scanFragment,
                     Collection<BarcodeFormat> decodeFormats,
                     Map<DecodeHintType,?> baseHints,
                     String characterSet,
                     ResultPointCallback resultPointCallback) {

            this.scanFragment = scanFragment;
            handlerInitLatch = new CountDownLatch(1);

            hints = new EnumMap<>(DecodeHintType.class);
            if (baseHints != null) {
                hints.putAll(baseHints);
            }

            // The prefs can't change while the thread is running, so pick them up once here.
            if (decodeFormats == null || decodeFormats.isEmpty()) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(scanFragment.getActivity());
                decodeFormats = EnumSet.noneOf(BarcodeFormat.class);
                if (prefs.getBoolean(PreferencesActivity.KEY_DECODE_1D_PRODUCT, false)) {
                    decodeFormats.addAll(DecodeFormatManager.PRODUCT_FORMATS);
                }
                if (prefs.getBoolean(PreferencesActivity.KEY_DECODE_1D_INDUSTRIAL, false)) {
                    decodeFormats.addAll(DecodeFormatManager.INDUSTRIAL_FORMATS);
                }
                if (prefs.getBoolean(PreferencesActivity.KEY_DECODE_QR, false)) {
                    decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
                }
                if (prefs.getBoolean(PreferencesActivity.KEY_DECODE_DATA_MATRIX, false)) {
                    decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
                }
                if (prefs.getBoolean(PreferencesActivity.KEY_DECODE_AZTEC, false)) {
                    decodeFormats.addAll(DecodeFormatManager.AZTEC_FORMATS);
                }
                if (prefs.getBoolean(PreferencesActivity.KEY_DECODE_PDF417, false)) {
                    decodeFormats.addAll(DecodeFormatManager.PDF417_FORMATS);
                }
            }
            hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

            if (characterSet != null) {
                hints.put(DecodeHintType.CHARACTER_SET, characterSet);
            }
            hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
            Log.i("DecodeThread", "Hints: " + hints);
        }

        public Handler getHandler() {
            try {
                handlerInitLatch.await();
            } catch (InterruptedException ie) {
                // continue?
            }
            return handler;
        }

        @Override
        public void run() {
            Looper.prepare();
            handler = new DecodeHandler(scanFragment, hints);
            handlerInitLatch.countDown();
            Looper.loop();
        }

    }

package cz.kofron.foodinventory.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.PODResult;
import cz.kofron.foodinventory.client.task.param.SearchPODParam;
import cz.kofron.foodinventory.client.util.Download;
import cz.kofron.foodinventory.client.util.PODParser;

/**
 * Created by kofee on 26.3.14.
 */
public class SearchPODTask extends AsyncTask<Object, Void, Void>
{
	public static interface PODResultListener
	{
		public void onResults(ArrayList<PODResult> results);
	}

	private SearchPODParam param;
	private Context context;
	private ArrayList<PODResult> results;
	private ProgressDialog pd;
	private final static String GET_FORMAT = "http://pod.opendatasoft.com/api/records/1.0/search?dataset=pod_gtin&q=%s&facet=gtin_nm";

	public SearchPODTask(SearchPODParam param, Context activity)
	{
		this.param = param;
		this.context = activity;
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();

		pd = new ProgressDialog(context);
		pd.setTitle("Searching Product Open Data");
		pd.setMessage("Quering for: " + param.gtin);
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}

	@Override
	protected Void doInBackground(Object... objects)
	{
		String query = String.format(GET_FORMAT, param.gtin);

		String data = Download.downloadString(query);

		System.out.println("Downloaded: '" + data + "'");

		if(data != null)
		{
			results = PODParser.parsePODs(data);
		}

		if(results != null)
		{
			System.out.println("Got " + results.size() + " results.");

			for (PODResult result : results)
			{
				System.out.println("Result: " + result.toString());
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid)
	{
		super.onPostExecute(aVoid);

		pd.dismiss();

		if(param.podResultListener != null)
		{
			param.podResultListener.onResults(results);
		}
	}
}

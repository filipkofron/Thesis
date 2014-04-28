package cz.kofron.foodinventory.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.PODResult;
import cz.kofron.foodinventory.client.task.param.SearchPODParam;
import cz.kofron.foodinventory.client.util.Download;
import cz.kofron.foodinventory.client.util.PODParser;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 26.3.14.
 */
public class SearchPODTask extends AsyncTask<Object, Void, Void>
{
	
	/**
	 * The listener interface for receiving PODResult events.
	 * The class that is interested in processing a PODResult
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addPODResultListener<code> method. When
	 * the PODResult event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see PODResultEvent
	 */
	public static interface PODResultListener
	{
		
		/**
		 * On results.
		 *
		 * @param results the results
		 */
		public void onResults(ArrayList<PODResult> results);
	}

	/** The param. */
	private SearchPODParam param;
	
	/** The context. */
	private Context context;
	
	/** The results. */
	private ArrayList<PODResult> results;
	
	/** The pd. */
	private ProgressDialog pd;
	
	/** The Constant GET_FORMAT. */
	private final static String GET_FORMAT = "http://pod.opendatasoft.com/api/records/1.0/search?dataset=pod_gtin&q=%s&facet=gtin_nm";

	/**
	 * Instantiates a new search pod task.
	 *
	 * @param param the param
	 * @param activity the activity
	 */
	public SearchPODTask(SearchPODParam param, Context activity)
	{
		this.param = param;
		this.context = activity;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
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

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Void doInBackground(Object... objects)
	{
		String query = String.format(GET_FORMAT, param.gtin);

		String data = Download.downloadString(query);

		if(data != null)
		{
			results = PODParser.parsePODs(data);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
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

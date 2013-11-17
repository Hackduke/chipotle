package edu.hackduke.chipotle;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class MapTask extends AsyncTask<String, String, Drawable>{
	protected Drawable Imagehandler(String url) {
		try {
			InputStream is = (InputStream) this.fetch(url);
			Drawable draw = Drawable.createFromStream(is, "src");
			Log.d("running", "Imagehandler");
			return draw;
		} catch (MalformedURLException e) {
			Log.d("url", "malformed url");
			return null;
		} catch (IOException e) {
			e.printStackTrace(System.out);
			Log.e("url", "ioexception");
			return null;
		}
	}

	protected Object fetch(String address) throws MalformedURLException,
			IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		Log.d("fetching", "content");
		return content;
	}

	@Override
	protected Drawable doInBackground(String... arg0) {
		Log.d("MapTask", "running");
		return Imagehandler(arg0[0]);
	}

}

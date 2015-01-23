package ro.project.comparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * 
 * @author Dragos
 *
 */
public class HtmlManager {

	/**
	 * Save the given HTML in the file from the given path.
	 * 
	 * @param path
	 *            The location of the file to save in.
	 * @param version
	 *            The name of the file to save in.
	 * @param html
	 *            The html code to save in the file.
	 */
	public void saveHtml(String path, String version, String html) {
		File file = new File(path + "\\\\" + version + ".html");

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(html);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Used for retrieving the HTML body from a given URL. By my understanding,
	 * the body is enough to compare or parse it after.
	 * 
	 * @param url
	 *            String representation of a URL.
	 * @return String the Html body retrieved from the URL.
	 */
	public String getHtmlFromUrl(String url) {
		Connection.Response html = null;
		try {
			html = Jsoup.connect(url).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return html.body();
	}

	/**
	 * Gets the HTML stored locally in file as a String.
	 * 
	 * @param path
	 *            The full location of the HTML file to read from.
	 * @return The HTML from the file as a String.
	 */
	public String getHtmlFromFile(String location) {
		String content = "";
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(location));
			String str;
			while ((str = in.readLine()) != null) {
				content += str;

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return content;
	}

	/**	
	 * Does almost nothing for the moment, and doesn't see as same the umlauts. Maybe
	 * try to encode the strings and then compare them. Or encode them at the
	 * beginning of the application and pass them as encoded.
	 * 
	 * @param lastVersionHtml
	 *            String representation of the last version of HTML, this one
	 *            was already stored locally.
	 *            
	 * @param html
	 *            String representation of the current version of HTML,
	 *            depending if they are (or not) equal, this will be stored (or
	 *            not) locally
	 *            
	 * @return true if the two HTML are the same, otherwise false.
	 */
	public boolean isDifferent(String lastVersionHtml, String html) {

		String lastVersionHtmlTemp = lastVersionHtml.replace("\n", "").replace(
				"\r", "");
		String htmlTemp = html.replace("\n", "").replace("\r", "");
		if (lastVersionHtmlTemp.equals(htmlTemp)) {
			return false;

		} else {
			return true;
		}

	}

}

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
	 * Used for retrieving the html body from a given URL. By my understanding,
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
	 * Gets the HTML stored locally as a String.
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
			/*
			 * StringBuilder b = new StringBuilder(content); b =
			 * b.replace(content.lastIndexOf("\n"), content.lastIndexOf("\n") +
			 * 1, ""); content = b.toString();
			 */
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
	 * THIS IS NO GOOD. HASHING FOR NO REASON....
	 * 
	 * Hashes the String representing the last version of HTML and the current
	 * version of HTML and then compares them to see if they are or not
	 * identical.
	 * 
	 * @param lastVersionHtml
	 *            String representation of the last version of HTML, this one
	 *            was already stored locally.
	 * @param html
	 *            String representation of the current version of HTML,
	 *            depending if they are (or not) equal, this will be stored (or
	 *            not) locally
	 * @return true if the two HTML are the same, otherwise false.
	 */
	public boolean isDifferent(String lastVersionHtml, String html) {

		String lastVersionHtmlTemp = lastVersionHtml.replace(
				System.getProperty("line.separator"), "");
		String htmlTemp = html
				.replace(System.getProperty("line.separator"), "");
		/*byte[] hash1 = null;
		byte[] hash2 = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hash1 = digest.digest(lastVersionHtmlTemp.getBytes("UTF-8"));
			hash2 = digest.digest(htmlTemp.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if ((hash1 != null) && (hash2 != null)) {
			if (MessageDigest.isEqual(hash1, hash2)) {
				return false;
			} else {
				return true;
			}
		}
		
		return false;
*/
		
		if(lastVersionHtmlTemp.equals(htmlTemp)) {
			return false;
		} else {
			return true;
		}
		
	}

}

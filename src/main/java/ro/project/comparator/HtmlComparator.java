package ro.project.comparator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

/**
 * @author Dragos
 * 
 */
public class HtmlComparator {

	HtmlManager htmlManager;

	public HtmlComparator() {
		htmlManager = new HtmlManager();
	}

	/**
	 * Compares the two HTMLS and prints the differences in two files which are
	 * created locally at the location given by the path variable.
	 * 
	 * @param pathLastVersionHtml
	 *            String The path to the last version of HTML, which is read
	 *            from file.
	 * @param currenthVersionHtml
	 *            String The HTML representing the current version of the web
	 *            site.
	 * @param path
	 *            String The location on which the two differences files are
	 *            written.
	 * @param lastV
	 *            String the name of the last version of HTML.
	 * @param currentV
	 *            String The name that the current version of HTML will have
	 *            when it will be stored locally;
	 * 
	 * 
	 * 
	 *            NTS: Version of HTML from the current check
	 *            (currenthVersionHtml) is given as string, but for the one that
	 *            is in memory the path is given, although the string from it
	 *            already has been read by this point and it was established
	 *            that the two strings are different, so we're going to read it
	 *            a second time here. The application could lead to needing this
	 *            implementation again, but for the moment should try to create
	 *            another method similar that just creates diffs from two
	 *            strings.
	 * 
	 *            NTS: Currently nor printing in diff files the line where the
	 *            difference exists. Easyer for testing at the moment, but in
	 *            the future this will be crucial necessary.
	 * 
	 *            NTS: PROBLEM! The umlauts are treaded as differences. Maybe
	 *            try to encode the strings and then compare them. Or encode
	 *            them at the begining of the application and pass them as
	 *            encoded.
	 */
	public void createDifHtml(String pathLastVersionHtml,
			String currenthVersionHtml, String path, String lastV,
			String currentV) {

		String htmlToWrite = null;

		// convert html to lines
		List<String> lastVersionHtmlLines = htmlFileToLines(pathLastVersionHtml);
		List<String> currentVersionHtmlLines = stringHtmlToLines(currenthVersionHtml);

		Patch patch = DiffUtils.diff(lastVersionHtmlLines,
				currentVersionHtmlLines);

		htmlToWrite = "";
		for (Delta delta : patch.getDeltas()) {
			String revisedText = delta.getRevised().toString();
			String content = revisedText.substring(
					revisedText.indexOf(" [") + 2, revisedText.indexOf("]]"));
			htmlToWrite += content + "\n";
		}
		htmlManager
				.saveHtml(path, "diff" + lastV + currentV + "x", htmlToWrite);

		Patch patch2 = DiffUtils.diff(currentVersionHtmlLines,
				lastVersionHtmlLines);

		htmlToWrite = "";
		for (Delta delta2 : patch2.getDeltas()) {
			String revisedText2 = delta2.getRevised().toString();
			String content2 = revisedText2.substring(
					revisedText2.indexOf(" [") + 2, revisedText2.indexOf("]]"));
			htmlToWrite += content2 + "\n";
		}
		htmlManager
				.saveHtml(path, "diff" + currentV + lastV + "x", htmlToWrite);
	}

	/**
	 * Returns the HTML given as String as a List where each line is in a
	 * different node.
	 * 
	 * @param html
	 *            String The HTML to parse.
	 * @return a List where each node represents a line from the HTML code.
	 */
	private List<String> stringHtmlToLines(String html) {
		List<String> content = new ArrayList<String>();
		String lines[] = html.split("\\r?\\n");

		for (int i = 0; i < lines.length; i++) {
			content.add(lines[i]);
		}
		return content;
	}

	/**
	 * Returns the HTML from the given path as a List with each line in a
	 * different node.
	 * 
	 * @param path
	 *            The location of the HTML file to read from.
	 * @return a List where each node represents a line from the HTML code.
	 */
	private List<String> htmlFileToLines(String path) {
		List<String> content = new ArrayList<String>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(path));
			String str;
			while ((str = in.readLine()) != null) {
				content.add(str);

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

}

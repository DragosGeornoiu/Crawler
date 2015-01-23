package ro.project.comparator;

import java.io.File;

public class WebsiteVersionApplication {
	private FileManager fileManager;
	private HtmlManager htmlManager;
	private HtmlComparator htmlComparator;

	public WebsiteVersionApplication() {
		fileManager = new FileManager();
		htmlManager = new HtmlManager();
		htmlComparator = new HtmlComparator();
	}

	public void checkHtmlOfUrl(String url) {

		String html = htmlManager.getHtmlFromUrl(url);

		String path = fileManager.createPathFromUrl(url);
		File file = new File(path);

		if (!file.exists()) {
			System.out.println("No previous version of " + url
					+ ". Saving as version 1...");
			file.mkdirs();

			String version = "V1";
			fileManager.createFileInPath(path, version);
			htmlManager.saveHtml(path, version, html);

		} else {
			/*
			 * If it exists, get the name of last version, create the next
			 * version and put the HTML in it, but before that make the HTML
			 * with the differences between he two, because the newest version
			 * has to be the last file modified.
			 */
			System.out.println("A previous version of this  " + url
					+ " exists.");
			File lastVersion = fileManager.getLastModifiedFile(path);
			String lastVersionHtml = htmlManager.getHtmlFromFile(lastVersion
					.toString());

			/*
			 * Check if the two HTML strings are different, if not, do nothing,
			 * if so, get the differences, and store them.
			 */
			if (htmlManager.isDifferent(lastVersionHtml, html)) {
				System.out.println("The version stored is NOT up to date."
						+ " Saving the current version of the website...");
				int length = lastVersion.toString().length();
				int lastVtemp = Integer.parseInt(lastVersion.toString()
						.substring(lastVersion.toString().lastIndexOf("V") + 1,
								lastVersion.toString().lastIndexOf(".html")));
				String lastV = "V" + Integer.toString(lastVtemp);
				String currentV = "V" + Integer.toString(lastVtemp + 1);

				htmlComparator.createDifHtml(lastVersion.toString(), html,
						path, lastV, currentV);
				fileManager.createFileInPath(path, currentV);
				htmlManager.saveHtml(path, currentV, html);
			} else {
				System.out.println("The version stored is up to date...");
			}
		}
	}
}
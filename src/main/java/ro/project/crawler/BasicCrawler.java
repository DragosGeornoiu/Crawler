package ro.project.crawler;

import java.util.regex.Pattern;

import ro.project.comparator.WebsiteVersionApplication;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Dragos
 * 
 *         BasicCrawler class is responsible for the actual crawling for the
 *         given website.
 * 
 *         NTS: Used two static members. Try to avoid using static, should test
 *         with other crawlers also.
 * 
 *         NTS: Check for other filters.
 *
 */
public class BasicCrawler extends WebCrawler {

	WebsiteVersionApplication websiteVersionApp = null;

	public static String websiteName = "";

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz|ico))$");

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {

		if (websiteVersionApp == null) {
			websiteVersionApp = new WebsiteVersionApplication();
		}

		String url = page.getWebURL().getURL();
		websiteVersionApp.checkHtmlOfUrl(url);

		// int docid = page.getWebURL().getDocid();
		/*
		 * String domain = page.getWebURL().getDomain(); String path =
		 * page.getWebURL().getPath(); String subDomain =
		 * page.getWebURL().getSubDomain(); String parentUrl =
		 * page.getWebURL().getParentUrl(); String anchor =
		 * page.getWebURL().getAnchor();
		 */

		/*
		 * System.out.println("Docid: " + docid); System.out.println("URL: " +
		 * url); System.out.println("Domain: '" + domain + "'");
		 * System.out.println("Sub-domain: '" + subDomain + "'");
		 * System.out.println("Path: '" + path + "'");
		 * System.out.println("Parent page: " + parentUrl);
		 * System.out.println("Anchor text: " + anchor);
		 * System.out.println("--------------");
		 */

		/*
		 * if (page.getParseData() instanceof HtmlParseData) { HtmlParseData
		 * htmlParseData = (HtmlParseData) page.getParseData(); String text =
		 * htmlParseData.getText(); String html = htmlParseData.getHtml();
		 * Set<WebURL> links = (Set<WebURL>) htmlParseData.getOutgoingUrls();
		 * 
		 * System.out.println("Text length: " + text.length());
		 * System.out.println("Html length: " + html.length());
		 * System.out.println("Number of outgoing links: " + links.size()); }
		 * 
		 * Header[] responseHeaders = page.getFetchResponseHeaders(); if
		 * (responseHeaders != null) { System.out.println("Response headers:");
		 * for (Header header : responseHeaders) { System.out.println("\t" +
		 * header.getName() + ": " + header.getValue()); } }
		 * 
		 * System.out.println("=============");
		 */
	}

	/**
	 * Implemented to specify whether the given URL should be crawled or not.
	 * 
	 * Also checks if the 'to visit' URL starts with the given website to crawl,
	 * so if we had to crawl http://website.com, we won't go crawling outer
	 * websites that the website.com link to, like http://otherwebsite.com.
	 * 
	 * NTS: Not sure if this could be a problem, but I think website.com can
	 * have something like blog.website.com so if that is the case, think of a
	 * way to also crawl the second one if it is linked to. Currently you are
	 * going to skip it, since it doesn't start with website.com.
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		/*
		 * if (app != null) { app = null; }
		 */

		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches()
				&& href.startsWith(websiteName.toLowerCase());
	}

}
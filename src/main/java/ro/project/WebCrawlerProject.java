package ro.project;

import java.io.IOException;

import ro.project.crawler.BasicCrawlController;

public class WebCrawlerProject {
	public static void main(String[] args) throws IOException {
		try {
			BasicCrawlController crawler = new BasicCrawlController();
			crawler.startCrawler("http://localhost:8080/SpringTest2/");
			crawler.startCrawler("http://www.eco-smile.ro/");
			crawler.startCrawler("http://www.thedentistsoffice.com/");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
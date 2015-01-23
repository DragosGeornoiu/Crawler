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
/*
 * public static void getImgAlt() throws IOException { Document doc =
 * Jsoup.connect("http://localhost:8080/SpringTest2/login").get(); Elements img
 * = doc.select("img"); for(Element i : img) System.out.println(i.attr("alt"));
 * 
 * }
 * 
 * public static void getStrongTag() throws IOException { Document doc =
 * Jsoup.connect("http://localhost:8080/SpringTest2/login").get(); Elements
 * strong = doc.select("strong"); for(Element s : strong)
 * System.out.println(s.text());
 * 
 * }
 * 
 * public static void getBTag() throws IOException { Document doc =
 * Jsoup.connect("http://localhost:8080/SpringTest2/login").get(); Elements b =
 * doc.select("b"); for(Element bold : b) System.out.println(bold.text());
 * 
 * }
 * 
 * public static void getH1() throws IOException { Document doc =
 * Jsoup.connect("http://localhost:8080/SpringTest2/login").get(); Elements h1 =
 * doc.select("h1"); for(Element h : h1) System.out.println(h.text()); }
 * 
 * public static void getH2() throws IOException { Document doc =
 * Jsoup.connect("http://localhost:8080/SpringTest2/login").get(); Elements h2 =
 * doc.select("h2"); for(Element h : h2) System.out.println(h.text()); }
 * 
 * public static void getH3() throws IOException { Document doc =
 * Jsoup.connect("http://localhost:8080/SpringTest2/login").get(); Elements h3 =
 * doc.select("h3"); for(Element h : h3) System.out.println(h.text()); }
 * 
 * public static void getMetaTags() throws IOException { Document doc =
 * Jsoup.connect("http://localhost:8080/SpringTest2/login").get(); Elements
 * eMETA = doc.select("META"); for(Element e : eMETA) { String name =
 * e.attr("name"); if(name.isEmpty()) { System.out.println("charset: " +
 * e.attr("charset")); } else { System.out.println("name: " + name +" content: "
 * + e.attr("content")); } } }
 * 
 * public static void getTitleTag() throws IOException { Document doc =
 * Jsoup.connect("http://localhost:8080/SpringTest2/login").get(); Elements
 * titles = doc.select("title"); for(Element t : titles)
 * System.out.println(t.text()); }
 * 
 * public static void getParagraphTags() throws IOException { Document doc =
 * Jsoup.connect("http://localhost:8080/SpringTest2/login").get(); Elements
 * paragraphs = doc.select("p"); for(Element p : paragraphs)
 * System.out.println(p.text()); }
 * 
 * public static void getHtml() throws IOException { Connection.Response html =
 * Jsoup.connect("http://localhost:8080/SpringTest2/login").execute();
 * System.out.println(html.body()); }
 */
package ro.project.crawler;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author Dragos
 *
 *         Extended CrawlController just to reduce the sleep times from 10 to 1.
 * 
 *         NTS: Check if it could lead to harmful behavior.
 * 
 */
public class MyCrawlController extends CrawlController {

	public MyCrawlController(CrawlConfig config, PageFetcher pageFetcher,
			RobotstxtServer robotstxtServer) throws Exception {
		super(config, pageFetcher, robotstxtServer);
	}

	@Override
	protected <T extends WebCrawler> void start(final Class<T> _c,
			final int numberOfCrawlers, boolean isBlocking) {
		try {
			crawlersLocalData.clear();
			final List<Thread> threads = new ArrayList<Thread>();
			final List<T> crawlers = new ArrayList<T>();

			for (int i = 1; i <= numberOfCrawlers; i++) {
				T crawler = _c.newInstance();
				Thread thread = new Thread(crawler, "Crawler " + i);
				crawler.setThread(thread);
				crawler.init(i, this);
				thread.start();
				crawlers.add(crawler);
				threads.add(thread);
			}

			final CrawlController controller = this;

			Thread monitorThread = new Thread(new Runnable() {

				public void run() {
					try {
						synchronized (waitingLock) {

							while (true) {
								sleep(1);
								boolean someoneIsWorking = false;
								for (int i = 0; i < threads.size(); i++) {
									Thread thread = threads.get(i);
									if (!thread.isAlive()) {
										if (!shuttingDown) {
											T crawler = _c.newInstance();
											thread = new Thread(crawler,
													"Crawler " + (i + 1));
											threads.remove(i);
											threads.add(i, thread);
											crawler.setThread(thread);
											crawler.init(i + 1, controller);
											thread.start();
											crawlers.remove(i);
											crawlers.add(i, crawler);
										}
									} else if (crawlers.get(i)
											.isNotWaitingForNewURLs()) {
										someoneIsWorking = true;
									}
								}
								if (!someoneIsWorking) {
									/*
									 * Make sure again that none of the threads
									 * are alive.
									 */
									sleep(1);

									someoneIsWorking = false;
									for (int i = 0; i < threads.size(); i++) {
										Thread thread = threads.get(i);
										if (thread.isAlive()
												&& crawlers
														.get(i)
														.isNotWaitingForNewURLs()) {
											someoneIsWorking = true;
										}
									}
									if (!someoneIsWorking) {
										if (!shuttingDown) {
											long queueLength = frontier
													.getQueueLength();
											if (queueLength > 0) {
												continue;
											}
											sleep(1);
											queueLength = frontier
													.getQueueLength();
											if (queueLength > 0) {
												continue;
											}
										}

										/*
										 * At this step, frontier notifies the
										 * threads that were waiting for new
										 * URLs and they should stop
										 */
										frontier.finish();
										for (T crawler : crawlers) {
											crawler.onBeforeExit();
											crawlersLocalData.add(crawler
													.getMyLocalData());
										}

										sleep(1);

										frontier.close();
										docIdServer.close();
										pageFetcher.shutDown();

										finished = true;
										waitingLock.notifyAll();

										return;
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			monitorThread.start();

			if (isBlocking) {
				waitUntilFinish();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

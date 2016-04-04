import java.util.*;
import java.io.*;

public class Main
{
	public static Collection<File> chain;
	
	//main thread starts ==================================
	public static void main(String[] args)
	{
		System.out.println("Starting...");
		(new Thread(new SearchFiles())).start();
		chain = new ArrayList<File>();

		
		//dir paths ========================================
		String path1 = "E:\\Waqas";
		String path2 = "C:\\Android";
		String path3 = "E:\\Software & Games";
		
		//create thread for each path ====================================
		Thread crawlerThread1 = new Thread(new CrawlDirs(path1, chain));
		Thread crawlerThread2 = new Thread(new CrawlDirs(path2, chain));
		Thread crawlerThread3 = new Thread(new CrawlDirs(path3, chain));
		Thread indexThread = new Thread(new ListDirectoriesFiles(chain));
		
		//start threads and put main to wait while all threads stop ============================
		crawlerThread1.start();
		try {
			crawlerThread1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		crawlerThread2.start();
		try {
			crawlerThread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		crawlerThread3.start();
		try {
			crawlerThread3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		indexThread.start();
		try {
			indexThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
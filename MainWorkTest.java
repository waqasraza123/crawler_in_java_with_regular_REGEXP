import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.*;
import java.io.*;

public class MainWorkTest extends TestCase
{

  public MainWorkTest( String testName )
  {
    super( testName );
  }
  public static Test suite()
  {
    return new TestSuite( AppTest.class );
  }
  public void testCrawler() throws InterruptedException
  {
    Collection<File> chain = new ArrayList<File>();
    Thread crawlerThread1 = new Thread(
      new CrawlDirs("E:\\", 
        chain));

    crawlerThread1.start();
    crawlerThread1.join();
    assertFalse(chain.size() == 0);
  }
}

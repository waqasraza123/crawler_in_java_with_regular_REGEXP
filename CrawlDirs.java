import java.util.*;
import java.io.*;

public class CrawlDirs implements Runnable
{
  public static Collection<File> chain = new ArrayList<File>();
  public static String dirPath;
  
  //default constructor=========================================
  public CrawlDirs(String givenDirPath, Collection<File> givenChain)
  {
    dirPath = givenDirPath;
    chain = givenChain;
  }
  
  //since multiple threads are crawling and adding files from the specified dirs(3)
  //so we have to make this method synchronized
  public static synchronized void addTree(File file, Collection<File> chain) 
  {
    File[] children = file.listFiles();
    if (children != null) 
    {
      for (File child : children) 
      {
        addTree(child, chain);
      }
    }
    else
    {
      chain.add(file);
    }
  }
  public static Collection<File> getChain()
  {
    return chain;
  }

  //thread ==============================
  public void run() 
  {
   addTree(new File(dirPath),chain);
  }
}

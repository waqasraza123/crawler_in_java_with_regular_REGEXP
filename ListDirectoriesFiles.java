import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;

public class ListDirectoriesFiles implements Runnable
{
	private BufferedWriter out;
	private Iterator itr;
	private String fileName, filePath;
	private BasicFileAttributes fileAttr;
	private DosFileAttributes dosAttr;
	private FileOwnerAttributeView ownerAttributeView;
	private UserPrincipal owner;	
	public static Collection<File> chain = new ArrayList<File>();
	
	//default constructor ========================
	public ListDirectoriesFiles(Collection<File> givenChain)
	  {
	    chain = givenChain;
	  }

	//thread
	//write files names and paths to the log file ================================
	public void run() 
	{
	try 
    {
      out = new BufferedWriter(new FileWriter("log.txt"));

      itr = chain.iterator();
      while(itr.hasNext())
      {
        File file_ = (File) itr.next();

        fileName = file_.getName();
        filePath = file_.getPath();
        
        fileAttr = Files.readAttributes(Paths.get(filePath),
          BasicFileAttributes.class);
        dosAttr = Files.readAttributes(Paths.get(filePath),
          DosFileAttributes.class);
        ownerAttributeView = Files.getFileAttributeView(
          Paths.get(filePath), FileOwnerAttributeView.class);
        owner = ownerAttributeView.getOwner();
        
        out.write(
          fileName + " @ " + filePath + " @ " + 
          fileAttr.size() + "," + fileAttr.creationTime() + "," + 
          fileAttr.lastModifiedTime() + "," + dosAttr.isReadOnly() + "," +
          dosAttr.isHidden() + "," + dosAttr.isArchive() + "," + owner.getName()
        );
        out.newLine();
        
      }
      out.close();
    }
    catch(IOException ex)
    {
      ex.printStackTrace();
    }
  }
}

import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchFiles implements Runnable
{
	
	//declare class variables as private
	private Scanner in;
	private String s;
	private Set set;
	private Iterator i;
	private Entry curr;
	private String filename;
	private List<String> details;
	private String filePath;
	private Pattern userPattern;
	private Matcher nameMatch;
	private File file;
	private BufferedReader br;
	private Matcher contentMatch;
	private BufferedReader reader;
	private Map<String, List<String>> listOfFiles;
	
	
  //search from index ====================================
  public void IndexSearch(Map files)
  {
    in = new Scanner(System.in);
      
    while(true)
    {
      System.out.println("Enter file name to search: ");
      s = in.nextLine();
      
      set = files.entrySet();
      i = set.iterator();
      
      System.out.println("Searching the file please hang on....");
      //search file names from the log ==================================
      while(i.hasNext()) 
      {

        curr = (Entry) i.next();
        filename = (String) curr.getKey();
        details = (ArrayList<String>) curr.getValue();
        filePath = (String) details.get(0);
        userPattern = Pattern.compile(s);

        nameMatch = userPattern.matcher(filename);
        if (nameMatch.find())
        {
          System.out.println("[FILE] found via regex: " + filePath);
        }
        else
        {
          if (s.equalsIgnoreCase(filename))
          {
            System.out.println("[FILE] found: " + filePath);
          }
        }

        file = new File(filePath);
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String line = null;
        try {
			while ((line = br.readLine()) != null) 
			{
			  contentMatch = userPattern.matcher(line);
			  if (contentMatch.find())
			  {
			    System.out.printf("[%s] found in file via regex: %s\n", s, filePath);
			  }
			  else
			  {
			    if (line.contains(s))
			    {
			      System.out.printf("[%s] found in file: %s\n", s, filePath);
			    }
			  }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }   
    }
  }
  
  //reads the log file to separate diff files ===========================
  public void run()
  {
    listOfFiles = new HashMap<>();

    try
    {
      reader = new BufferedReader(new FileReader("log.txt"));
      String line;

      while((line = reader.readLine()) != null)
      {
        List<String> fileDetails = new ArrayList<>();
        
        fileDetails.add(line.split(" @ ")[1]);
        try {
        	fileDetails.add(line.split(" @ ")[2]);
		} catch (Exception e) {
			System.out.println("out of bounds");
		}
        

        listOfFiles.put(line.split(" @ ")[0], fileDetails);
      }

      IndexSearch(listOfFiles);
      reader.close();
    }
    catch (FileNotFoundException FNFexc)
    {
      System.out.println("Error reading log file.");
      FNFexc.printStackTrace();
      
    }
    catch (IOException IOexc)
    {
      System.out.println("Error reading log file.");
      IOexc.printStackTrace();
    }
  }
}
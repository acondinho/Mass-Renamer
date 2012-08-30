package Main;
import javax.swing.*;
import java.io.*;

public class MassRenamer 
{
	public static void main(String[] args) 
	{
		//Get Folder Path
		String folderPath = getFldrPath();
		
		//Count number of files
		int numFiles = getFleNums(folderPath);
		
		//Ask user for split
		System.out.println("Please enter what you'd like to split on (\" - \" is the default)");
		String splitInput = " - ";
		try 
		{
			BufferedReader bfRead = new BufferedReader(new InputStreamReader(System.in));
			splitInput = bfRead.readLine();
			if(splitInput.isEmpty()) {
				splitInput = " - ";
			}
		} catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
		
		//Ask for the prefix/suffix (alpha prefix only)
		System.out.println("Please enter the prefix you'd like to append to the files");
		String prefixInput = "";
		try 
		{
			BufferedReader bfRead = new BufferedReader(new InputStreamReader(System.in));
			prefixInput = bfRead.readLine();
		} catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}		
		
		//Pull namelist into main
		String[] origNames = getFileNameArray(folderPath);
		
		//Main event loop
		for (int i = 0; i < numFiles; i++)
		{
			//Separate the filename using split
			String seperatedOrig = getRegExpStr(origNames[i], splitInput);
			
			//Append the new prefix to the separated filename
			String newFleName = createFleName(prefixInput, seperatedOrig);
			
			saveFleName(folderPath, origNames[i], newFleName);
		}

	}
	
	public static String getFldrPath()
	{
		//Open a folder directory to select dir
		JFrame frame = new JFrame();
		String fldPath = "";
		JFileChooser folderFinder = new JFileChooser("C:\\");
		folderFinder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int returnval = folderFinder.showOpenDialog(frame);
		if (returnval == JFileChooser.APPROVE_OPTION)
		{

			fldPath = folderFinder.getSelectedFile().getAbsolutePath();
		}
		return fldPath;
	}
	
	public static int getFleNums(String path)
	{			
		//Add support for different extensions later, default mp3
		//Count number of files in target dir
		int numFiles = 0;
		
		String[] myFiles = getFileNameArray(path);
		numFiles = myFiles.length;
		
		return numFiles;
	}
	
	public static String[] getFileNameArray (String path)
	{	
		File dir = new File(path);
		
		//Create file Filter
		FilenameFilter extFilt = new FilenameFilter()
		{
			public boolean accept(File dir, String fileName)
			{
				return fileName.endsWith(".mp3");
			}
		};
		
		String[] myFiles = dir.list(extFilt);
		
		return myFiles;
	}
	
	public static String getRegExpStr(String origName, String splitInput)
	{
		//Uses split to seperate and return the part of the filename to keep (ie song title)
		String seperated = "";
		String[] pieces = origName.split(splitInput);
		seperated = pieces[1];
		
		return seperated;
	}
	
	public static String createFleName(String prefix, String orig)
	{
		//Appends pre/suffix to seperated filename and returns new filename
		String newFleName = "";
		StringBuffer appender = new StringBuffer();
		
		//Append strings
		appender.append(prefix);
		appender.append(orig);
		
		newFleName = appender.toString();
		
		return newFleName;
	}
	
	public static void saveFleName(String path, String oldFleName, String newFleName)
	{
		//Saves the file name
        File file = new File(path + "/" + oldFleName);

        if (!file.exists() || file.isDirectory()) {
            System.out.println("File does not exist: " + file);
            return;
        }

        File newFile = new File(path + "/" + newFleName);

        //Rename
        if (file.renameTo(newFile)) {
            System.out.println("File has been renamed.");
        } else {
            System.out.println("Error renaming file");
        }
		
	}
}

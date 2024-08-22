import java.io.*;
import java.util.Scanner;

public class ArticleData
{
	private String[] words;
	
	public ArticleData(String fN)
	{
		try
		{
			Scanner file = new Scanner(new File(fN));
			String line = file.nextLine().strip().toLowerCase();
			words = line.split(" ");
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Error - file not found");
		}
	}
	
	public String[] getWords()
	{
		return words;
	}

	
}

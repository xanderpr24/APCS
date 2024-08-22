import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordDatabase
{
	private WordArray angerArray = new WordArray("anger", "negative");
	private WordArray anticipationArray = new WordArray("anticipation", "negative");
	private WordArray disgustArray = new WordArray("disgust", "negative");
	private WordArray fearArray = new WordArray("fear", "negative");
	private WordArray joyArray = new WordArray("joy", "positive");
	private WordArray sadnessArray = new WordArray("sadness", "negative");
	private WordArray surpriseArray = new WordArray("surprise", "positive");
	private WordArray trustArray = new WordArray("trust", "positive");
	private WordArray missingArray = new WordArray("missing", "negative");
	
	public WordDatabase(String fN)
	{
		try
		{
			Scanner file = new Scanner(new File(fN));
			file.nextLine();
			while (file.hasNextLine())
			{
				String[] lineData = file.nextLine().split("\t");
				Word tempWord = new Word(lineData[0], Double.parseDouble(lineData[2]));
				String emotion = lineData[1];
				switch (emotion) {
					
				case "anger": angerArray.addWord(tempWord);
				break;
				
				case "anticipation": anticipationArray.addWord(tempWord);
				break;
				 
				case "disgust": disgustArray.addWord(tempWord);
				break;
				
				case "fear": fearArray.addWord(tempWord);
				break;
				
				case "joy": joyArray.addWord(tempWord);
				break;
				
				case "sadness": sadnessArray.addWord(tempWord);
				break;
				
				case "surprise": surpriseArray.addWord(tempWord);
				break;
				
				case "trust": trustArray.addWord(tempWord);
				break;
				
				default: missingArray.addWord(tempWord);
				break;
				
				}
			
				
			}
			file.close();
		}
		
		catch (FileNotFoundException ex)
		{
			System.out.println("?");
		}
	}

	public WordArray getAngerArray() {
		return angerArray;
	}

	public WordArray getAnticipationArray() {
		return anticipationArray;
	}

	public WordArray getDisgustArray() {
		return disgustArray;
	}

	public WordArray getFearArray() {
		return fearArray;
	}

	public WordArray getJoyArray() {
		return joyArray;
	}

	public WordArray getSadnessArray() {
		return sadnessArray;
	}

	public WordArray getSurpriseArray() {
		return surpriseArray;
	}

	public WordArray getTrustArray() {
		return trustArray;
	}

	public WordArray getMissingArray() {
		return missingArray;
	}
	
	public void displayData()
	{
		angerArray.displayData();
	}
	

}

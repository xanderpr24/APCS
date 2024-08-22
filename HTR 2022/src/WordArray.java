import java.util.*;

public class WordArray
{
	private ArrayList<Word> array = new ArrayList<Word>();
	private String emotion;
	private String sentiment;
	
	
	public WordArray(String e, String s)
	{
		emotion = e;
		sentiment = s;
	}


	public ArrayList<Word> getArray() {
		return array;
	}


	public void setArray(ArrayList<Word> array) {
		this.array = array;
	}


	public String getEmotion() {
		return emotion;
	}


	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}


	public String getSentiment() {
		return sentiment;
	}


	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	
	public void addWord(Word w)
	{
		array.add(w);
	}
	
	public void displayData()
	{
		for (Word w: array)
		{
			System.out.println(w);
		}
	}
	
	
	
}

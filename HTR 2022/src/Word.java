
public class Word
{
	private String word;
	private double coefficient;
	
	public Word(String w, double c)
	{
		word = w;
		coefficient = c;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}
	
	public String toString()
	{
		return word + ", " + coefficient;
	}
	
	
	
}

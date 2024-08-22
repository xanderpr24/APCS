import py4j.GatewayServer;

public class LexiconDriver {

	public static void main(String[] args)
	{
		
		
		LexiconDriver app = new LexiconDriver();
	    // app is now the gateway.entry_point
	    GatewayServer server = new GatewayServer(app);
	    server.start();
		
	}
	
	public static double calculateCoefficients(WordArray array, ArticleData article)
	{
		
		
		double val;
		double sum = 0;
		int count = article.getWords().length;
		
		for (String s: article.getWords())
		{
			for ( Word w: array.getArray())
			{
				if (s.equals(w.getWord()))
				{
					sum += w.getCoefficient();
					break;
				}
	
					
			}
		}
		
		val = sum/count*100;
		return val;
	}
	

		

	
	public double[] main2(String fN)
	{
		WordDatabase database = new WordDatabase("emotionLexicon.txt");
		ArticleData article = new ArticleData(fN);
		double[] coefficients = new double[8];
			
		coefficients[0] = calculateCoefficients(database.getAngerArray(), article);
		coefficients[1] = calculateCoefficients(database.getAnticipationArray(), article);
		coefficients[2] = calculateCoefficients(database.getDisgustArray(), article);
		coefficients[3] = calculateCoefficients(database.getFearArray(), article);
		coefficients[4] = calculateCoefficients(database.getJoyArray(), article);
		coefficients[5] = calculateCoefficients(database.getSadnessArray(), article);
		coefficients[6] = calculateCoefficients(database.getSurpriseArray(), article);
		coefficients[7] = calculateCoefficients(database.getTrustArray(), article);
		
		return coefficients;
	}

}

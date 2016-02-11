package rivers.yeah.research.yelda.mbolda;

import rivers.yeah.research.yelda.Document;

public class MBODocument extends Document{
	public int fowardNum;
	public int commentNum;
	public int likeNum;
	
	public MBODocument(int[] words, String rawStr, String domain,
			int forwardNum, int commentNum, int likeNum) {
		super(words, rawStr, domain);
		this.fowardNum = forwardNum;
		this.commentNum = commentNum;
		this.likeNum = likeNum;
	}
}

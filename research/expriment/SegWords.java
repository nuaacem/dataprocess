package rivers.yeah.research.expriment;

import java.io.IOException;

import org.junit.Test;

import rivers.yeah.research.datacollect.weibo.service.CommentService;
import rivers.yeah.research.wordseg.CallNLPIR;

public class SegWords {

	@Test
	public void segWords() throws IOException {
		CallNLPIR.segWords("segwords", "22.txt", true, false, 0);
	}
	
	@Test
	public void outputConcatComments() throws IOException {
		String inPath = "./cfg/comment_crawl_list.txt";
		String outPath = "./segwords/input/0608.txt";
		CommentService.getConcatComments(inPath,outPath);
		CallNLPIR.segWords("segwords", "0608.txt", true, true, 0);
	}
}

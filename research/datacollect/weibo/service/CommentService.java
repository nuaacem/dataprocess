package rivers.yeah.research.datacollect.weibo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import rivers.yeah.research.datacollect.weibo.dao.CommentDao;
import rivers.yeah.research.datacollect.weibo.model.Comment;


public class CommentService {
	
	private static final CommentDao commentDao = new CommentDao();
	
	public static int addComment(Comment comment) {
		return commentDao.saveOrUpdate(comment);
	}
	
	public static List<Comment> getCommentsByMid(String mid) {
		return commentDao.getCommentsByMid(mid);
	}
	
	public static void getConcatComments(String inPath, String outPath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(inPath)));
		FileWriter fw = new FileWriter(new File(outPath));
		String line = br.readLine();
		List<Comment> comments = null;
		String domain = "";
		while (line != null && !"".equals(line)) {
			comments = getCommentsByMid(line);
			domain = WeiboService.getDomainByMid(line);
			StringBuilder sb = new StringBuilder();
			for (Comment comment : comments) {
				sb.append(comment.getContent()).append(" ");
			}
			fw.write(domain + "\t" + sb.toString().trim() + "\r\n");
			line = br.readLine();
		}
		br.close();
		fw.flush();
		fw.close();
	}
}

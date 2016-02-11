package rivers.yeah.research.expriment.extractdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import org.junit.Test;

import rivers.yeah.research.wordseg.CallNLPIR;

public class ExtractData {

	@Test
	public void getDataFromDB() throws Exception {
		DBConnection con = new DBConnection();
		Connection conn = con.getConnection();
		String sql = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		OutputStreamWriter out = null;
		OutputStreamWriter out2 = null;

		for (int i = 11; i < 26; i++) {
			out = new OutputStreamWriter(new FileOutputStream(new File(
					"files/10-27/" + (i - 10) + ".txt")), "UTF-8");
			out2 = new OutputStreamWriter(new FileOutputStream(new File(
					"files/10-27/property/" + (i - 10) + ".txt")), "UTF-8");
			sql = "select content,domain,forward_num,comment_num,like_num from weibo,domain"
					+ " where pub_time > '2015-09-"
					+ i
					+ "' and pub_time < '2015-09-"
					+ (i + 1)
					+ "' and weibo.domain_id = domain.domain_id;";
			prep = conn.prepareStatement(sql);
			prep.execute();
			rs = prep.getResultSet();
			rs.beforeFirst();

			while (rs.next()) {
				out.write(rs.getString(1) + "\r\n");
				out2.write(rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getString(5) + "\r\n");
			}
			out.flush();
			out2.flush();
			out.close();
			out2.close();
			prep.close();
			rs.close();
		}
	}

	@Test
	public void combineDataProperty() throws Exception {
		BufferedReader br = null;
		BufferedReader br2 = null;
		BufferedWriter bw = null;
		String line = null;
		String line2 = null;
		for (int i = 1; i < 15; i++) {
			br = new BufferedReader(new FileReader(new File(
					"files/10-27/output/" + i + ".txt")));
			br2 = new BufferedReader(new FileReader(new File(
					"files/10-27/property/" + i + ".txt")));
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File("files/10-27/final/" + i
							+ ".txt")), "UTF-8"));
			while ((line = br.readLine()) != null
					&& (line2 = br2.readLine()) != null) {
				if (!line.trim().equals("")) {
					bw.write(line2 + "\t" + line);
					bw.newLine();
				}
			}
			br2.close();
			br.close();
			bw.flush();
			bw.close();
		}
	}

	@Test
	public void segWordsOfEachFiles() throws IOException {
		for (int i = 1; i < 15; i++) {
			CallNLPIR.segWords("files/10-27", i + ".txt", true, false, 0);
		}

	}

	public static final int BUFSIZE = 1024 * 8;

	public static void mergeFiles(String outFile) {
		FileChannel outChannel = null;
		try {
			outChannel = new FileOutputStream(outFile).getChannel();
			for (int i = 1; i < 15; ++i) {
				String f = "files/10-27/final/" + i + ".txt";
				FileChannel fc = new FileInputStream(f).getChannel();
				ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
				while (fc.read(bb) != -1) {
					bb.flip();
					outChannel.write(bb);
					bb.clear();
				}
				fc.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (outChannel != null) {
					outChannel.close();
				}
			} catch (IOException ignore) {
			}
		}
	}

	@Test
	public void concatFiles() {
		mergeFiles("files/10-27/final.txt");
	}
}

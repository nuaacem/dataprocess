package rivers.yeah.research.yelda.mbolda;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import rivers.yeah.research.yelda.Corpus;
import rivers.yeah.research.yelda.Document;
import rivers.yeah.research.yelda.Vocabulary;

public class MBOCorpus extends Corpus{
	
	public MBOCorpus() {
		localVoc = new MBOVocabulary();
		M = 0;
		V = 0;
		docs = new ArrayList<Document>();
		lid2gid = null;
		globalVoc = null;
	}
	
	public MBOCorpus(int M, Vocabulary globalVoc) {
		localVoc = new MBOVocabulary();	
		this.M = M;
		this.V = 0;
		docs = new ArrayList<Document>(M);	
		
		this.globalVoc = globalVoc;
		lid2gid = new HashMap<Integer, Integer>();
	}
	
	/**
	 * set the document at the index idx if idx is greater than 0 and less than M
	 * @param str string contains doc
	 * @param idx index in the document array
	 */
	public void setDoc(String line) {
		
		//文档加上domain 属性
		String domain = null;
		String forward = null;
		String comment = null;
		String like = null;
		int forwardNum = 0;
		int commentNum = 0;
		int likeNum = 0;
		
		StringTokenizer st = new StringTokenizer(line, " \t\r\n");
		domain = st.nextToken();
		forward = st.nextToken();
		comment = st.nextToken();
		like = st.nextToken();
		if (forward != null) {
			forwardNum = Integer.parseInt(forward);
		}
		if (comment != null) {
			commentNum = Integer.parseInt(comment);
		}
		if (like != null) {
			likeNum = Integer.parseInt(like);
		}
		
		int nid = st.countTokens();
		int[] ids = new int[nid];
		for (int i = 0; i < nid; i++) {
			String word = st.nextToken();
			int _id = localVoc.id2word.size();
			if (localVoc.contains(word)) {
				_id = localVoc.getId(word);
			}
			if (globalVoc != null){
				//get the global id					
				Integer id = globalVoc.word2id.get(word);
				//System.out.println(id);
				
				if (id != null){
					localVoc.addWord(word);					
				}
				else {
					//not in global dictionary
					//do nothing currently
					localVoc.addWord(word);					
					globalVoc.addWord(word);
				}
				lid2gid.put(_id, id);
				ids[i] = _id;
			}
			else {
				localVoc.addWord(word);
				ids[i] = _id;
			}			
		}
		MBODocument doc = new MBODocument(ids, line, domain, forwardNum, commentNum, likeNum);
		docs.add(doc);
	}
	
	/**
	 *  read a dataset from a stream, create new dictionary
	 *  @return dataset if success and null otherwise
	 */
	public static MBOCorpus loadCorpus(String filePath) {
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "UTF-8"));
			MBOCorpus corpus = new MBOCorpus();
			String line = null;
			int ndoc = 0;
			while ((line = br.readLine()) != null) {
				corpus.setDoc(line);
				ndoc++;
			}
			corpus.M = ndoc;
			corpus.V = corpus.localVoc.id2word.size();
			return corpus;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * read a dataset from a file with a preknown vocabulary
	 * @param filename file from which we read dataset
	 * @param dict the dictionary
	 * @return dataset if success and null otherwise
	 */
	public static MBOCorpus loadCorpus(String filename, Vocabulary voc){
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), "UTF-8"));
			//read number of document
			MBOCorpus corpus = new MBOCorpus(64 , voc);
			
			String line = null;
			int ndoc = 0;
			while ((line = br.readLine()) != null) {
				corpus.setDoc(line);
				ndoc++;
			}
			corpus.M = ndoc;
			corpus.V = corpus.localVoc.id2word.size();
			return corpus;
		}
		catch (Exception e){
			System.out.println("Read Dataset Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

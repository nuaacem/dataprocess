package rivers.yeah.research.yelda.mbolda;

import java.util.ArrayList;
import java.util.List;

import rivers.yeah.research.yelda.Vocabulary;

public class MBOVocabulary extends Vocabulary{
	
	public List<Integer> id2count;
	public int size;
	
	public MBOVocabulary() {
		super();
		size = 0;
		id2count = new ArrayList<>(1024);
	}
	
	@Override
	public int addWord(String word) {
		++size;
		if (contains(word)) {
			int id = word2id.get(word);
			int count = id2count.get(id);
			id2count.set(id, ++count);
			return id;
		} else {
			int id = id2word.size();
			word2id.put(word, id);
			id2word.add(word);
			id2count.add(1);
			return id;
		}
	}
}

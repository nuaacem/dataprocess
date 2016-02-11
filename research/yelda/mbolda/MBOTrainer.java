package rivers.yeah.research.yelda.mbolda;

import java.util.List;

import rivers.yeah.research.yelda.Constants;
import rivers.yeah.research.yelda.Document;
import rivers.yeah.research.yelda.LdaUtil;
import rivers.yeah.research.yelda.olda.OldaArgs;
import rivers.yeah.research.yelda.olda.OldaTrainer;

public class MBOTrainer extends OldaTrainer {

	// public double[][] tht;
	public double[][] A;
	public double[] oalpha;
	public double Koalpha;
	public double[][] sigma;
	public double[][] omega;

	public boolean initMBOTrainer(OldaArgs option) {
		this.option = option;
		trnModel = new MBOModel();
		if (!trnModel.init(option)) {
			return false;
		}
		if (!trnModel.initFirstOldaModel()) {
			System.out.println("init olda trainer failed!");
			return false;
		}
		B = new double[option.delta][][];
		obeta = new double[trnModel.K][];
		globalVoc = trnModel.data.localVoc;
		vocWindow = new MBOVocabulary[option.delta];
		Kalpha = trnModel.K * trnModel.alpha;
		Vbeta = trnModel.V * trnModel.beta;
		Vobeta = new double[trnModel.K];
		// tht = new double[option.delta][option.ntopics];
		A = new double[option.delta][option.ntopics];
		sigma = new double[option.delta][option.ntopics];
		omega = new double[option.delta][];
		oalpha = new double[option.ntopics];
		Koalpha = Kalpha;
		return true;
	}

	public void trainMBOlda() {
		System.out.println("Train NO:" + 1 + " model");

		// train first time window
		train();
		A[0] = computeTht();
		B[0] = trnModel.phi;
		vocWindow[0] = trnModel.data.localVoc;

		int i;

		for (i = 2; i <= option.delta; i++) {
			System.out.println("Train NO:" + i + " model");
			if (!trnModel.initNewOldaModel(option, globalVoc, i)) {
				// System.out.println("train NO:" + i + " model failed!");
				return;
			}
			train();
			A[i - 1] = computeTht();
			B[i - 1] = trnModel.phi;
			vocWindow[i - 1] = trnModel.data.localVoc;
		}

		while (i <= option.timeSlices
				&& trnModel.initNewOldaModel(option, globalVoc, i)) {
			// computeSigma();
			computeOmega();
			computeOalpha();
			computeObeta();
			System.out.println("Train NO:" + i + " model");
			trainNext();
			adjustTimeWindow();
			++i;
		}
	}

	public void trainNext() {
		System.out.println("Sampling " + trnModel.niters + " iteration!");

		int lastIter = trnModel.liter;
		for (trnModel.liter = lastIter + 1; trnModel.liter < trnModel.niters; trnModel.liter++) {
			// System.out.println("Iteration " + trnModel.liter + " ...");

			for (int m = 0; m < trnModel.M; m++) {
				for (int n = 0, N = trnModel.data.docs.get(m).length; n < N; n++) {
					int topic = sampleMBOlda(m, n);
					trnModel.z[m][n] = topic;
				}
			}

			if (option.savestep > 0) {
				if (trnModel.liter % option.savestep == 0) {
					System.out.println("Saving the model at iteration "
							+ trnModel.liter + " ...");
					computeTheta();
					computePhi();
					trnModel.saveModel("model-"
							+ LdaUtil.converseZeroPad(trnModel.liter, 5));
				}
			}
		}

		System.out.println("Gibbs sampling completed!\n");
		System.out.println("Saving the final model!\n");
		computeTheta();
		computePhi();
		trnModel.liter--;
		trnModel.saveModel(trnModel.modelName);
	}

	private int sampleMBOlda(int m, int n) {
		// remove z_i from the count variables 先将这个词从计数器中抹掉
		int topic = trnModel.z[m][n];
		int w = trnModel.data.docs.get(m).words[n];
		trnModel.nw[w][topic]--;
		trnModel.nd[m][topic]--;
		trnModel.nwsum[topic]--;
		trnModel.ndsum[m]--;

		// do multinomial sampling via cumulative method: 通过多项式方法采样多项式分布
		for (int k = 0; k < trnModel.K; k++) {
			trnModel.p[k] = (trnModel.nd[m][k] + oalpha[k])
					/ (trnModel.ndsum[m] + Koalpha)
					* (trnModel.nw[w][k] + obeta[k][w])
					/ (trnModel.nwsum[k] + Vobeta[k]);
		}

		// for (int k = 0; k < trnModel.K; k++) {
		// trnModel.p[k] = (trnModel.nd[m][k] + option.alpha)
		// / (trnModel.ndsum[m] + Kalpha)
		// * (trnModel.nw[w][k] + obeta[k][w])
		// / (trnModel.nwsum[k] + Vobeta[k]);
		// }

		// cumulate multinomial parameters 累加多项式分布的参数
		for (int k = 1; k < trnModel.K; k++) {
			trnModel.p[k] += trnModel.p[k - 1];
		}

		// scaled sample because of unnormalised p[] 正则化
		double u = Math.random() * trnModel.p[trnModel.K - 1];

		for (topic = 0; topic < trnModel.K; topic++) {
			if (trnModel.p[topic] > u) {
				break;
			}
		}
		trnModel.nw[w][topic]++;
		trnModel.nd[m][topic]++;
		trnModel.nwsum[topic]++;
		trnModel.ndsum[m]++;

		return topic;
	}

	// private double[] computeTht() {
	// double[] tht = new double[trnModel.K];
	//
	// for (int j = 0; j < trnModel.K; j++) {
	// double sum = 0;
	// for (int j2 = 0; j2 < trnModel.M; j2++) {
	// sum += trnModel.theta[j2][j];
	// }
	// tht[j] = sum;
	// }
	// return tht;
	// }

	private double[] computeTht() {
		double[] ht = new double[trnModel.M];
		List<Document> docs = trnModel.data.docs;
		MBODocument doc = null;
		int h = 0;
		int i = 0;
		double sum = 0;
		for (Document document : docs) {
			doc = (MBODocument) document;
			h = doc.fowardNum * Constants.FORWARD_RATE + doc.commentNum
					* Constants.COMMENT_RATE + doc.likeNum
					* Constants.LIKE_RATE;
			ht[i++] = h;
			sum += h;
		}
		double avg = sum / trnModel.M;
		for (int j = 0; j < trnModel.M; j++) {
			ht[j] = ht[j] / avg;
		}
		double[] tht = new double[trnModel.K];

		for (int j = 0; j < trnModel.K; j++) {
			sum = 0;
			for (int j2 = 0; j2 < trnModel.M; j2++) {
				sum += trnModel.theta[j2][j] * ht[j2];
			}
			tht[j] = sum;
		}

		return tht;
	}

	// private double[] computeTht() {
	// double[] ht = new double[trnModel.M];
	// List<Document> docs = trnModel.data.docs;
	// MBODocument doc = null;
	// int h = 0;
	// int i = 0;
	// for (Document document : docs) {
	// doc = (MBODocument) document;
	// h = doc.fowardNum * Constants.FORWARD_RATE + doc.commentNum
	// * Constants.COMMENT_RATE + doc.likeNum
	// * Constants.LIKE_RATE;
	// ht[i++] = h;
	// }
	// double[] tht = new double[trnModel.K];
	//
	// double sumTh = 0;
	// for (int j = 0; j < trnModel.K; j++) {
	// double sum = 0;
	// for (int j2 = 0; j2 < trnModel.M; j2++) {
	// sum += trnModel.theta[j2][j] * ht[j2];
	// }
	// tht[j] = sum;
	// sumTh += sum;
	// }
	//
	// double factor = trnModel.K * trnModel.alpha / sumTh;
	// for (int j = 0; j < trnModel.K; j++) {
	// tht[j] = tht[j] * factor;
	// }
	// return tht;
	// }

	@Override
	protected void computeObeta() {
		for (int i = 0; i < trnModel.K; ++i) {
			obeta[i] = new double[trnModel.V];
			double sum = 0;
			for (int j = 0; j < trnModel.V; j++) {
				double tmpSum = 0;
				for (int j2 = 0; j2 < option.delta; j2++) {
					Integer k = vocWindow[j2].word2id
							.get(trnModel.data.localVoc.id2word.get(j));
					if (k != null && B[j2][i].length > k) {
						// tmpSum += omega[j2][j] * B[j2][i][k];
						tmpSum += option.omega[j2] * B[j2][i][k];
					}
				}
				if (0 == tmpSum) {
					obeta[i][j] = option.beta;
				} else {
					obeta[i][j] = tmpSum;
				}
				sum += tmpSum;
			}
			Vobeta[i] = sum;
		}
	}

	private void computeOalpha() {

		double sum = 0;
		for (int i = 0; i < trnModel.K; i++) {
			double tmpSum = 0;
			for (int j = 0; j < option.delta; j++) {
				tmpSum += A[j][i] * option.omega[j];
			}
			oalpha[i] = tmpSum;
			sum += tmpSum;
		}
		Koalpha = sum;
	}

	// private void computeOmega() {
	//
	// omega = new double[option.delta][trnModel.V];
	//
	// MBOVocabulary voc = null;
	// for (int j = 0; j < trnModel.V; j++) {
	// int nw = 0;
	// for (int i = 0; i < option.delta; i++) {
	// voc = (MBOVocabulary) vocWindow[i];
	// Integer _id = voc.word2id
	// .get(trnModel.data.localVoc.getWord(j));
	// int nwj = 0;
	// if (_id != null) {
	// nwj = voc.id2count.get(_id);
	// }
	// omega[i][j] = nwj;
	// nw += nwj;
	// }
	// for (int i = 0; i < option.delta; i++) {
	// if (nw == 0) {
	// omega[i][j] = option.omega[i];
	// } else {
	// omega[i][j] = omega[i][j] / nw;
	// }
	// }
	// }
	// }

	private void computeOmega() {

		omega = new double[option.delta][trnModel.V];
		int sum = 0;
		MBOVocabulary voc = null;
		for (int i = 0; i < option.delta; i++) {
			voc = (MBOVocabulary) vocWindow[i];
			omega[i][0] = voc.size;
			sum += voc.size;
		}
		for (int i = 0; i < option.delta; i++) {
			omega[i][0] = omega[i][0] / sum; 
		}
	}

	private void computeSigma() {
		for (int i = 0; i < trnModel.K; i++) {
			double TH = 0;
			for (int j = 0; j < option.delta; j++) {
				TH += A[j][i];
			}
			for (int j = 0; j < option.delta; j++) {
				sigma[j][i] = A[j][i] / TH;
			}
		}
	}

	private void adjustTimeWindow() {
		for (int i = option.delta - 1; i > 0; --i) {
			A[i - 1] = A[i];
			B[i - 1] = B[i];
			vocWindow[i - 1] = vocWindow[i];
		}
		A[option.delta - 1] = computeTht();
		B[option.delta - 1] = trnModel.phi;
		vocWindow[option.delta - 1] = trnModel.data.localVoc;
	}
}

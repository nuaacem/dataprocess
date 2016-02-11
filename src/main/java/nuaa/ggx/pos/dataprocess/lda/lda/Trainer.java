package nuaa.ggx.pos.dataprocess.lda.lda;

import java.io.File;

import nuaa.ggx.pos.dataprocess.lda.util.LdaUtil;

public class Trainer {
	
	public LdaArgs option;
	public Model trnModel;
	public double Kalpha;
	public double Vbeta;
	
	public boolean init(LdaArgs option) {
		this.option = option;
		trnModel = new Model();
		if (1 == option.est) {
			if (!trnModel.initNewModel(option)) {
				return false;
			}
			trnModel.data.localVoc.writeWordMap(option.dir + File.separator + option.wordMapFileName);
		}
		else if (1 == option.estc) {
			if (!trnModel.initEstimatedModel(option)) {
				return false;
			}
		}	
		Kalpha = trnModel.K * trnModel.alpha;
		Vbeta = trnModel.V * trnModel.beta;
		return true;
	}
	
	public void train() {
		System.out.println("Sampling " + trnModel.niters + " iteration!");
		
		int lastIter = trnModel.liter;
		for (trnModel.liter = lastIter + 1; trnModel.liter < trnModel.niters; trnModel.liter++) {
			System.out.println("Iteration " + trnModel.liter + " ...");
			
			for (int m = 0; m < trnModel.M; m++) {
				for (int n = 0, N = trnModel.data.docs.get(m).length; n < N; n++) {
					int topic = sample(m, n);
					trnModel.z[m][n] = topic;
				}
			}
			
			if (option.savestep > 0) {
				if (trnModel.liter % option.savestep == 0){
					System.out.println("Saving the model at iteration " + trnModel.liter + " ...");
					computeTheta();
					computePhi();
					trnModel.saveModel("model-" + LdaUtil.converseZeroPad(trnModel.liter, 5));
				}
			}
		}
		
		System.out.println("Gibbs sampling completed!\n");
		System.out.println("Saving the final model!\n");
		computeTheta();
		computePhi();
		trnModel.liter--;
		trnModel.saveModel(option.modelName);
	}
	
	/**
     * Sample a topic z_i from the full conditional distribution: p(z_i = j |
     * z_-i, w) = (n_-i,j(w_i) + beta)/(n_-i,j(.) + W * beta) * (n_-i,j(d_i) +
     * alpha)/(n_-i,.(d_i) + K * alpha) <br>
     * 根据上述公式计算文档m中第n个词语的主题的完全条件分布，输出最可能的主题
     *
     * @param m document
     * @param n word
     */
	protected int sample(int m, int n) {
		// remove z_i from the count variables  先将这个词从计数器中抹掉
		int topic = trnModel.z[m][n];
		int w = trnModel.data.docs.get(m).words[n];
		trnModel.nw[w][topic]--;
		trnModel.nd[m][topic]--;
		trnModel.nwsum[topic]--;
		trnModel.ndsum[m]--;
		
        // do multinomial sampling via cumulative method: 通过多项式方法采样多项式分布
		for (int k = 0; k < trnModel.K; k++) {
			trnModel.p[k] = (trnModel.nd[m][k] + trnModel.alpha) / (trnModel.ndsum[m] + Kalpha) * 
					(trnModel.nw[w][k] + trnModel.beta) / (trnModel.nwsum[k] + Vbeta);
		}
		
        // cumulate multinomial parameters  累加多项式分布的参数
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
	
	protected void computePhi() {
		for (int k = 0; k < trnModel.K; k++) {
			for (int w = 0; w < trnModel.V; w++) {
				trnModel.phi[k][w] = (trnModel.nw[w][k] + trnModel.beta) /
						(trnModel.nwsum[k] + Vbeta);
			}
		}
	}

	protected void computeTheta() {
		for (int m = 0; m < trnModel.M; m++) {
			for (int k = 0; k < trnModel.K; k++) {
				trnModel.theta[m][k] = (trnModel.nd[m][k] + trnModel.alpha) /
						(trnModel.ndsum[m] + Kalpha);
			}
		}		
	}
}

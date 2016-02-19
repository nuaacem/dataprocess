package nuaa.ggx.pos.dataprocess.lda.util;

import java.util.List;

import nuaa.ggx.pos.dataprocess.model.Domain;
import nuaa.ggx.pos.dataprocess.service.impl.DomainService;

public class Constants {
	
	public static final long BUFFER_SIZE_LONG = 1000000;
	public static final short BUFFER_SIZE_SHORT = 512;
	
	public static final int MODEL_STATUS_UNKNOWN = 0;
	public static final int MODEL_STATUS_EST = 1;
	public static final int MODEL_STATUS_ESTC = 2;
	public static final int MODEL_STATUS_INF = 3;
	
	public static final int FORWARD_RATE = 1;
	public static final int COMMENT_RATE = 1;
	public static final int LIKE_RATE = 1;

	public static final List<Domain> DOMAINS = DomainService.getDomains();
	
}

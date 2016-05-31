package cn.cem.Crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * @author HXF
 *
 */
public class httpClient {
	public static String CookieCrawler(String url) throws UnsupportedEncodingException, UnsupportedOperationException, IOException {
		
		System.out.println("----------------------------------------");
        System.out.println("----------------------------------------");

        String logPath = url;

        @SuppressWarnings("deprecation")
		DefaultHttpClient httpclient = new DefaultHttpClient();

        String cookieStr = "SINAGLOBAL=3571091049504.7334.1460686219450; wb_publish_vip_1908952571=5; myuid=1908952571; SUS=SID-1908952571-1462071430-GZ-wazqi-05a52a75346111ab793aaa18753a7dcb; SUE=es%3D52f91f2463183a7289cb8b55fcddf367%26ev%3Dv1%26es2%3D0e0982611e904b225101c76c2b49d9a3%26rs0%3DlUF0RRgWXyXwJsgBOhL4RWxlD9TuPZlIr%252FtxiRCsCJuTQCzaTyh8xhn9TrEihwidD7W44VEs9fRxxoyrIUq37n7jpmCr74AgVEJwNdTvdvIvnwSa8JWasvlskhYj5dEGUFodMkXcHeC%252B1PmptIsx0lBiwK0G5v%252BODjXArlC7o5w%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1462071429%26et%3D1462157829%26d%3Dc909%26i%3D7dcb%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D1908952571%26name%3D289631468%2540qq.com%26nick%3D%25E7%2594%25A8%25E6%2588%25B71908952571%26fmp%3D%26lcp%3D2013-07-24%252015%253A39%253A02; SUB=_2A256IQDVDeRxGedH61oY9SzJzD2IHXVZV3UdrDV8PUNbvtBeLRnSkW9LHesn7SAba3JxqsOyU_WgOk30Fb3ZyQ..; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWg3HQyi2QOWTZ3_gsoRFUA5JpX5KMt; SUHB=0YhQVfNYMg8kzl; ALF=1493607429; SSOLoginState=1462071430; YF-Ugrow-G0=169004153682ef91866609488943c77f; wvr=6; YF-V5-G0=b2423472d8aef313d052f5591c93cb75; _s_tentry=www.baidu.com; Apache=1261553021884.4407.1462071542769; ULV=1462071542782:13:1:1:1261553021884.4407.1462071542769:1461845766820; YF-Page-G0=091b90e49b7b3ab2860004fba404a078; UOR=,,www.baidu.com";

        // 目标地址
        HttpGet httpget = new HttpGet(logPath);
        httpget.setHeader("Cookie", cookieStr);

        System.out.println("请求: " + httpget.getRequestLine());
        // 设置类型
        // 执行
        HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        HttpEntity entity = response.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                entity.getContent(), "UTF-8"));
        String line = reader.readLine();
     /*   while ((line = ) != null) {
            System.out.println(line);
        }*/
		return line;
	}
}

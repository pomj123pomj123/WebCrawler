package tw.pers.allen.crawler.jsoup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// ALT + ?
// CRTL + 1
public class Action {

	public static void main(String[] args) throws Exception {

		final String output = "C:\\Users\\User\\Desktop\\product\\";
		File folder = new File(output); // 將目標路徑建立成File物件
		if (!folder.exists()) { // 判斷目標是否存在? 不存在則做
			folder.mkdir(); // 建立資料夾
		}

		final String TARGET = "https://24h.pchome.com.tw/region/DHAA";

		URL url = new URL(TARGET);

		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

		String html = "";
		String temp;
		while ((temp = br.readLine()) != null) {
			html += temp; // 最好 StringBuilder or StringBuffer
		}

		br.close();

		Document doc = Jsoup.parse(html); // 解析Html得到doc物件
		Elements elements = doc.getElementsByClass("main_scontent"); // 根據類別選取目標
		Element element = elements.get(0); // 很多元素可使用同類別，從集合拿出第一筆元素

		Elements prodcuts = element.select(".main_con"); // 選取目標元素內的所有img(包含子孫元素)

		for (int i = 0; i < prodcuts.size(); i++) {
			Element p = prodcuts.get(i);
			Elements img = p.select("img");

			URL u = new URL(img.attr("src"));
			BufferedInputStream bis = new BufferedInputStream(u.openStream());
			byte[] bytes = bis.readAllBytes();
			bis.close();

			FileOutputStream fos = new FileOutputStream(output + i + ".jpg");

			fos.write(bytes);
			fos.close();

		}

	}

}

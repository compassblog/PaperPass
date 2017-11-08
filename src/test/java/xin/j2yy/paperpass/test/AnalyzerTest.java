package xin.j2yy.paperpass.test;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import xin.j2yy.paperpass.commons.lucene.ElementDict;
import xin.j2yy.paperpass.commons.lucene.TextCosine;

public class AnalyzerTest {
	@Test
	public void test1() {
		String keyWord = "java是一种可以撰写跨平台应用软件的面向对象的程序设计语言。";
		IKAnalyzer analyzer = new IKAnalyzer();
		System.out.println("分词：" + keyWord);
		try {
			TokenStream tokenStream = analyzer.tokenStream("",keyWord);
			tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream.reset();// 必须先调用reset方法，否则会报下面的错，可以参考TokenStream的API说明
			System.out.print("结果：");
			while (tokenStream.incrementToken()) {
				CharTermAttribute charTermAttribute = (CharTermAttribute) tokenStream
						.getAttribute(CharTermAttribute.class);
				System.out.print(charTermAttribute.toString() + " ");
			}
			tokenStream.end();
			tokenStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void docLike() {
		TextCosine textCosine = new TextCosine();
		String str1 = "java是一种可以撰写跨平台应用软件的面向对象的程序设计语言";
		String str2 = "面向对象的程序设计语言";
		List<ElementDict> em1 = textCosine.tokenizer(str1);
		List<ElementDict> em2 = textCosine.tokenizer(str2);
		double socre = textCosine.analysis(em1, em2);
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
		System.out.println(df.format(socre));
	}
}

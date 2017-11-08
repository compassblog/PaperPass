package xin.j2yy.paperpass.commons.util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import xin.j2yy.paperpass.commons.lucene.ElementDict;
import xin.j2yy.paperpass.commons.lucene.TextCosine;
import xin.j2yy.paperpass.entity.Paper;

/**
 * lucene工具类，基于Lucene7.1.0
 * 
 * @author firefish
 *
 */
public class LuceneUtil {

	private static final LuceneManager luceneManager = LuceneManager.getInstance();

	/**
	 * 打开索引目录
	 * 
	 * @param indexFactory
	 * @return
	 */
	public static FSDirectory openFSDDirectory(String indexDirectory) {
		FSDirectory directory = null;
		try {
			directory = FSDirectory.open(Paths.get(indexDirectory));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return directory;
	}
	/**
	 * 获取配置文件，分词器采用中文分词
	 * @return
	 */
	public static IndexWriterConfig getIndexWriterConfig() {
		IKAnalyzer analyzer = new IKAnalyzer();
		return new IndexWriterConfig(analyzer);
	}

	/**
	 * 关闭索引目录
	 * 
	 * @param directory
	 */
	public static void closeDirectory(Directory directory) {
		try {
			if (directory != null) {
				directory.close();
				directory = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取IndexWriter
	 * 
	 * @param directory
	 * @param config
	 * @return
	 */
	public static IndexWriter getIndexWriter(Directory directory, IndexWriterConfig config) {
		return luceneManager.getIndexWriter(directory, config);
	}

	/**
	 * 获取IndexWriter
	 * 
	 * @param path
	 * @param config
	 * @return
	 */
	public static IndexWriter getIndexWriter(String path, IndexWriterConfig config) {
		FSDirectory directory = openFSDDirectory(path);
		return luceneManager.getIndexWriter(directory, config);
	}

	/**
	 * 关闭IndexWriter
	 * 
	 * @param indexWriter
	 */
	public static void closeIndexWriter(IndexWriter indexWriter) {
		luceneManager.closeIndexWriter(indexWriter);
	}

	/**
	 * 获得IndexReader，未开启NRTReader
	 * 
	 * @param directory
	 * @return
	 */
	public static IndexReader getIndexReader(Directory directory) {
		return luceneManager.getIndexReader(directory);
	}

	/**
	 * 获取IndexReader，自主选择是否开启NRTReader
	 * 
	 * @param directory
	 * @param enableNRTReader
	 * @return
	 */
	public static IndexReader getIndexReader(Directory directory, boolean enableNRTReader) {
		return luceneManager.getIndexReader(directory, enableNRTReader);
	}

	/**
	 * 关闭IndexReader
	 * 
	 * @param indexReader
	 */
	public static void closeIndexReader(IndexReader indexReader) {
		try {
			if (indexReader != null) {
				indexReader.close();
				indexReader = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文本相似度计算
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static double docsLike(String str1, String str2) {
		double score = 0;
		TextCosine textCosine = new TextCosine();
		List<ElementDict> list1 = textCosine.tokenizer(str1);
		List<ElementDict> list2 = textCosine.tokenizer(str2);
		score = textCosine.analysis(list1, list2);
		return score;
	}
	
	public static Paper reversalDocument(Document doc) {
		Paper paper = new Paper();
		String pid = doc.get("pid");
		String title = doc.get("title");
		String type = doc.get("type");
		String synopsis = doc.get("synopsis");
		paper.setPid(Integer.parseInt(pid));
		paper.setTitle(title);
		paper.setType(type);
		paper.setSynopsis(synopsis);
		return paper;
	}
	
	public static Document reversalPaper(Paper paper) {
		Document doc = new Document();
		doc.add(new StringField("pid", String.valueOf(paper.getPid()), Field.Store.YES));
		doc.add(new TextField("title", paper.getTitle(), Field.Store.YES));
		doc.add(new TextField("type", paper.getType(), Field.Store.YES));
		doc.add(new TextField("synopsis", paper.getSynopsis(), Field.Store.YES));
		return doc;
	}
}

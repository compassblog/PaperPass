package xin.j2yy.paperpass.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import xin.j2yy.paperpass.bean.PaperBean;
import xin.j2yy.paperpass.commons.util.LuceneUtil;
import xin.j2yy.paperpass.dao.PaperDao;
import xin.j2yy.paperpass.entity.Paper;
import xin.j2yy.paperpass.service.LuceneService;

@Service("luceneService")
public class LuceneServiceImpl implements LuceneService {
	@Autowired
	PaperDao paperDao;

	/**
	 * 创建索引
	 * 
	 * @throws IOException
	 */
	public void createIndex() throws IOException {
		// 创建索引目录
		Directory dir = LuceneUtil.openFSDDirectory("D:\\lucene");
		// 创建IndexWriter配置对象,默认使用IKAnalyzer中文分词器
		IndexWriterConfig config = LuceneUtil.getIndexWriterConfig();
		// 创建IndexWriter对象
		IndexWriter indexWriter = LuceneUtil.getIndexWriter(dir, config);
		/*
		 * 获取数据源，从数据库中获取需要索引的数据
		 */
		List<Paper> papers = paperDao.selectAll();
		for (Paper paper : papers) {
			Document doc = LuceneUtil.reversalPaper(paper);
			// 将数据写入索引
			indexWriter.addDocument(doc);
		}
		// 提交索引
		indexWriter.commit();
		// 关闭索引和索引目录
		LuceneUtil.closeIndexWriter(indexWriter);
		LuceneUtil.closeDirectory(dir);
	}

	/**
	 * 增量添加索引
	 * 
	 * @param paper
	 * @throws IOException
	 */
	public void insertIndex(Paper paper) throws IOException {
		// 打开索引目录
		Directory dir = LuceneUtil.openFSDDirectory("D:\\lucene");
		IndexWriterConfig config = LuceneUtil.getIndexWriterConfig();
		IndexWriter indexWriter = LuceneUtil.getIndexWriter(dir, config);
		Document doc = LuceneUtil.reversalPaper(paper);
		indexWriter.addDocument(doc);
		indexWriter.commit();
		LuceneUtil.closeIndexWriter(indexWriter);
	}

	/**
	 * 删除索引
	 * 
	 * @param str
	 * @throws Exception
	 */
	public void deleteIndex(Paper paper) throws Exception {
		Directory dir = LuceneUtil.openFSDDirectory("D:\\lucene");
		IndexWriterConfig config = LuceneUtil.getIndexWriterConfig();
		IndexWriter indexWriter = new IndexWriter(dir, config);
		// 根据论文ID删除索引
		indexWriter.deleteDocuments(new Term("pid", String.valueOf(paper.getPid())));
		indexWriter.commit();
		LuceneUtil.closeIndexWriter(indexWriter);
	}

	/**
	 * 索引查询
	 * 
	 * @param str
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public List<PaperBean> searchIndex(String str) throws IOException, ParseException {
		List<PaperBean> beans = new ArrayList<>();
		// 确定索引库
		Directory dir = LuceneUtil.openFSDDirectory("D:\\lucene");
		DirectoryReader reader = DirectoryReader.open(dir);
		// 建立搜索索引
		IndexSearcher searcher = new IndexSearcher(reader);
		// 设置分词器
		Analyzer analyzer = new IKAnalyzer();
		QueryParser queryParser = new QueryParser("title", analyzer);
		Query query = queryParser.parse(str);
		TopDocs hits = searcher.search(query, 10);
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			Paper paper = LuceneUtil.reversalDocument(doc);
			double socre = LuceneUtil.docsLike(str, paper.getTitle());
			beans.add(new PaperBean(paper,socre));
		}
		reader.close();
		return beans;
	}
}
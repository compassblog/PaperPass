package xin.j2yy.paperpass.commons.util;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;

/**
 * Lucene索引读写器、查询器单例获取工具类
 * 
 * @author firefish
 *
 */
public class LuceneManager {
	// 单例设计模式 :懒汉式
	// private volatile static LuceneManager luceneManager;
	// 饿汉式
	private volatile static LuceneManager luceneManager = new LuceneManager();
	// 索引写入
	private volatile static IndexWriter indexWriter;
	// 索引读取
	private volatile static IndexReader indexReader;

	private final Lock writerLock = new ReentrantLock();
	// 线程绑定
	private static ThreadLocal<IndexWriter> threadLocal = new ThreadLocal<>();

	// 构造器私有化
	private LuceneManager() {
	}

	// public static LuceneManager getInstance() {
	// if (luceneManager == null) {
	// synchronized (LuceneManager.class) {
	// if (luceneManager == null) {
	// luceneManager = new LuceneManager();
	// }
	// }
	// }
	// return luceneManager;
	// }
	/**
	 * 单例获取LuceneManager对象 饿汉式不需要考虑线程同步问题，在此推荐使用
	 * 
	 * @return
	 */
	public static LuceneManager getInstance() {
		return luceneManager;
	}

	/**
	 * 获取单例的IndexWriter对象
	 * 
	 * @param dir
	 * @param directory
	 * @return
	 */
	public IndexWriter getIndexWriter(Directory directory, IndexWriterConfig config) {
		if (directory == null) {
			throw new IllegalArgumentException("Directory对象不能为空！！！");
		}
		if (config == null) {
			throw new IllegalArgumentException("IndexWriterConfig对象不能为空！！！");
		}
		try {
			writerLock.lock();
			// 获取当前线程下的indexWriter对象
			indexWriter = threadLocal.get();
			if (indexWriter == null) {
				indexWriter = new IndexWriter(directory, config);
				// 绑定线程
				threadLocal.set(indexWriter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return indexWriter;
	}

	/**
	 * 获取单例的IndexReader对象
	 * 
	 * @param directory
	 * @param enableNRTReader
	 *            是否开启实时索引
	 * @return
	 */
	public IndexReader getIndexReader(Directory directory, boolean enableNRTReader) {
		if (directory == null) {
			throw new IllegalArgumentException("Directory对象不能为空！！！");
		}
		try {
			// 实例化indexReader对象
			if (indexReader == null) {
				indexReader = DirectoryReader.open(directory);
			}
			if (enableNRTReader && indexReader instanceof DirectoryReader) {
				// 开启近实时Reader,能立即看到动态添加/删除的索引变化
				indexReader = DirectoryReader.openIfChanged((DirectoryReader) indexReader);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexReader;
	}

	/**
	 * 获取IndexReader对象，默认不开启实时索引
	 * 
	 * @param directory
	 * @return
	 */
	public IndexReader getIndexReader(Directory directory) {
		return getIndexReader(directory, false);
	}
	
	/**
	 * 关闭IndexWriter
	 * @param indexWriter
	 */
	public void closeIndexWriter(IndexWriter indexWriter) {
		try {
			if (indexWriter != null) {
				indexWriter.close();
				indexWriter = null;
				//从线程池中移出
				threadLocal.remove();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

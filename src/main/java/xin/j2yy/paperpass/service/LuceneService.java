package xin.j2yy.paperpass.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

import xin.j2yy.paperpass.bean.PaperBean;
import xin.j2yy.paperpass.entity.Paper;

public interface LuceneService {
	public void createIndex() throws IOException;
	public void insertIndex(Paper paper) throws IOException;
	public void deleteIndex(Paper paper) throws Exception;
	public List<PaperBean> searchIndex(String str) throws IOException, ParseException;
}

package xin.j2yy.paperpass.test;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import xin.j2yy.paperpass.bean.PaperBean;
import xin.j2yy.paperpass.service.LuceneService;

public class LuceneTest {
	ApplicationContext app;
	@Before
	public void before() {
		app = new ClassPathXmlApplicationContext("spring.xml");
	}
	@Test
	public void createIndexTest() throws IOException {
		LuceneService luceneService = (LuceneService) app.getBean("luceneService");
		luceneService.createIndex();
	}
	@Test
	public void searcheIndexTest() throws IOException, ParseException {
		LuceneService luceneService = (LuceneService) app.getBean("luceneService");
		List<PaperBean> bean = luceneService.searchIndex("幼儿园系统");
		for (PaperBean paperBean : bean) {
			System.out.println(paperBean.getPaper().getTitle()+ "评分：" + paperBean.getSocre());
		}
	}
}

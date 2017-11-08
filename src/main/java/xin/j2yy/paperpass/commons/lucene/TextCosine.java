package xin.j2yy.paperpass.commons.lucene;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TextCosine {
	// 用来保存同义词典中的键值对
	private Map<String, String> map = null;

	public TextCosine() {
		map = new HashMap<String, String>();
		try {
			InputStreamReader isReader = new InputStreamReader(
					new FileInputStream(TextCosine.class.getClassLoader().getResource("synonyms.dict").getPath()), "UTF-8");
			BufferedReader br = new BufferedReader(isReader);
			String s = null;
			while ((s = br.readLine()) != null) {
				String[] synonymsEnum = s.split("→");
				map.put(synonymsEnum[0], synonymsEnum[1]);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 传入相应的字符串，对齐进行分词，然后对分词信息和出现的频率信息进行保存 使用IKAnlyzer进行分词
	 * 
	 * @param str
	 * @return
	 */
	public List<ElementDict> tokenizer(String str) {
		// ElementDict保存了关于分词后的词组信息和其出现的频率
		List<ElementDict> list = new ArrayList<ElementDict>();
		IKAnalyzer analyzer = new IKAnalyzer();
		try {
			// 将一个字符串分词
			TokenStream stream = analyzer.tokenStream("", str);
			// 将分词过后的相应词汇保存进内存中
			CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
			// 重设流（可选）
			stream.reset();
			int index = -1;
			// 获取下一分词信息
			while (stream.incrementToken()) {
				// 判断list集合中是否包含该分词信息
				/*
				 * cta.toString()：获取该分词信息的字符串内容 isContain(cta.toString(),
				 * list):判断该分词信息是否在list集合中存在, 如存在，返回该分词信息所出现的频率
				 */
				if ((index = isContain(cta.toString(), list)) >= 0) {
					// 如果存在，更新分词信息出现的频率
					list.get(index).setFreq(list.get(index).getFreq() + 1);
				} else {
					// 否则在list集合中添加该分词信息
					list.add(new ElementDict(cta.toString(), 1));
				}
			}
			// 关闭分词器
			analyzer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 返回保存有分词信息的list集合
		return list;
	}

	/**
	 * 判断该字符串是否在list集合中存在,
	 * 
	 * @param str
	 * @param list
	 * @return
	 */
	private int isContain(String str, List<ElementDict> list) {
		for (ElementDict ed : list) {
			// 如果存在，则返回该字符串所在的ElementDict对象在list集合中的索引
			if (ed.getTerm().equals(str)) {
				return list.indexOf(ed);
			}
			/*
			 * 相似判断： 若该ElementDict对象中的term值不为空， 且在相似值集合中key为该term所对应的值与该字符串的值相等，
			 * 则返回该ElementDict对象在list集合中的索引
			 */
			else if (map.get(ed.getTerm()) != null && map.get(ed.getTerm()).equals(str)) {
				return list.indexOf(ed);
			}
		}
		// 否则返回-1
		return -1;
	}

	/**
	 * 将分词后的集合进行合并，得到归并后的分词信息集合
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	private List<String> mergeTerms(List<ElementDict> list1, List<ElementDict> list2) {
		List<String> list = new ArrayList<String>();
		// 遍历集合list1
		for (ElementDict ed : list1) {
			// 判断归并集合中是否存在该分词信息
			if (!list.contains(ed.getTerm())) {
				// 若不存在，向list集合中添加该字段
				list.add(ed.getTerm());
			}
			// 对近似值集合进行判断
			else if (!list.contains(map.get(ed.getTerm()))) {
				// 若不存在，向list集合添加该字段
				list.add(ed.getTerm());
			}
		}
		// 遍历集合list2，判断流程如集合list1的判断流程
		for (ElementDict ed : list2) {
			if (!list.contains(ed.getTerm())) {
				list.add(ed.getTerm());
			} else if (!list.contains(map.get(ed.getTerm()))) {
				list.add(ed.getTerm());
			}
		}
		// 返回合并后的分词信息集合
		return list;
	}

	/**
	 * 若长句长度（中文切分后以词汇为单位表征，并非以字符为单位）为短句的1.5倍，
	 * 则针对长句选定短句长度的文本内容逐个与短句进行相似度判定，直至长句结束， 若中间达到预设的阈值，则跳出该循环，否则判定文本不相似。
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public int anslysisTerms(List<ElementDict> list1, List<ElementDict> list2) {
		// 获取两个分词集合的长度
		int len1 = list1.size();
		int len2 = list2.size();
		// 如何集合list2的长度大于list1的1.5倍
		if (len2 >= len1 * 1.5) {
			// 则针对长句选定短句长度的文本内容逐个与短句进行相似度判定
			List<ElementDict> newList = new ArrayList<ElementDict>();
			for (int i = 0; i + len1 <= len2; i++) {
				for (int j = 0; j < len1; j++)
					newList.add(list2.get(i + j));
				newList = adjustList(newList, list2, len2, len1, i);
				// 中间达到预设的阈值，则跳出该循环
				if (getResult(analysis(list1, newList)))
					return 1;
				else
					newList.clear();
			}
		} else if (len1 >= len2 * 1.5) {
			List<ElementDict> newList = new ArrayList<ElementDict>();
			for (int i = 0; i + len2 <= len1; i++) {
				for (int j = 0; j < len2; j++)
					newList.add(list1.get(i + j));

				newList = adjustList(newList, list1, len1, len2, i);
				if (getResult(analysis(newList, list2)))
					return 1;
				else
					newList.clear();
			}
		} else {
			if (getEasyResult(analysis(list1, list2)))
				return 1;
		}
		return 0;
	}
	
	

	/**
	 * 调整List
	 * 
	 * @param newList
	 * @param list
	 * @param lenBig
	 * @param lenSmall
	 * @param index
	 * @return
	 */
	public List<ElementDict> adjustList(List<ElementDict> newList, List<ElementDict> list, int lenBig, int lenSmall,
			int index) {
		int gap = lenBig - lenSmall;
		int size = (gap / 2 > 2) ? 2 : gap / 2;
		if (index < gap / 2) {
			for (int i = 0; i < size; i++) {
				newList.add(list.get(lenSmall + index + i));
			}
		} else {
			for (int i = 0; i > size; i++) {
				newList.add(list.get(lenBig - index - i));
			}
		}
		return newList;
	}

	/**
	 * 分析list
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public double analysis(List<ElementDict> list1, List<ElementDict> list2) {
		List<String> list = mergeTerms(list1, list2);
		List<Integer> weightList1 = assignWeight(list, list1);
		List<Integer> weightList2 = assignWeight(list, list2);
		return countCosSimilariry(weightList1, weightList2);
	}

	public List<Integer> assignWeight(List<String> list, List<ElementDict> list1) {
		List<Integer> vecList = new ArrayList<Integer>(list.size());
		boolean isEqual = false;
		for (String str : list) {
			for (ElementDict ed : list1) {
				if (ed.getTerm().equals(str)) {
					isEqual = true;
					vecList.add(new Integer(ed.getFreq()));
				} else if (map.get(ed.getTerm()) != null && map.get(ed.getTerm()).equals(str)) {
					isEqual = true;
					vecList.add(new Integer(ed.getFreq()));
				}
			}

			if (!isEqual) {
				vecList.add(new Integer(0));
			}
			isEqual = false;
		}
		return vecList;
	}

	/**
	 * 相似度计算
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public double countCosSimilariry(List<Integer> list1, List<Integer> list2) {
		double countScores = 0;
		int element = 0;
		int denominator1 = 0;
		int denominator2 = 0;
		int index = -1;
		for (Integer it : list1) {
			index++;
			int left = it.intValue();
			int right = list2.get(index).intValue();
			element += left * right;
			denominator1 += left * left;
			denominator2 += right * right;
		}
		try {
			countScores = (double) element / Math.sqrt(denominator1 * denominator2);
		} catch (ArithmeticException e) {
			e.printStackTrace();
		}
		return countScores;
	}
	public boolean getResult(double scores) {
		if (scores >= 0.85)
			return true;
		else
			return false;
	}
	public boolean getEasyResult(double scores) {
		if (scores >= 0.75)
			return true;
		else
			return false;
	}

}
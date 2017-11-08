package xin.j2yy.paperpass.bean;

import xin.j2yy.paperpass.entity.Paper;

public class PaperBean {
	private Paper paper;
	private double socre;
	public PaperBean(Paper paper, double socre) {
		super();
		this.paper = paper;
		this.socre = socre;
	}
	public Paper getPaper() {
		return paper;
	}
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	public double getSocre() {
		return socre;
	}
	public void setSocre(double socre) {
		this.socre = socre;
	}
}

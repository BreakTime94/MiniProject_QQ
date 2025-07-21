package com.climbjava.miniproject_qq.domain;

public class Menu {
	private int no;
	private String name;
	private int category; // 0 메인메뉴, 1 사이드, 2 주류
	private int price; // 가격
	private boolean sale = true;

	public Menu() {
	}

	public Menu(int no, String name, int category, int price) {
		super();
		this.no = no;
		this.name = name;
		this.category = category;
		this.price = price;

	}

	// Getter/Setter 사용
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isSale() {
		return sale;
	}

	public void setSale(boolean sale) {
		this.sale = sale;
	}

	@Override
	public String toString() {
		return (category == 0 ? "메인메뉴" : (category == 1 ? "사이드메뉴" : "주류")) + " [no=" + no + ", name=" + name + ", category="
						+ (category == 0 ? "메인메뉴" : (category == 1 ? "사이드메뉴" : "주류")) + ", price=" + price + "]";
	}
}
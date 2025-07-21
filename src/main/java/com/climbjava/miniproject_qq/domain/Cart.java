package com.climbjava.miniproject_qq.domain;

public class Cart extends Menu{
	private int amount;

	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public Cart(Menu menu, int amount) {
		super(menu.getNo(), menu.getName(), menu.getCategory(), menu.getPrice());
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return " 메뉴번호 : " + getNo()  + ", 메뉴명 : " + getName() +  ", 가격 = " + getPrice() + "원, " + amount + "개, " + "소계 = " + getPrice() * amount + "원" ;
	}


}

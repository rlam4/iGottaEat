package iGottaEat;

public class Restaurant {
	String name, number, address, url;
	Double rating;
	int allTimeVisitors;
	int yearVisitors;
	int monthVisitors;
	int weekVisitors;
	int dayVisitors;
	
	/*getters and setters*/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getURL() {
		return url;
	}
	
	public String setURL(String url) {
		this.url = url;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	private int getAllTimeVisitors() {
		return allTimeVisitors;
	}
	private void setAllTimeVisitors(int allTimeVisitors) {
		this.allTimeVisitors = allTimeVisitors;
	}
	
	private int getYearVisitors() {
		return yearVisitors;
	}
	private void setYearVisitors(int yearVisitors) {
		this.yearVisitors = yearVisitors;
	}
	private int getMonthVisitors() {
		return monthVisitors;
	}
	
	private void setMonthVisitors(int monthVisitors) {
		this.monthVisitors = monthVisitors;
	}
	
	private int getWeekVisitors() {
		return weekVisitors;
	}
	private void setWeekVisitors(int weekVisitors) {
		this.weekVisitors = weekVisitors;
	}
	
	private int getDayVisitors() {
		return dayVisitors;
	}
	private void setDayVisitors(int dayVisitors) {
		this.dayVisitors = dayVisitors;
	}
	
	
	
	
}

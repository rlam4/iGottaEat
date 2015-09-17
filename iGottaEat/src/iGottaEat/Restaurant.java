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
	
	private String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	
	private String getNumber() {
		return number;
	}
	private void setNumber(String number) {
		this.number = number;
	}
	
	private String getURL() {
		return url;
	}
	
	private String setURL(String url) {
		this.url = url;
	}
	
	private String getAddress() {
		return address;
	}
	private void setAddress(String address) {
		this.address = address;
	}
	
	private Double getRating() {
		return rating;
	}
	private void setRating(Double rating) {
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

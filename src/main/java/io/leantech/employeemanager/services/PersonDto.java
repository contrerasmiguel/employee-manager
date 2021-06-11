package io.leantech.employeemanager.services;

public class PersonDto {
	private final String name;
	private final String lastName;
	private final String address;
	private final String cityName;
	private final String cellphone;
	
	public PersonDto(
			String name,
			String lastName,
			String address,
			String cityName,
			String cellphone
	) {
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.cityName = cityName;
		this.cellphone = cellphone;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getCityName() {
		return cityName;
	}

	public String getCellphone() {
		return cellphone;
	}
}

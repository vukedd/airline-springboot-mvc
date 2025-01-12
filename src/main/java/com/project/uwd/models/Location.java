package com.project.uwd.models;

import com.project.uwd.models.enums.Continent;

public class Location {
	private Long id;
	private String city;
	private String country;
	private Continent continent;
	private byte[] image;
	private String decodedImage;
	
	public Location(Long id, String city, String country, Continent continent) {
		super();
		this.id = id;
		this.city = city;
		this.country = country;
		this.continent = continent;
	}

	public Location() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Continent getContinent() {
		return continent;
	}

	public void setContinent(Continent continent) {
		this.continent = continent;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getDecodedImage() {
		return decodedImage;
	}

	public void setDecodedImage(String decodedImage) {
		this.decodedImage = decodedImage;
	}
}

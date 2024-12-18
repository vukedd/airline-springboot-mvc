package com.project.uwd.models;

public class Airplane {
	private Long id;
	private String name;
	private int numberOfColumns;
	private int numberOfRows;
	
	public Airplane(Long id, String name, int numberOfColumns, int numberOfRows) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
	}

	public Airplane() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	@Override
	public String toString() {
		return "Airplane [id=" + id + ", name=" + name + ", numberOfColumns=" + numberOfColumns + ", numberOfRows=" + numberOfRows + "]";
	}
}

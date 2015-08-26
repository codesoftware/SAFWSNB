package co.com.codesoftware.persistence.entites;

import co.com.codesoftware.persistence.enumeration.DataType;

public class ParamFunction {

	public String name;
	public DataType dataType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
}

package edu.uark.uarkregisterapp.models.api.fields;

import edu.uark.uarkregisterapp.models.api.interfaces.FieldNameInterface;

public enum ProductListingFieldName implements FieldNameInterface {
	PRODUCTS("products");

	private String fieldName;
	public String getFieldName() {
		return this.fieldName;
	}

	ProductListingFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}

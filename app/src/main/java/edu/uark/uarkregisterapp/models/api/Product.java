package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.fields.ProductFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class Product implements ConvertToJsonInterface, LoadFromJsonInterface<Product> {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public Product setId(UUID id) {
		this.id = id;
		return this;
	}

	private String lookupCode;
	public String getLookupCode() {
		return this.lookupCode;
	}
	public Product setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	private int count;
	public int getCount() {
		return this.count;
	}
	public Product setCount(int count) {
		this.count = count;
		return this;
	}

	private Date createdOn;
	public Date getCreatedOn() {
		return this.createdOn;
	}
	public Product setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	@Override
	public Product loadFromJson(JSONObject rawJsonObject) {
		String value = rawJsonObject.optString(ProductFieldName.ID.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value);
		}

		this.lookupCode = rawJsonObject.optString(ProductFieldName.LOOKUP_CODE.getFieldName());
		this.count = rawJsonObject.optInt(ProductFieldName.COUNT.getFieldName());

		value = rawJsonObject.optString(ProductFieldName.CREATED_ON.getFieldName());
		if (!StringUtils.isBlank(value)) {
			try {
				this.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return this;
	}

	@Override
	public JSONObject convertToJson() {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put(ProductFieldName.ID.getFieldName(), this.id.toString());
			jsonObject.put(ProductFieldName.LOOKUP_CODE.getFieldName(), this.lookupCode);
			jsonObject.put(ProductFieldName.COUNT.getFieldName(), this.count);
			jsonObject.put(ProductFieldName.CREATED_ON.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdOn));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public Product() {
		this.count = -1;
		this.lookupCode = "";
		this.id = new UUID(0, 0);
		this.createdOn = new Date();
	}

	public Product(ProductTransition productTransition) {
		this.id = productTransition.getId();
		this.count = productTransition.getCount();
		this.createdOn = productTransition.getCreatedOn();
		this.lookupCode = productTransition.getLookupCode();
	}
}

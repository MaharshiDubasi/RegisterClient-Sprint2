package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

import edu.uark.uarkregisterapp.commands.converters.ByteToUUIDConverterCommand;
import edu.uark.uarkregisterapp.commands.converters.UUIDToByteConverterCommand;
import edu.uark.uarkregisterapp.models.api.Product;

public class ProductTransition implements Parcelable {
	private UUID id;
	public UUID getId() {
		return this.id;
	}
	public ProductTransition setId(UUID id) {
		this.id = id;
		return this;
	}

	private String lookupCode;
	public String getLookupCode() {
		return this.lookupCode;
	}
	public ProductTransition setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	private int count;
	public int getCount() {
		return this.count;
	}
	public ProductTransition setCount(int count) {
		this.count = count;
		return this;
	}

	private Date createdOn;
	public Date getCreatedOn() {
		return this.createdOn;
	}
	public ProductTransition setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeByteArray((new UUIDToByteConverterCommand()).setValueToConvert(this.id).execute());
		destination.writeString(this.lookupCode);
		destination.writeInt(this.count);
		destination.writeLong(this.createdOn.getTime());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<ProductTransition> CREATOR = new Parcelable.Creator<ProductTransition>() {
		public ProductTransition createFromParcel(Parcel productTransitionParcel) {
			return new ProductTransition(productTransitionParcel);
		}

		public ProductTransition[] newArray(int size) {
			return new ProductTransition[size];
		}
	};

	public ProductTransition() {
		this.count = -1;
		this.id = new UUID(0, 0);
		this.createdOn = new Date();
		this.lookupCode = StringUtils.EMPTY;
	}

	public ProductTransition(Product product) {
		this.id = product.getId();
		this.count = product.getCount();
		this.createdOn = product.getCreatedOn();
		this.lookupCode = product.getLookupCode();
	}

	private ProductTransition(Parcel productTransitionParcel) {
		this.id = (new ByteToUUIDConverterCommand()).setValueToConvert(productTransitionParcel.createByteArray()).execute();
		this.lookupCode = productTransitionParcel.readString();
		this.count = productTransitionParcel.readInt();

		this.createdOn = new Date();
		this.createdOn.setTime(productTransitionParcel.readLong());
	}
}

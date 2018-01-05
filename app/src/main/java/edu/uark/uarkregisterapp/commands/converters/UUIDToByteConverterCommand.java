package edu.uark.uarkregisterapp.commands.converters;

import java.nio.ByteBuffer;
import java.util.UUID;

import edu.uark.uarkregisterapp.commands.interfaces.ResultInterface;

public class UUIDToByteConverterCommand implements ResultInterface<byte[]> {
	@Override
	public byte[] execute() {
		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
		byteBuffer.putLong(this.valueToConvert.getMostSignificantBits());
		byteBuffer.putLong(this.valueToConvert.getLeastSignificantBits());

		return byteBuffer.array();
	}

	private UUID valueToConvert;
	public UUID getValueToConvert() {
		return this.valueToConvert;
	}
	public UUIDToByteConverterCommand setValueToConvert(UUID valueToConvert) {
		this.valueToConvert = valueToConvert;
		return this;
	}
}

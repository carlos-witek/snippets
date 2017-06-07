package org.carlos.hashing;

import java.nio.ByteBuffer;
import java.util.List;

public class Bytes {

	public static final byte[] allDIY( Integer... integers ) {
		byte[] bytes = new byte[4 * integers.length];

		int index = 0;
		for ( Integer integer : integers ) {
			int intValue = integer.intValue();
			bytes[index++] = (byte) ( intValue );
			bytes[index++] = (byte) ( intValue >>> 8 );
			bytes[index++] = (byte) ( intValue >>> 16 );
			bytes[index++] = (byte) ( intValue >>> 24 );
		}
		return bytes;
	}

	public static final byte[] allDIY( List<Integer> integers ) {
		byte[] bytes = new byte[4 * integers.size()];

		int index = 0;
		for ( Integer integer : integers ) {
			int intValue = integer.intValue();
			bytes[index++] = (byte) ( intValue );
			bytes[index++] = (byte) ( intValue >>> 8 );
			bytes[index++] = (byte) ( intValue >>> 16 );
			bytes[index++] = (byte) ( intValue >>> 24 );
		}
		return bytes;
	}

	public static final byte[] all( Integer... integers ) {
		ByteBuffer buffer = ByteBuffer.allocate( 4 * integers.length );
		for ( Integer integer : integers )
			buffer.putInt( integer.intValue() );
		return buffer.array();
	}

	public static final byte[] all( List<Integer> integers ) {
		ByteBuffer buffer = ByteBuffer.allocate( 4 * integers.size() );
		for ( Integer integer : integers )
			buffer.putInt( integer.intValue() );
		return buffer.array();
	}

}

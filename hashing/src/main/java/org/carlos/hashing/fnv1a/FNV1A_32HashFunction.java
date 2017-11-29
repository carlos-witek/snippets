package org.carlos.hashing.fnv1a;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.primitives.Chars;

public class FNV1A_32HashFunction implements HashFunction {

	private static final long FNV_OFFSET_BASIS = 2166136261L;
	private static final long FNV_PRIME = 16777619L;

	@Override
	public Hasher newHasher() {
		return newHasher( 32 );
	}

	@Override
	public Hasher newHasher( int expectedInputSize ) {
		return new Hasher() {
			private long hash = FNV_OFFSET_BASIS;

			@Override
			public Hasher putByte( byte b ) {
				hash = ( hash ^ b ) * FNV_PRIME;
				return this;
			}

			@Override
			public Hasher putBytes( byte[] bytes ) {
				for ( int i = 0; i < bytes.length; i++ )
					hash = ( hash ^ bytes[i] ) * FNV_PRIME;
				return this;
			}

			@Override
			public Hasher putBytes( byte[] bytes, int off, int len ) {
				for ( int i = off; i < off + len; i++ )
					hash = ( hash ^ bytes[i] ) * FNV_PRIME;
				return this;
			}

			@Override
			public Hasher putBytes( ByteBuffer bytes ) {
				while ( bytes.hasRemaining() )
					hash = ( hash ^ bytes.get() ) * FNV_PRIME;
				return this;
			}

			@Override
			public Hasher putShort( short s ) {
				hash = ( hash ^ (byte) ( s ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( s >>> 8 ) ) * FNV_PRIME;
				return this;
			}

			@Override
			public Hasher putInt( int i ) {
				hash = ( hash ^ (byte) ( i ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( i >>> 8 ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( i >>> 16 ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( i >>> 24 ) ) * FNV_PRIME;
				return this;
			}

			@Override
			public Hasher putLong( long l ) {
				hash = ( hash ^ (byte) ( l ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( l >>> 8 ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( l >>> 16 ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( l >>> 24 ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( l >>> 32 ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( l >>> 40 ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( l >>> 48 ) ) * FNV_PRIME;
				hash = ( hash ^ (byte) ( l >>> 56 ) ) * FNV_PRIME;
				return this;
			}

			@Override
			public Hasher putFloat( float f ) {
				return putInt( Float.floatToRawIntBits( f ) );
			}

			@Override
			public Hasher putDouble( double d ) {
				return putLong( Double.doubleToRawLongBits( d ) );
			}

			@Override
			public Hasher putBoolean( boolean b ) {
				return putByte( b ? (byte) 1 : (byte) 0 );
			}

			@Override
			public Hasher putChar( char c ) {
				return putBytes( ByteBuffer.allocate( Chars.BYTES )
						.order( ByteOrder.LITTLE_ENDIAN )
						.putChar( c )
						.array() );
			}

			@Override
			public Hasher putUnencodedChars( CharSequence charSequence ) {
				ByteBuffer buffer = ByteBuffer.allocate( Chars.BYTES )
						.order( ByteOrder.LITTLE_ENDIAN );
				for ( int index = 0, length = charSequence.length(); index < length; index++ )
					buffer.putChar( charSequence.charAt( index ) );
				return putBytes( buffer.array() );
			}

			@Override
			public Hasher putString( CharSequence charSequence, Charset charset ) {
				return putBytes( charSequence.toString().getBytes( charset ) );
			}

			@Override
			public <T> Hasher putObject( T instance, Funnel<? super T> funnel ) {
				funnel.funnel( instance, this );
				return this;
			}

			@Override
			public HashCode hash() {
				return HashCode.fromInt( (int) hash );
			}

		};
	}

	@Override
	public HashCode hashInt( int input ) {
		return newHasher().putInt( input ).hash();
	}

	@Override
	public HashCode hashLong( long input ) {
		return newHasher().putLong( input ).hash();
	}

	@Override
	public HashCode hashBytes( byte[] input ) {
		return newHasher().putBytes( input ).hash();
	}

	@Override
	public HashCode hashBytes( byte[] input, int off, int len ) {
		return newHasher().putBytes( input, off, len ).hash();
	}

	@Override
	public HashCode hashBytes( ByteBuffer input ) {
		return newHasher().putBytes( input ).hash();
	}

	@Override
	public HashCode hashUnencodedChars( CharSequence input ) {
		return newHasher().putUnencodedChars( input ).hash();
	}

	@Override
	public HashCode hashString( CharSequence input, Charset charset ) {
		return newHasher().putString( input, charset ).hash();
	}

	@Override
	public <T> HashCode hashObject( T instance, Funnel<? super T> funnel ) {
		return newHasher().putObject( instance, funnel ).hash();
	}

	@Override
	public int bits() {
		return 32;
	}

}

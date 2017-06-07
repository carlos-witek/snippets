package org.carlos.hashing.fnv1a;

import static org.carlos.hashing.Bytes.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class HashFunctionSpeeds {

	public static void main( String[] args ) {
		int length = 10_000_000;
		for ( int j = 0; j < 5; j++ ) {

			HashFunction fnv1a = new FNV1A_32HashFunction();
			Set<Long> fnv1zHashSet = new HashSet<Long>();
			long fnv1aHashTime = 0;

			HashFunction murmur3 = Hashing.murmur3_32();
			Set<Long> murmur3HashSet = new HashSet<Long>();
			long murmur3Time = 0;

			for ( int i = 0; i < length; i++ ) {
				List<Integer> integers = integers();

				long currentTimeMillis = System.currentTimeMillis();
				long fnv1aHash = guavahash( fnv1a, integers );
				fnv1aHashTime += System.currentTimeMillis() - currentTimeMillis;

				fnv1zHashSet.add( fnv1aHash );

				currentTimeMillis = System.currentTimeMillis();
				long murmur3Hash = guavahash( murmur3, integers );
				murmur3Time += System.currentTimeMillis() - currentTimeMillis;

				murmur3HashSet.add( murmur3Hash );
			}

			System.out.print( length / fnv1aHashTime + " " + fnv1aHashTime + " " + fnv1zHashSet.size() );
			System.out.print( " | " );
			System.out.print( length / murmur3Time + " " + murmur3Time + " " + murmur3HashSet.size() );
			System.out.println();

		}
	}

	static int guavahashx( HashFunction hfunction, List<Integer> integers ) {
		return hfunction.newHasher().putBytes( all( integers ) ).hash().asInt();
	}

	static int guavahash( HashFunction hfunction, List<Integer> integers ) {
		Hasher hasher = hfunction.newHasher();
		for ( Integer integer : integers )
			hasher.putInt( integer );
		return hasher.hash().asInt();
	}

	private static List<Integer> integers() {
		Random random = new Random( System.currentTimeMillis() );

		List<Integer> list = new ArrayList<>();
		for ( int i = 0; i < 10; i++ )
			list.add( random.nextInt( Integer.MAX_VALUE ) );

		return list;
	}
}

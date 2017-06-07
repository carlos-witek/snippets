package org.carlos.hashing.fnv1a;

import static org.carlos.hashing.Bytes.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class FNV1A_32HashFunctionTest {

	FNV1A_32HashFunction fnv = new FNV1A_32HashFunction();

	@Test
	public void testCollisions() {

		int depth = 2;
		int space = 1024;

		int expected = 1;
		for ( int i = 0; i < depth; i++ )
			expected *= space;

		Map<Integer, Integer> scan = scan( new HashMap<>(), new LinkedList<>(), depth, space );
		Map<Integer, Integer> scan2 = scan2( new HashMap<>(), new LinkedList<>(), depth, space );

		System.out.println( expected + " " + scan.size() + " " + scan2.size() );

		assertThat( scan.size(), IsEqual.equalTo( 723702 ) );
		assertThat( scan2.size(), IsEqual.equalTo( expected ) );
	}

	private Map<Integer, Integer> scan( Map<Integer, Integer> map, LinkedList<Integer> integers,
			int depth, int space ) {
		if ( integers.size() == depth ) {
			int hash = fnv.newHasher().putBytes( all( integers ) ).hash().asInt();

			Integer integer = map.get( hash );
			map.put( hash, Integer.valueOf( ( integer == null ? 0 : integer.intValue() ) + 1 ) );
		} else {
			for ( int i = 1; i <= space; i++ ) {
				integers.addLast( i );
				scan( map, integers, depth, space );
				integers.removeLast();
			}
		}
		return map;
	}

	private Map<Integer, Integer> scan2( Map<Integer, Integer> map, LinkedList<Integer> integers,
			int depth, int space ) {
		if ( integers.size() == 2 * depth ) {
			int hash = fnv.newHasher().putBytes( all( integers ) ).hash().asInt();

			Integer integer = map.get( hash );
			map.put( hash, Integer.valueOf( ( integer == null ? 0 : integer.intValue() ) + 1 ) );
		} else {
			integers.addLast( Integer.MAX_VALUE - integers.size() );
			for ( int i = 1; i <= space; i++ ) {
				integers.addLast( i );
				scan2( map, integers, depth, space );
				integers.removeLast();
			}
			integers.removeLast();
		}
		return map;
	}

	@Test
	public void testConsistency() {
		assertEquals( fnv.newHasher().putBytes( all( new Integer( 123456 ) ) ).hash().asInt(),
				fnv.newHasher().putBytes( all( new Integer( 123456 ) ) ).hash().asInt() );
	}

}

package org.carlos.codewars;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MorseCodeDecoder {
	public static String decode( String morseCode ) {
		return Stream.of( morseCode.trim().split( "   " ) )
				.map( string -> Stream.of( string.split( " " ) )
						.map( code -> MorseCode.get( code ) )
						.filter( Objects::nonNull )
						.collect( Collectors.joining() ) )
				.collect( Collectors.joining( " " ) );
	}

	public static void main( String[] args ) {
		System.out.println( MorseCodeDecoder.decode( ".... . -.--   .--- ..- -.. ." ) );
		System.out.println( MorseCodeDecoder.decode( "------" ) );
	}
}

class MorseCode {

	public static String get( String morseCode ) {
		switch ( morseCode ) {
		case "....":
			return "H";
		case ".":
			return "E";
		case "-.--":
			return "Y";
		case ".---":
			return "J";
		case "..-":
			return "U";
		case "-..":
			return "D";
		}
		return morseCode;

	}

}

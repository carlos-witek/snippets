package org.carlos.various.groupid;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

import com.google.common.io.Files;

public class GroupId {

	public static void main( String[] args ) throws IOException {
		File file = new File( "/Users/carlos/git-public/snippets/various/src/main/java/org/carlos/various/groupid/GroupId.txt" );
		BufferedReader newReader = Files.newReader( file, Charset.forName( "UTF-8" ) );

		Set<String> sets = new HashSet<String>();

		String readLine = null;
		while ( ( readLine = newReader.readLine() ) != null ) {
			String substring = readLine.substring( readLine.indexOf( "<groupId>" ) );
			if ( substring.contains( "com.fanduel" ) )
				sets.add( substring );
		}

		for ( String string : sets ) {
			System.out.println( string );
		}
	}
}

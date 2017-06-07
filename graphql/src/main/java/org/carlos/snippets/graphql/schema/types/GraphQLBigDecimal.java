package org.carlos.snippets.graphql.schema.types;

import java.math.BigDecimal;
import java.math.RoundingMode;

import graphql.language.FloatValue;
import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

public class GraphQLBigDecimal extends GraphQLScalarType {

	public static final GraphQLBigDecimal GraphQLBigDecimal = new GraphQLBigDecimal();

	private static BigDecimal scale( BigDecimal bigDecimal ) {
		return bigDecimal.setScale( 2, RoundingMode.DOWN );
	}

	private GraphQLBigDecimal() {
		super( "BigDecimal", "BigDecimal", new Coercing<Object, BigDecimal>() {

			@Override
			public BigDecimal serialize( Object input ) {
				if ( input instanceof Integer ) {
					return scale( new BigDecimal( ( (Integer) input ).intValue() ) );
				} else if ( input instanceof Long ) {
					return scale( new BigDecimal( ( (Long) input ).longValue() ) );
				} else if ( input instanceof Float ) {
					return scale( new BigDecimal( ( (Float) input ).doubleValue() ) );
				} else if ( input instanceof Double ) {
					return scale( new BigDecimal( ( (Double) input ).doubleValue() ) );
				} else if ( input instanceof String ) {
					return scale( new BigDecimal( (String) input ) );
				} else if ( input instanceof BigDecimal ) {
					return scale( (BigDecimal) input );
				}
				return null;
			}

			@Override
			public Object parseValue( Object input ) {
				return serialize( input );
			}

			@Override
			public Object parseLiteral( Object input ) {
				if ( input instanceof IntValue ) {
					return scale( new BigDecimal( ( (IntValue) input ).getValue() ) );
				} else if ( input instanceof FloatValue ) {
					return scale( ( (FloatValue) input ).getValue() );
				} else if ( input instanceof StringValue ) {
					return scale( new BigDecimal( ( (StringValue) input ).getValue() ) );
				}
				return null;
			}
		} );
	}

}

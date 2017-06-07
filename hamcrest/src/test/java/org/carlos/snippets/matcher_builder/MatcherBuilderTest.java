package org.carlos.snippets.matcher_builder;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

public class MatcherBuilderTest {

	public void testBuilderPreventsAliasesForIgnoredAttributes() {
		// who cares
	}

	public void testBuilderPreventsIgnoringAllAttributes() {
		// who cares
	}

	@Test
	public void testBuilderCanHandleNullValues() {
		BasicEntity entity = new BasicEntity();
		entity.setBooleanAttr( null );
		assertThat( entity, MatcherBuilder.builder()
				.hasProperty( "booleanAttr", (Object) null )
				.build() );
	}

	@Test
	public void testEqualToEntityOnPrimitives() {
		PrimitiveEntity primitiveEntity = new PrimitiveEntity();
		PrimitiveEntity equivPrimitiveEntity = new PrimitiveEntity();

		assertThat( primitiveEntity, MatcherBuilder.samePropertyValuesAs( equivPrimitiveEntity )
				.build() );

		for ( PrimitiveEntity diffPrimitiveEntity : generateDiffForEachAttr( primitiveEntity ) ) {
			assertThat( primitiveEntity,
					not( MatcherBuilder.samePropertyValuesAs( diffPrimitiveEntity ) ) );
		}
	}

	@Test
	public void testEqualToEntityOnBasicTypes() {
		BasicEntity entity = new BasicEntity();
		BasicEntity equivEntity = new BasicEntity();

		assertThat( entity, MatcherBuilder.samePropertyValuesAs( equivEntity ).build() );
		for ( BasicEntity diffEntity : generateDiffForEachAttr( entity ) ) {
			assertThat( entity, not( MatcherBuilder.samePropertyValuesAs( diffEntity ).build() ) );
		}
	}

	@Test
	public void testEqualToEntityOnComparableEntities() {

		Date date = to( 2001, 1, 1, 17, 23, 2, 22 );
		Date equivDate = to( 2001, 1, 1, 17, 23, 2, 22 );
		Date diffDate = to( 2001, 1, 1, 17, 23, 2, 26 );

		ComparableEntity entity = new ComparableEntity();
		entity.setDateAttr( date );

		ComparableEntity equivEntity = new ComparableEntity();
		equivEntity.setDateAttr( equivDate );

		ComparableEntity diffEntity = new ComparableEntity();
		diffEntity.setDateAttr( diffDate );

		assertThat( entity, MatcherBuilder.samePropertyValuesAs( equivEntity ).build() );
		assertThat( entity, not( MatcherBuilder.samePropertyValuesAs( diffEntity ).build() ) );
	}

	private Date to( int year, int month, int day, int hour, int minute, int second, int millisecond ) {
		Calendar calendar = Calendar.getInstance();
		calendar.set( Calendar.YEAR, year );
		calendar.set( Calendar.MONTH, month );
		calendar.set( Calendar.DAY_OF_MONTH, day );
		calendar.set( Calendar.HOUR_OF_DAY, hour );
		calendar.set( Calendar.MINUTE, minute );
		calendar.set( Calendar.SECOND, second );
		calendar.set( Calendar.MILLISECOND, millisecond );
		return calendar.getTime();
	}

	@Test
	public void testEqualToEntityOnNestedEntities() {
		NestedEntity nestedEntity = new NestedEntity();
		NestedEntity equivNestedEntity = new NestedEntity();
		NestedEntity diffNestedEntity = new NestedEntity();
		BasicEntity diffBasicEntity = new BasicEntity();
		diffBasicEntity.setBooleanAttr( !diffBasicEntity.getBooleanAttr() );
		diffNestedEntity.setBasicEntityAttr( diffBasicEntity );

		assertThat( nestedEntity, MatcherBuilder.samePropertyValuesAs( equivNestedEntity ).build() );
		assertThat( nestedEntity, not( MatcherBuilder.samePropertyValuesAs( diffNestedEntity )
				.build() ) );
	}

	@Test
	public void testEqualToEntityIgnoringAttr() {
		NestedEntity nestedEntity = new NestedEntity();
		NestedEntity diffNestedEntity = new NestedEntity();
		BasicEntity diffBasicEntity = new BasicEntity();
		diffBasicEntity.setBooleanAttr( !diffBasicEntity.getBooleanAttr() );
		diffNestedEntity.setBasicEntityAttr( diffBasicEntity );
		diffNestedEntity.setBooleanAttr( !diffNestedEntity.getBooleanAttr() );

		assertThat( nestedEntity, MatcherBuilder.samePropertyValuesAs( diffNestedEntity )
				.excludeProperties( "booleanAttr", "basicEntityAttr" )
				.build() );
	}

	@Test
	public void testEqualToEntityIgnoringAttrs() {
		NestedEntity nestedEntity = new NestedEntity();
		NestedEntity diffNestedEntity = new NestedEntity();
		BasicEntity diffBasicEntity = new BasicEntity();
		diffBasicEntity.setBooleanAttr( !diffBasicEntity.getBooleanAttr() );
		diffNestedEntity.setBasicEntityAttr( diffBasicEntity );
		diffNestedEntity.setBooleanAttr( !diffNestedEntity.getBooleanAttr() );

		assertThat( nestedEntity, MatcherBuilder.samePropertyValuesAs( diffNestedEntity )
				.excludeProperties( "booleanAttr", "basicEntityAttr" )
				.build() );
	}

	@Test
	public void testEqualToEntityWithCustomMatcher() {
		NestedEntity nestedEntity = new NestedEntity();
		NestedEntity equivNestedEntity = new NestedEntity();

		assertThat( nestedEntity, MatcherBuilder.samePropertyValuesAs( equivNestedEntity ).build() );

		// As default matcher for entity replaced with equalTo, 
		// despite being equal, as not same instance, will fail
		assertThat( nestedEntity, not( MatcherBuilder.samePropertyValuesAs( equivNestedEntity )
				.hasProperty( "basicEntityAttr", equalTo( new BasicEntity() ) )
				.build() ) );
	}

	@Test
	public void testEqualToEntityDisregardsAliases() {
		// sounds like inconsistent behavior
	}

	@Test
	public void testSameAttributesAsEntity() {
		NestedEntity nestedEntity = new NestedEntity();
		OtherNestedEntity equivNestedEntity = new OtherNestedEntity();

		// As NestedEnity doesn't have toBeIgnoredDoubleAttr, will fail
		assertThat( nestedEntity, not( MatcherBuilder.samePropertyValuesAs( equivNestedEntity )
				.mapProperty( "otherNameForStringAttr", "stringAttr" )
				.build() ) );
	}

	@Test
	public void testSameAttributesAsEntityIgnoringAttr() {
		NestedEntity nestedEntity = new NestedEntity();
		OtherNestedEntity equivNestedEntity = new OtherNestedEntity();

		// As NestedEnity doesn't have toBeIgnoredDoubleAttr and 
		// otherNameForStringAttr is named differently, would have failed without ignore
		assertThat( nestedEntity, MatcherBuilder.samePropertyValuesAs( equivNestedEntity )
				.mapProperty( "otherNameForStringAttr", "stringAttr" )
				.excludeProperties( "toBeIgnoredDoubleAttr", "otherNameForStringAttr" )
				.build() );
	}

	@Test
	public void testSameAttributesAsEntityIgnoringAttrs() {
		NestedEntity nestedEntity = new NestedEntity();
		OtherNestedEntity equivNestedEntity = new OtherNestedEntity();

		// As NestedEnity doesn't have toBeIgnoredDoubleAttr and 
		// otherNameForStringAttr is named differently, would have failed without ignore
		assertThat( nestedEntity, MatcherBuilder.samePropertyValuesAs( equivNestedEntity )
				.mapProperty( "otherNameForStringAttr", "stringAttr" )
				.excludeProperties( "toBeIgnoredDoubleAttr", "otherNameForStringAttr" )
				.build() );
	}

	@Test
	public void testSameAttributesAsEntityUsingAlias() {
		NestedEntity nestedEntity = new NestedEntity();
		OtherNestedEntity equivNestedEntity = new OtherNestedEntity();

		// As NestedEnity doesn't have toBeIgnoredDoubleAttr would have failed without ignore
		// As otherNameForStringAttr is named differently would have failed without alias
		assertThat( nestedEntity, MatcherBuilder.samePropertyValuesAs( equivNestedEntity )
				.mapProperty( "otherNameForStringAttr", "stringAttr" )
				.excludeProperties( "toBeIgnoredDoubleAttr" )
				.build() );

		// Just making sure fails without ignore
		assertThat( nestedEntity, not( MatcherBuilder.samePropertyValuesAs( equivNestedEntity )
				.mapProperty( "otherNameForStringAttr", "stringAttr" )
				.build() ) );
	}

	private List<PrimitiveEntity> generateDiffForEachAttr( PrimitiveEntity primitiveEntity ) {
		PrimitiveEntity diffBoolPrimitiveEntity = new PrimitiveEntity();
		diffBoolPrimitiveEntity.setBooleanAttr( !primitiveEntity.isBooleanAttr() );

		PrimitiveEntity diffCharPrimitiveEntity = new PrimitiveEntity();
		diffCharPrimitiveEntity.setCharAttr( Character.isAlphabetic( primitiveEntity.getCharAttr() ) ? '0' : 'a' );

		PrimitiveEntity diffBytePrimitiveEntity = new PrimitiveEntity();
		diffBytePrimitiveEntity.setByteAttr( (byte) ( primitiveEntity.getByteAttr() + 1 ) );

		PrimitiveEntity diffShortPrimitiveEntity = new PrimitiveEntity();
		diffShortPrimitiveEntity.setShortAttr( (short) ( primitiveEntity.getShortAttr() + 1 ) );

		PrimitiveEntity diffIntPrimitiveEntity = new PrimitiveEntity();
		diffIntPrimitiveEntity.setIntegerAttr( primitiveEntity.getIntegerAttr() + 1 );

		PrimitiveEntity diffLongPrimitiveEntity = new PrimitiveEntity();
		diffLongPrimitiveEntity.setLongAttr( primitiveEntity.getLongAttr() + 1L );

		PrimitiveEntity diffFloatPrimitiveEntity = new PrimitiveEntity();
		diffFloatPrimitiveEntity.setFloatAttr( primitiveEntity.getFloatAttr() + 1.1f );

		PrimitiveEntity diffDoublePrimitiveEntity = new PrimitiveEntity();
		diffDoublePrimitiveEntity.setDoubleAttr( primitiveEntity.getDoubleAttr() + 1.1 );

		return Lists.newArrayList( diffBoolPrimitiveEntity, diffCharPrimitiveEntity,
				diffBytePrimitiveEntity, diffShortPrimitiveEntity, diffIntPrimitiveEntity,
				diffLongPrimitiveEntity, diffFloatPrimitiveEntity, diffDoublePrimitiveEntity );
	}

	private List<BasicEntity> generateDiffForEachAttr( BasicEntity entity ) {
		BasicEntity diffBooEntity = new BasicEntity();
		diffBooEntity.setBooleanAttr( !entity.getBooleanAttr() );

		BasicEntity diffChaEntity = new BasicEntity();
		diffChaEntity.setCharAttr( Character.isAlphabetic( entity.getCharAttr() ) ? '0' : 'a' );

		BasicEntity diffBytEntity = new BasicEntity();
		diffBytEntity.setByteAttr( (byte) ( entity.getByteAttr() + 1 ) );

		BasicEntity diffShorEntity = new BasicEntity();
		diffShorEntity.setShortAttr( (short) ( entity.getShortAttr() + 1 ) );

		BasicEntity diffInEntity = new BasicEntity();
		diffInEntity.setIntegerAttr( entity.getIntegerAttr() + 1 );

		BasicEntity diffLonEntity = new BasicEntity();
		diffLonEntity.setLongAttr( entity.getLongAttr() + 1L );

		BasicEntity diffFloaEntity = new BasicEntity();
		diffFloaEntity.setFloatAttr( entity.getFloatAttr() + 1.1f );

		BasicEntity diffDoublEntity = new BasicEntity();
		diffDoublEntity.setDoubleAttr( entity.getDoubleAttr() + 1.1 );

		BasicEntity diffStringEntity = new BasicEntity();
		diffStringEntity.setStringAttr( entity.getStringAttr() + "!" );

		return Lists.newArrayList( diffBooEntity, diffChaEntity, diffBytEntity, diffShorEntity,
				diffInEntity, diffLonEntity, diffFloaEntity, diffDoublEntity, diffStringEntity );
	}

	public static class OtherNestedEntity {
		private Boolean booleanAttr = true;
		private String otherNameForStringAttr = "string";
		private Double toBeIgnoredDoubleAttr = 1.2;
		private BasicEntity basicEntityAttr = new BasicEntity();

		public Boolean getBooleanAttr() {
			return booleanAttr;
		}

		public void setBooleanAttr( Boolean booleanAttr ) {
			this.booleanAttr = booleanAttr;
		}

		public String getOtherNameForStringAttr() {
			return otherNameForStringAttr;
		}

		public void setOtherNameForStringAttr( String otherNameForStringAttr ) {
			this.otherNameForStringAttr = otherNameForStringAttr;
		}

		public Double getToBeIgnoredDoubleAttr() {
			return toBeIgnoredDoubleAttr;
		}

		public void setToBeIgnoredDoubleAttr( Double toBeIgnoredDoubleAttr ) {
			this.toBeIgnoredDoubleAttr = toBeIgnoredDoubleAttr;
		}

		public BasicEntity getBasicEntityAttr() {
			return basicEntityAttr;
		}

		public void setBasicEntityAttr( BasicEntity basicEntityAttr ) {
			this.basicEntityAttr = basicEntityAttr;
		}
	}

	public static class NestedEntity {
		private Boolean booleanAttr = true;
		private String stringAttr = "string";
		private Long notInOtherLongAttr = 2L;
		private BasicEntity basicEntityAttr = new BasicEntity();

		public Boolean getBooleanAttr() {
			return booleanAttr;
		}

		public void setBooleanAttr( Boolean booleanAttr ) {
			this.booleanAttr = booleanAttr;
		}

		public String getStringAttr() {
			return stringAttr;
		}

		public void setStringAttr( String stringAttr ) {
			this.stringAttr = stringAttr;
		}

		public Long getNotInOtherLongAttr() {
			return notInOtherLongAttr;
		}

		public void setNotInOtherLongAttr( Long notInOtherLongAttr ) {
			this.notInOtherLongAttr = notInOtherLongAttr;
		}

		public BasicEntity getBasicEntityAttr() {
			return basicEntityAttr;
		}

		public void setBasicEntityAttr( BasicEntity basicEntityAttr ) {
			this.basicEntityAttr = basicEntityAttr;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper( this )
					.add( "booleanAttr", booleanAttr )
					.add( "stringAttr", stringAttr )
					.add( "notInOtherLongAttr", notInOtherLongAttr )
					.add( "basicEntityAttr", basicEntityAttr )
					.toString();
		}
	}

	public static class ComparableEntity extends BasicEntity {
		private Date dateAttr = new Date();
		private BigInteger bigDecimal = new BigInteger( "1" );

		public Date getDateAttr() {
			return dateAttr;
		}

		public void setDateAttr( Date dateAttr ) {
			this.dateAttr = dateAttr;
		}

		public BigInteger getBigDecimal() {
			return bigDecimal;
		}

		public void setBigDecimal( BigInteger bigDecimal ) {
			this.bigDecimal = bigDecimal;
		}
	}

	public static class BasicEntity {
		private Boolean booleanAttr = true;
		private Character charAttr = 'a';
		private Byte byteAttr = 'b';
		private Short shortAttr = 1;
		private Integer integerAttr = 2;
		private Long longAttr = 3L;
		private Float floatAttr = 4.4f;
		private Double doubleAttr = 5.5;
		private String stringAttr = "string";

		public Boolean getBooleanAttr() {
			return booleanAttr;
		}

		public void setBooleanAttr( Boolean booleanAttr ) {
			this.booleanAttr = booleanAttr;
		}

		public Character getCharAttr() {
			return charAttr;
		}

		public void setCharAttr( Character charAttr ) {
			this.charAttr = charAttr;
		}

		public Byte getByteAttr() {
			return byteAttr;
		}

		public void setByteAttr( Byte byteAttr ) {
			this.byteAttr = byteAttr;
		}

		public Short getShortAttr() {
			return shortAttr;
		}

		public void setShortAttr( Short shortAttr ) {
			this.shortAttr = shortAttr;
		}

		public Integer getIntegerAttr() {
			return integerAttr;
		}

		public void setIntegerAttr( Integer integerAttr ) {
			this.integerAttr = integerAttr;
		}

		public Long getLongAttr() {
			return longAttr;
		}

		public void setLongAttr( Long longAttr ) {
			this.longAttr = longAttr;
		}

		public Float getFloatAttr() {
			return floatAttr;
		}

		public void setFloatAttr( Float floatAttr ) {
			this.floatAttr = floatAttr;
		}

		public Double getDoubleAttr() {
			return doubleAttr;
		}

		public void setDoubleAttr( Double doubleAttr ) {
			this.doubleAttr = doubleAttr;
		}

		public String getStringAttr() {
			return stringAttr;
		}

		public void setStringAttr( String stringAttr ) {
			this.stringAttr = stringAttr;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper( this )
					.add( "booleanAttr", booleanAttr )
					.add( "charAttr", charAttr )
					.add( "byteAttr", byteAttr )
					.add( "shortAttr", shortAttr )
					.add( "integerAttr", integerAttr )
					.add( "longAttr", longAttr )
					.add( "floatAttr", floatAttr )
					.add( "doubleAttr", doubleAttr )
					.add( "stringAttr", stringAttr )
					.toString();
		}
	}

	public static class PrimitiveEntity {
		private boolean booleanAttr = true;
		private char charAttr = 'a';
		private byte byteAttr = 'b';
		private short shortAttr = 1;
		private int integerAttr = 2;
		private long longAttr = 3L;
		private float floatAttr = 4.4f;
		private double doubleAttr = 5.5;

		public boolean isBooleanAttr() {
			return booleanAttr;
		}

		public void setBooleanAttr( boolean booleanAttr ) {
			this.booleanAttr = booleanAttr;
		}

		public char getCharAttr() {
			return charAttr;
		}

		public void setCharAttr( char charAttr ) {
			this.charAttr = charAttr;
		}

		public byte getByteAttr() {
			return byteAttr;
		}

		public void setByteAttr( byte byteAttr ) {
			this.byteAttr = byteAttr;
		}

		public short getShortAttr() {
			return shortAttr;
		}

		public void setShortAttr( short shortAttr ) {
			this.shortAttr = shortAttr;
		}

		public int getIntegerAttr() {
			return integerAttr;
		}

		public void setIntegerAttr( int integerAttr ) {
			this.integerAttr = integerAttr;
		}

		public long getLongAttr() {
			return longAttr;
		}

		public void setLongAttr( long longAttr ) {
			this.longAttr = longAttr;
		}

		public float getFloatAttr() {
			return floatAttr;
		}

		public void setFloatAttr( float floatAttr ) {
			this.floatAttr = floatAttr;
		}

		public double getDoubleAttr() {
			return doubleAttr;
		}

		public void setDoubleAttr( double doubleAttr ) {
			this.doubleAttr = doubleAttr;
		}
	}
}

package org.carlos.snippets.matcher_builder;

import static org.hamcrest.beans.PropertyUtil.NO_ARGUMENTS;
import static org.hamcrest.beans.PropertyUtil.propertyDescriptorsFor;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

public class JavaBeansMapper<F> {

	public static <F> JavaBeansMapper<F> from( F from ) {
		return new JavaBeansMapper<F>( from );
	}

	private F from;
	private Map<String, String> mappingPropertyNames = new HashMap<>();

	private JavaBeansMapper( F from ) {
		this.from = from;
	}

	public JavaBeansMapper<F> mappingProperty( String fromPropertyName, String toPropertyName ) {
		Preconditions.checkNotNull( fromPropertyName, "fromPropertyName is null" );
		Preconditions.checkNotNull( toPropertyName, "toPropertyName is null" );
		mappingPropertyNames.put( fromPropertyName, toPropertyName );
		return this;
	}

	public <T> T to( Class<T> toClass ) {
		return to( toClass, true );
	}

	public <T> T to( Class<T> toClass, boolean skip ) {
		try {
			T toInstance = toClass.newInstance();
			PropertyDescriptor[] fromDescriptors = propertyDescriptorsFor( from, Object.class );
			PropertyDescriptor[] toDescriptors = propertyDescriptorsFor( toInstance, Object.class );

			Map<String, PropertyDescriptor> map = Stream.of( fromDescriptors ).collect(
					Collectors.toMap( new Function<PropertyDescriptor, String>() {
						@Override
						public String apply( PropertyDescriptor t ) {
							String mappedPropertyName = mappingPropertyNames.get( t.getName() );
							return mappedPropertyName == null ? t.getName() : mappedPropertyName;
						}
					}, Function.identity() ) );

			for ( PropertyDescriptor toDescriptor : toDescriptors ) {
				PropertyDescriptor fromDescriptor = map.get( toDescriptor.getName() );
				Preconditions.checkState( fromDescriptor != null,
						String.format( "property %s was not matched", toDescriptor.getName() ) );
				toDescriptor.getWriteMethod().invoke( toInstance,
						fromDescriptor.getReadMethod().invoke( from, NO_ARGUMENTS ) );
			}

			return toInstance;
		} catch ( Exception e ) {
			throw new IllegalStateException( e );
		}
	}

}

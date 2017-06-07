package org.carlos.jmx;

public class Hello implements HelloMBean {

	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public void sayHello() {
		System.out.println( String.format( "Hello, %s!", name ) );
	}

}

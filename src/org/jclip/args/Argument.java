package org.jclip.args;

public class Argument
{
	public String arg;
	public String bridge;
	public String prefix;
	public String key;
	public String value;

	public Argument(String prefix, String flag)
	{

	}

	public Argument(String arg)
	{
		this.arg = arg;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public void setBridge(String bridge)
	{
		this.bridge = bridge;
	}
}

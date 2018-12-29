package demo.function.sm4;

public class SM4_Context
{
	public int mode; // encrypt/decrypt
	
	public long[] sk; //SM4 subkeys

	public boolean isPadding;

	public SM4_Context() 
	{
		this.mode = 1;
		this.isPadding = true;
		this.sk = new long[32];
	}
}

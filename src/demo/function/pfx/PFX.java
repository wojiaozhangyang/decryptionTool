package demo.function.pfx;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.crypto.SecretKey;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import demo.function.sm4.Util;
import doubleca.security.provider.DoubleCA;



public class PFX {
	
	private static final String SIGNATURE_KEY_ALGORITHM = "SM2";
	private static final int SIGNATURE_KEY_SIZE = 256;
	private static final String NAME_OF_CERT_FOLDER="cert";
	private static final String NAME_OF_PRIVATEKEY_FILE="privateKey.txt";
	
	public static String analysePFXInfo(String name,String word, byte[] data)throws Exception{
		//String foldername1="";
		
		String result=null;
		Security.addProvider(new DoubleCA());
		InputStream input = new ByteArrayInputStream(data);
		KeyStore ks=null;
		try {
			ks = KeyStore.getInstance("DCKS");			
		    ks.load(input, word.toCharArray());
		    Enumeration e = ks.aliases();
		    
		    File folder1=new File(name);
		    if(!folder1.exists()){
		    	folder1.mkdir();
		    }
		    
		    String folder2Name=name+"/"+NAME_OF_CERT_FOLDER;
		    File folder2=new File(folder2Name);
		    if(!folder2.exists()){
		    	folder2.mkdir();
		    }
		    
		    File file=new File(name+"/"+NAME_OF_PRIVATEKEY_FILE);
		    if(!file.exists()){
		    	file.createNewFile();
		    }
		    BufferedWriter writer1=new BufferedWriter( new FileWriter(file));
		    
		    while (e.hasMoreElements())
			{
				String alias = (String) e.nextElement();
				System.out.println("alias : " + alias);
				if (ks.isKeyEntry(alias))
				{
					// key
					Key key=null;
					try {
						key = ks.getKey(alias, word.toCharArray());
					} catch (UnrecoverableKeyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						//return null;
					}
					//System.out.println("Key Type : " + key.getFormat());
					//System.out.println("Key Algorithm : " + key.getAlgorithm());
					if (key instanceof PrivateKey)
					{
						//BigInteger b=new BigInteger(1, key.getEncoded());
						//System.out.println("PrivateKey Value : " +  b.toString(16));
						String value=getPrivateValue(key);
						Certificate cert = ks.getCertificate(alias);
						System.out.println("Certificate Value : " + cert);
						
						String strCert=Base64.encode(cert.getEncoded());
						
						String tmpFileName=folder2Name+"/"+alias+".cer";
						File tmpFile=new File(tmpFileName);
					    if(!tmpFile.exists()){
					    	tmpFile.createNewFile();
					    }
					    BufferedWriter writer2=new BufferedWriter( new FileWriter(tmpFile));
					    writer2.write(strCert);
					    writer2.close();
					   
					    
					    String lineContent=alias+".cer "+value;
						writer1.write(lineContent);
						writer1.newLine();
					}
					
				}
			    result=name;			
			}
		    writer1.close();

		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return null;
		}
		return result;
	}
	
	
	/**
	 * 从key中提取公钥的信息
	 * @param key
	 * @return
	 */
	public static String getPublicValue(KeyPair key){
		
		BigInteger p = new BigInteger(1, key.getPublic().getEncoded());
		String str=p.toString(16);
//		String str="3059301306072a8648ce3d020106082a811ccf5501822d034200046e7a87575bcf4965819f0934be8b61c57cfed14f591c5a738f6f68159257fffef04cf16ec3bc834f3cccf51f2d4ce066a6aa75f0ccdd3a7cc60820fffb323ccb";
		System.out.println("source_public:["+str+"]");
		//曲线信息长度
		String len=str.charAt(6)+""+str.charAt(7);		
		int len_num=Util.hexStringToAlgorism(len);//hexString转化成int.
		int index=(4+len_num+4)*2;
		
		//公钥长度
		String len_pubKey=str.charAt(index-6)+""+str.charAt(index-5);
		int len_pubKey_num=Util.hexStringToAlgorism(len_pubKey);
		if(len_pubKey_num==66){
			String value=str.substring(index, str.length());
			System.out.println("public:["+value+"]");
			return value;
			
		}else{
			System.out.println("illegal SM2 public key. ");
			return null;
		}
		
	}

	
	/**
	 * 从PFX中提取私钥的信息中获取私钥的值  
	 * @param key
	 * @return
	 */
	public static String getPrivateValue(Key key){
		
		BigInteger p = new BigInteger(1, key.getEncoded());
		String str=p.toString(16);
		System.out.println("source_private:["+str+"]");
		
		//曲线信息长度
		int index=12;
		String len=str.charAt(index)+""+str.charAt(index+1);		
		int len_num=Util.hexStringToAlgorism(len);//hexString转化成int.
		System.out.println("曲线长度:"+len+","+len_num);
	    index+=(1+len_num)*2;
		
	    index+=8*2;
		//私钥长度
		String len_priKey=str.charAt(index)+""+str.charAt(index+1);
		int len_pubKey_num=Util.hexStringToAlgorism(len_priKey);
		System.out.println("长度:"+len_priKey+","+len_pubKey_num);
		index+=2;
		if(len_pubKey_num==32){
			String value=str.substring(index, index+64);
			System.out.println("私钥值=["+value+"],长度="+value.length());
			return value;
			
		}else{
			System.out.println("illegal SM2 private key. ");
			return null;
		}
	}
	
	/**
	 * description:read film 
	 * @param path
	 * @return byte[]
	 */
	private static byte[] readFile_BYTE(String path){ 
		File file = new File(path);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return filecontent;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String a="c:\\sdf\\dfs.d";
		String[] aa=a.split("\\\\");
		String[] aaa=aa[aa.length-1].split("\\.");
		System.out.println(a);
		System.out.println(aa[0]);
		System.out.println(aa[aa.length-1]);
		System.out.println(aaa[0]);
	}

}

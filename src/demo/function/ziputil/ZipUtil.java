package demo.function.ziputil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;  
import java.util.zip.CRC32;  
import java.util.zip.CheckedOutputStream;  
import java.util.zip.ZipEntry;  
import java.util.zip.ZipFile;  
import java.util.zip.ZipOutputStream; 

import org.apache.commons.lang3.StringUtils;

import Decoder.BASE64Decoder;
import doubleca.security.provider.DoubleCA;



public class ZipUtil {
	
	private static final String NAME_OF_PUBLICKEY_FILE="publicKey.txt";
	private static final String NAME_OF_CERT_FOLDER="cert";
	/** 
     * 解压缩zip包 
     * @param zipFilePath zip文件的全路径 
     * @param unzipFilePath 解压后的文件保存的路径 
     * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含 
     */  
    @SuppressWarnings("unchecked")  
    public static String unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception  
    {  
        if (StringUtils.isEmpty(zipFilePath) || StringUtils.isEmpty(unzipFilePath))  
        {  
            //throw new ParameterException(ICommonResultCode.PARAMETER_IS_NULL);   
        	System.out.println("文件内容为空!");
        	return null;
        }  
        File zipFile = new File(zipFilePath);  
        //如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径  
        if (includeZipFileName)  
        {  
            String fileName = zipFile.getName();  
            if (StringUtils.isNotEmpty(fileName))  
            {  
                fileName = fileName.substring(0, fileName.lastIndexOf("."));  
            }  
            unzipFilePath = unzipFilePath + File.separator + fileName;  
        }  
        //创建解压缩文件保存的路径  
        File unzipFileDir = new File(unzipFilePath);  
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory())  
        {  
            unzipFileDir.mkdirs();  
        }  
          
        //开始解压  
        ZipEntry entry = null;  
        String entryFilePath = null, entryDirPath = null;  
        File entryFile = null, entryDir = null;  
        int index = 0, count = 0, bufferSize = 1024;  
        byte[] buffer = new byte[bufferSize];  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
        ZipFile zip = new ZipFile(zipFile);  
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zip.entries();  
        //循环对压缩包里的每一个文件进行解压       
        while(entries.hasMoreElements())  
        {  
            entry = entries.nextElement();  
            //构建压缩包中一个文件解压后保存的文件全路径  
            entryFilePath = unzipFilePath + File.separator + entry.getName();  
            //构建解压后保存的文件夹路径  
            index = entryFilePath.lastIndexOf(File.separator);  
            if (index != -1)  
            {  
                entryDirPath = entryFilePath.substring(0, index);  
            }  
            else  
            {  
                entryDirPath = "";  
            }             
            entryDir = new File(entryDirPath);  
            //如果文件夹路径不存在，则创建文件夹  
            if (!entryDir.exists() || !entryDir.isDirectory())  
            {  
                entryDir.mkdirs();  
            }  
              
            //创建解压文件  
            entryFile = new File(entryFilePath);  
            if (entryFile.exists())  
            {  
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException  
                SecurityManager securityManager = new SecurityManager();  
                securityManager.checkDelete(entryFilePath);  
                //删除已存在的目标文件  
                entryFile.delete();   
            }  
              
            //写入文件  
            bos = new BufferedOutputStream(new FileOutputStream(entryFile));  
            bis = new BufferedInputStream(zip.getInputStream(entry));  
            while ((count = bis.read(buffer, 0, bufferSize)) != -1)  
            {  
                bos.write(buffer, 0, count);  
            }  
            bos.flush();  
            bos.close();              
        }  
        return unzipFilePath;
    }  

	
	public static String analyZip(String name,String zipPath)throws Exception{
		
		String result=null;
		 String rootpath=null;
		 File folder1=new File(name);
		 if(!folder1.exists()){
		    folder1.mkdir();
		 }
		 try {
			rootpath=unzip(zipPath,name+"/"+NAME_OF_CERT_FOLDER,false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rootpath==null) {
			return null;
		}
		 
		 File file1=new File(name+"/"+NAME_OF_PUBLICKEY_FILE);
		 if(!file1.exists()){
		    try {
				file1.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 BufferedWriter writer1=new BufferedWriter( new FileWriter(file1));
 
		 File root = new File(rootpath);
		 File[] files = root.listFiles();
		 Security.addProvider(new DoubleCA());	
		 for(File file:files){     
		     if(file.isFile()){
		    	 String fileName=file.getName();
		    	 Long filelength = file.length();  
		         byte[] filecontent = new byte[filelength.intValue()];  
		         String readLine=null;
		         
                 FileInputStream in = new FileInputStream(file);  
		         in.read(filecontent);  
		         in.close();  
		        
		         readLine=new String(filecontent, "UTF-8");
		         BASE64Decoder decoder = new BASE64Decoder();
		 		byte[] byteCert = decoder.decodeBuffer(readLine);
		 		ByteArrayInputStream bain = new ByteArrayInputStream(byteCert);		
		 		CertificateFactory cf = CertificateFactory.getInstance("X.509", DoubleCA.PROVIDER_NAME);
		 		X509Certificate cert = (X509Certificate)cf.generateCertificate(bain);
		 		String value=getPublicValue(cert.getPublicKey());
		 		if(value==null){
		 			return null;
		 		}
		 	    readLine=fileName+" "+value;
		 	    writer1.write(readLine);
		 	    writer1.newLine();
		     }
		 }
		 writer1.close();
		 return name;
		 
	}
public static String getPublicValue(PublicKey key){
		
		BigInteger p = new BigInteger(1, key.getEncoded());
		String str=p.toString(16);
//		String str="3059301306072a8648ce3d020106082a811ccf5501822d034200046e7a87575bcf4965819f0934be8b61c57cfed14f591c5a738f6f68159257fffef04cf16ec3bc834f3cccf51f2d4ce066a6aa75f0ccdd3a7cc60820fffb323ccb";
//		System.out.println("source_public:["+str+"]");
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			analyZip("111","d://2018112816424090.zip");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

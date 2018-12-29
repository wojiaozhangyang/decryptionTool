package demo.window;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import demo.function.sm4.SM4Utils;

public class Test  {

	
    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
    	 File file = new File("d://2018112816424090.zip");  
         Long filelength = file.length();  
         byte[] filecontent = new byte[filelength.intValue()];  
           
         FileInputStream in = new FileInputStream(file);  
         in.read(filecontent);  
         in.close();  
       
         SM4Utils sm4 = new SM4Utils();
 		 sm4.secretKey = "1111111111111112";  //meF8U9wHFOMfs2Y9
 		 sm4.hexString = false;
 		
 		 System.out.println("ECB模式");
         byte[] cryptTxt=sm4.encryptData_ECB_BYTE(filecontent);
         
         File file1=new File("d://2018112816424090.zip.anwei");
         if(!file1.exists()){  
        	 file1.createNewFile();  
         }  
         FileOutputStream out=new FileOutputStream(file1);
         out.write(cryptTxt);
         out.close();

    }

    
         
}

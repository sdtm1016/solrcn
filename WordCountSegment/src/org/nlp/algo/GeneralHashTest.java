package org.nlp.algo;
/*
 **************************************************************************
 *                                                                        *
 *           General Purpose Hash Function Algorithms Test                *
 *                                                                        *
 * Author: Arash Partow - 2002                                            *
 * URL: http://www.partow.net                                             *
 * URL: http://www.partow.net/programming/hashfunctions/index.html        *
 *                                                                        *
 * Copyright notice:                                                      *
 * Free use of the General Purpose Hash Function Algorithms Library is    *
 * permitted under the guidelines and in accordance with the most current *
 * version of the Common Public License.                                  *
 * http://www.opensource.org/licenses/cpl.php                             *
 *                                                                        *
 **************************************************************************
*/



import java.io.IOException;


public class GeneralHashTest
{

   public static void main(String args[]) throws IOException
   {

      GeneralHashFunctionLibrary ghl = new GeneralHashFunctionLibrary();

      String key = "abcdefghijklmnopqrstuvwxyz1234567890";

      System.out.println("General Purpose Hash Function Algorithms Test");
      System.out.println("By Arash Partow - 2002\n");
      System.out.println("Key: " + key);
      System.out.println(" 1. RS-Hash Function Value:   " + ghl.RSHash(key));
      System.out.println(" 2. JS-Hash Function Value:   " + ghl.JSHash(key));
      System.out.println(" 3. PJW-Hash Function Value:  " + ghl.PJWHash(key));
      System.out.println(" 4. ELF-Hash Function Value:  " + ghl.ELFHash(key));
      System.out.println(" 5. BKDR-Hash Function Value: " + ghl.BKDRHash(key));
      System.out.println(" 6. SDBM-Hash Function Value: " + ghl.SDBMHash(key));
      System.out.println(" 7. DJB-Hash Function Value:  " + ghl.DJBHash(key));
      System.out.println(" 8. DEK-Hash Function Value:  " + ghl.DEKHash(key));
      System.out.println(" 9. BP-Hash Function Value:   " + ghl.BPHash(key));
      System.out.println(" 9. FNV-Hash Function Value:  " + ghl.FNVHash(key));
      System.out.println(" 9. FNV-Hash Function Value:  " + ghl.FNVHash(key.getBytes()));      
      System.out.println("10. AP-Hash Function Value:   " + ghl.APHash(key));
      System.out.println("11. FNV-Hash1 32bit Function Value:   " + ghl.FNVHash1(key));
      System.out.println("11. FNV-Hash1 32bit Function Value:   " + ghl.FNVHash1(key.getBytes()));
      System.out.println("Press 'ENTER' to exit...");

      

      long s = System.currentTimeMillis();
      for (int i=0;i<10000000;i++){
    	  ghl.FNVHash1(key); 
      }
      System.out.println(System.currentTimeMillis()-s);
      
      
      byte[] k = key.getBytes();
      long t = System.currentTimeMillis();
      for (int i=0;i<10000000;i++){
    	  ghl.FNVHash1(k); 
      }
      System.out.println(System.currentTimeMillis()-t);          
      

     

  
   }


}

package com.cangjie.basetool.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;

import javax.crypto.Cipher;

/**
 * DES加密解密
 */
public class DESPlus {
     private static String strDefaultKey = "handtrip8520"; //默认密钥
     private Cipher encryptCipher = null;
     private Cipher decryptCipher = null;
 /**
  * 将byte数组转换为表�?16进制值的字符串， 如：byte[]{8,18}转换为：0813�? 和public static byte[] hexStr2ByteArr(String strIn) 互为可�?�的转换过程  * 
  * @param arrB �?要转换的byte数组
  * @return 转换后的字符�?
  */
 public static String byteArr2HexStr(byte[] arrB) throws Exception {
  int iLen = arrB.length;  
  StringBuffer sb = new StringBuffer(iLen * 2);// 每个byte用两个字符才能表示，�?以字符串的长度是数组长度的两�?
  for (int i = 0; i < iLen; i++)
  {
      int intTmp = arrB[i];   
      while (intTmp < 0)// 把负数转换为正数
      {
         intTmp = intTmp + 256;
      }   
      if (intTmp < 16)// 小于0F的数�?要在前面�?0
      {
         sb.append("0");
      }
      sb.append(Integer.toString(intTmp, 16));
  }
  return sb.toString();
 }

 /**
  * 将表�?16进制值的字符串转换为byte数组�? 和public static String byteArr2HexStr(byte[] arrB)互为可�?�的转换过程
  * @param strIn �?要转换的字符�?
  * @return 转换后的byte数组
  * @throws Exception 本方法不处理任何异常，所有异常全部抛�?
  */
 public static byte[] hexStr2ByteArr(String strIn) throws Exception {
     byte[] arrB = strIn.getBytes();
     int iLen = arrB.length;    
     byte[] arrOut = new byte[iLen / 2]; // 两个字符表示�?个字节，�?以字节数组长度是字符串长度除�?2
     for (int i = 0; i < iLen; i = i + 2)
     {
       String strTmp = new String(arrB, i, 2);
       arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
     }
     return arrOut;
 }

 /**
  * 默认构�?�方法，使用默认密钥
  * @throws Exception
  */
 public DESPlus() throws Exception {
  this(strDefaultKey);
 }

 /**
  * 指定密钥构�?�方�?
  * 
  * @param strKey
  *            指定的密�?
  * @throws Exception
  */
 public DESPlus(String strKey) throws Exception {
//  Security.addProvider(new com.sun.crypto.provider.SunJCE());
  Key key = getKey(strKey.getBytes());
  encryptCipher = Cipher.getInstance("DES");
  encryptCipher.init(Cipher.ENCRYPT_MODE, key);

  decryptCipher = Cipher.getInstance("DES");
  decryptCipher.init(Cipher.DECRYPT_MODE, key);
 }

 /**
  * 加密字节数组
  * 
  * @param arrB�?加密的字节数�?
  * @return 加密后的字节数组
  * @throws Exception
  */
 public byte[] encrypt(byte[] arrB) throws Exception {
  return encryptCipher.doFinal(arrB);
 }

 /**
  * 加密字符�?
  * @param strIn �?加密的字符串
  * @return 加密后的字符�?
  * @throws Exception
  */
 public String encrypt(String strIn) throws Exception {
  return byteArr2HexStr(encrypt(strIn.getBytes()));
 }

 /**
  * 解密字节数组
  * 
  * @param arrB �?解密的字节数�?
  * @return 解密后的字节数组
  * @throws Exception
  */
 public byte[] decrypt(byte[] arrB) throws Exception {
  return decryptCipher.doFinal(arrB);
 }

 /**
  * 解密字符�?
  * 
  * @param strIn �?解密的字符串
  * @return 解密后的字符�?
  * @throws Exception
  */
 public String decrypt(String strIn) {
	 String result = null;
	 try{
		 result = new String(decrypt(hexStr2ByteArr(strIn)));
	 }catch(Exception e){
		 result = "--";
	 }
  return result;
 }

 /**
  * 从指定字符串生成密钥，密钥所�?的字节数组长度为8�? 不足8位时后面�?0，超�?8位只取前8�?
  * @param arrBTmp  构成该字符串的字节数�?
  * @return 生成的密�?
  * @throws Exception
  */
 private Key getKey(byte[] arrBTmp) throws Exception {
         byte[] arrB = new byte[8];// 创建�?个空�?8位字节数组（默认值为0�? 
         for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) // 将原始字节数组转换为8�?
         {
             arrB[i] = arrBTmp[i];
         }  
         Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");// 生成密钥
         return key;
 }

 public static void main(String[] args) {

  String test = "b455a3f9df6a6e70";
  String test2 = "111111";
  // DESPlus des = new DESPlus();//默认密钥
  DESPlus des;
  try {
   des = new DESPlus();
   // 自定义密�?
//   System.out.println("加密前的字符�?" + test);
   //System.out.println("加密后的字符�?" + des.decrypt(test));
   
   
   System.out.println("解密后的字符" + des.decrypt(test));
   System.out.println("加密后的字符" + des.encrypt(test2));
   //createKeyFile("c:\\","key.dat",des.encrypt(test));
   
//   System.out.println(readKeyFile("d:\\2009-07-16.dat"));
  } catch (Exception e) {
   e.printStackTrace();
  }

 }
 
 public static boolean createKeyFile(String path, String filename, String key) throws IOException {
	 File file = new File(path,filename);
	 if(!file.exists()){
		 file.createNewFile();
	 }
	 FileOutputStream out=new FileOutputStream(file,true);
	 out.write(key.getBytes("utf-8"));
	 out.close();
	 return true;
 }
 
 /**
  * 读取文件校对
  * @param path
  * @return
  * @throws IOException
  */
 public static String readKeyFile(String path) throws IOException {
	 File file=new File(path);
     if(!file.exists()||file.isDirectory())
         throw new FileNotFoundException();
     BufferedReader br=new BufferedReader(new FileReader(file));
     String temp=null;
     StringBuffer sb=new StringBuffer();
     temp=br.readLine();
     while(temp!=null){
         sb.append(temp);
         temp=br.readLine();
     }
     return sb.toString();
 }
 
}


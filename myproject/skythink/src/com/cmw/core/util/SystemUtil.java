package com.cmw.core.util;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemUtil {
	   
	public static final String OS_USERNAME = "user.name";
	/**
	 * 获取硬 盘上的所有盘符
	 * @return 返回字符串数组，此字符串数组的元素就是硬盘的所有盘符
	 */
	public final static String[] getWinOsRoots(){
		String[] winRoots = null;
		File[] files = File.listRoots();
		if(null == files || files.length == 0) return winRoots;
		winRoots = new String[files.length];
		int i=0;
		for(File file : files){
			String root = file.getPath();
			winRoots[i++] = root.replaceAll(":|[\\\\]", ""); 
		}
		return winRoots;
	}
	
	public final static String getOsName(){
		 String osName = System.getProperty("os.name"); //操作系统名称
		return osName;
	}
	
	/**
	 * 判断当前操作系统是否是Windows
	 * @return true : 是WINDOWS 操作系统, false : 非WINDOWS系统
	 */
	public final static boolean isWindows(){
		boolean flag = false;
		String osName = getOsName();
		if(!StringHandler.isValidStr(osName)) return false;
		osName = osName.toUpperCase();
		flag =(osName.indexOf("WINDOWS") != -1);
		return flag;
	}
	
	/**
	 * 判断当前操作系统是否是 Linux
	 * @return true : 是 Linux 操作系统, false : 非Linux系统
	 */
	public final static boolean isLinux(){
		boolean flag = false;
		String osName = getOsName();
		if(!StringHandler.isValidStr(osName)) return false;
		osName = osName.toUpperCase();
		flag =(osName.indexOf("LINUX") != -1);
		return flag;
	}
	
	/**
	 * 判断当前操作系统是否是 MAC OS 苹果操作系统
	 * @return true : 是 MAC OS 操作系统, false : 非MAC OS系统
	 */
	public final static boolean isMacOS(){
		boolean flag = false;
		String osName = getOsName();
		if(!StringHandler.isValidStr(osName)) return false;
		osName = osName.toUpperCase();
		flag =(osName.indexOf("MAC OS") != -1);
		return flag;
	}
	
	/**
	 * 获取 Windows 操作系统的安装目录 即: Windows 所在的目录
	 *  假如 Windows 操作系统安装在D盘,
	 *  那么，调用 getWinSetUpDir() 后 ---> D:\Windows
	 * @return 
	 */
	public final static String getWinSetUpDir(){
		String[] roots = getWinOsRoots();
		String winDir = null;
		for(String root : roots){
			winDir = root + ":\\Windows";
			File rootFile = new File(winDir);
			if(rootFile.exists() && rootFile.isDirectory()) return winDir;
		}
		return null;
	}
	/**
	 * 获取当前系统登录用户名
	 * @return 返回 当前系统登录用户名
	 */
	public final static String getOsAccountName(){
		String accountName = System.getProperty(OS_USERNAME);
		return accountName;
	}
	
	/**
	 * 获取当前计算机的IP地址
	 * @return	返回当前计算机的IP地址
	 * @throws UnknownHostException
	 */
	public final static String getIpAddress() throws UnknownHostException {  
        InetAddress address = InetAddress.getLocalHost();  
        return address.getHostAddress();  
    }  
	
	  /** 
     * 知识的补充 
     *  
     * InetAddress 继承自 java.lang.Object类 
     * 它有两个子类：Inet4Address 和 Inet6Address 
     * 此类表示互联网协议 (IP) 地址。  
     *  
     * IP 地址是 IP 使用的 32 位或 128 位无符号数字， 
     * 它是一种低级协议，UDP 和 TCP 协议都是在它的基础上构建的。 
     *  
     * ************************************************ 
     * 主机名就是计算机的名字（计算机名），网上邻居就是根据主机名来识别的。 
     * 这个名字可以随时更改，从我的电脑属性的计算机名就可更改。 
     *  用户登陆时候用的是操作系统的个人用户帐号，这个也可以更改， 
     *  从控制面板的用户界面里改就可以了。这个用户名和计算机名无关。 
     */ 
    /** 
     * 获取本机的IP 
     * @return Ip地址 
     */ 
     public static String getLocalHostIP() { 
	      String ip; 
	      try { 
	           /**返回本地主机。*/ 
	           InetAddress addr = InetAddress.getLocalHost(); 
	           /**返回 IP 地址字符串（以文本表现形式）*/ 
	           ip = addr.getHostAddress();  
	      } catch(Exception ex) { 
	          ip = ""; 
	      } 
	       
	      return ip; 
     } 
      
     /** 
      * 或者主机名： 
      * @return 
      */ 
     public static String getLocalHostName() { 
          String hostName; 
          try { 
               /**返回本地主机。*/ 
               InetAddress addr = InetAddress.getLocalHost(); 
               /**获取此 IP 地址的主机名。*/ 
               hostName = addr.getHostName(); 
          }catch(Exception ex){ 
              hostName = ""; 
          } 
           
          return hostName; 
     } 
      
     /** 
      * 获得本地所有的IP地址 
      * @return 
      */ 
     public static String[] getAllLocalHostIP() { 
           
         String[] ret = null; 
          try { 
               /**获得主机名*/ 
               String hostName = getLocalHostName(); 
               if(hostName.length()>0) { 
                   /**在给定主机名的情况下，根据系统上配置的名称服务返回其 IP 地址所组成的数组。*/ 
                    InetAddress[] addrs = InetAddress.getAllByName(hostName); 
                    if(addrs.length>0) { 
                         ret = new String[addrs.length]; 
                         for(int i=0 ; i< addrs.length ; i++) { 
                             /**.getHostAddress()   返回 IP 地址字符串（以文本表现形式）。*/ 
                             ret[i] = addrs[i].getHostAddress(); 
                         } 
                    } 
               } 
                
          }catch(Exception ex) { 
              ret = null; 
          } 
           
          return ret; 
     } 
 

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		 String[] roots = getWinOsRoots();
		 String root = getOsAccountName();
		 String ip = getIpAddress();
		 System.out.println(isWindows());
	}
	
	/*
		public static String getProperty(String key)   
		键   相关值的描述  
		java.version    Java 运行时环境版本  
		java.vendor     Java 运行时环境供应商  
		java.vendor.url     Java 供应商的 URL  
		java.home   Java 安装目录  
		java.vm.specification.version   Java 虚拟机规范版本  
		java.vm.specification.vendor    Java 虚拟机规范供应商  
		java.vm.specification.name  Java 虚拟机规范名称  
		java.vm.version     Java 虚拟机实现版本  
		java.vm.vendor  Java 虚拟机实现供应商  
		java.vm.name    Java 虚拟机实现名称  
		java.specification.version  Java 运行时环境规范版本  
		java.specification.vendor   Java 运行时环境规范供应商  
		java.specification.name     Java 运行时环境规范名称  
		java.class.version  Java 类格式版本号  
		java.class.path     Java 类路径  
		java.library.path   加载库时搜索的路径列表  
		java.io.tmpdir  默认的临时文件路径  
		java.compiler   要使用的 JIT 编译器的名称  
		java.ext.dirs   一个或多个扩展目录的路径  
		os.name     操作系统的名称  
		os.arch     操作系统的架构  
		os.version  操作系统的版本  
		file.separator  文件分隔符（在 UNIX 系统中是“/”）  
		path.separator  路径分隔符（在 UNIX 系统中是“:”）  
		line.separator  行分隔符（在 UNIX 系统中是“/n”）  
		user.name   用户的账户名称  
		user.home   用户的主目录  
		user.dir    用户的当前工作目录  
	*/
   
      
}

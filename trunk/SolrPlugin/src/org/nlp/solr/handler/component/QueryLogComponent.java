/**
 * 
 */
package org.nlp.solr.handler.component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;

/**
 * 
 * @author longkeyy
 * 
 */
public class QueryLogComponent extends SearchComponent {

	public static final String COMPONENT_NAME = "QueryLog";
	public static final String LogFile = "QueryLog.log";

	
	 /** 
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true 
     *  
     * @param fileName 
     * @param content 
     */  
    public static void method1(String file, String conent) {  
        BufferedWriter out = null;  
        try {  
            out = new BufferedWriter(new OutputStreamWriter(  
                    new FileOutputStream(file, true)));  
            out.write(conent);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    } 
    
    
    /** 
     * 追加文件：使用FileWriter 
     *  
     * @param fileName 
     * @param content 
     */  
    public static void method2(String fileName, String content) {  
        try {  
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
            FileWriter writer = new FileWriter(fileName, true);  
            writer.write(content);  
            writer.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 
    
    /** 
     * 追加文件：使用RandomAccessFile 
     *  
     * @param fileName 
     *            文件名 
     * @param content 
     *            追加的内容 
     */  
    public static void method3(String fileName, String content) {  
        try {  
            // 打开一个随机访问文件流，按读写方式  
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");  
            // 文件长度，字节数  
            long fileLength = randomFile.length();  
            // 将写文件指针移到文件尾。  
            randomFile.seek(fileLength);  
            randomFile.writeBytes(content);  
            randomFile.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }     

	@Override
	public void process(ResponseBuilder rb) throws IOException {
		// do nothing
	}

	@Override
	public void prepare(ResponseBuilder rb) throws IOException {

		String queryString = rb.getQueryString();
		if (queryString == null) {
			// this is the normal way it's set.
			SolrParams params = rb.req.getParams();
			queryString = params.get(CommonParams.Q);
			System.out.println(queryString);
		} else {			
			System.out.println(queryString);
		}
		method1("method1_"+LogFile,queryString+"\r");
		method2("method2_"+LogFile,queryString+"\r");
		method3("method3_"+LogFile,queryString+"\r");
	}

	@Override
	public String getDescription() {
		return "QueryLogComponent";
	}

	@Override
	public String getSource() {
		return "";
	}

	@Override
	public String getVersion() {
		return "";
	}

}

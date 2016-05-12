package org.alexanderhs.PDFTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFUtil {
	/**
	 * 将图片转换为PDF
	 * @param imagePath image图片路径
	 * @param pdfPath PDF文件路径
	 * @throws IOException
	 */
	public static void imageToPDF(String imagePath,String pdfPath) throws IOException{
 		PDDocument doc = new PDDocument();
 		try{
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
            
            float w = pdImage.getWidth();
            float h = pdImage.getHeight();

            PDPage page = new PDPage(new PDRectangle (w,h));
            
            doc.addPage(page);
            
            PDPageContentStream contents = new PDPageContentStream(doc, page);
            
            contents.drawImage(pdImage, 0, 0);
            
            contents.close();
            doc.save(pdfPath);
        }finally{
            doc.close();
        }
 	}
 
 	/**
 	 * 合并若干PDF文件
 	 * @param destination 目标文档绝对路径
 	 * @param sources 待合并PDF路径集合
 	 * @throws IOException
 	 */
 	public static void mergePDFs(String destination,List<String> sources) throws IOException{
 		PDFMergerUtility pm=new PDFMergerUtility();
 		pm.setDestinationFileName(destination);
 		for (String source:sources){
 			pm.addSource(source);
 		}
 		pm.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());	
 	}
 	
 	/** 
 	 * 合并两个PDF文件 
 	 * @param destination
 	 * @param source
 	 * @throws IOException
 	 */
 	public static void merge2PDF(String destination,String source) throws IOException{
 		PDFMergerUtility pm=new PDFMergerUtility();
 		
 		pm.addSource(destination);
 		pm.addSource(source);
 		
 		pm.setDestinationFileName(destination);
 		pm.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());	
 	}
 	
 	/**
 	 * 向PDF中添加图片
 	 * @param pdfPath	PDF路径
 	 * @param imagePath	图片路径
 	 * @throws IOException
 	 */
 	public static void addImageToPDF(String pdfPath,String imagePath) throws IOException{
 		PDDocument doc = null;
 		try{
            doc = PDDocument.load(new File(pdfPath));
            
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
            
            float w = pdImage.getWidth();
            float h = pdImage.getHeight();

            PDPage page = new PDPage(new PDRectangle (w,h));
            
            doc.addPage(page);
            
            PDPageContentStream contents = new PDPageContentStream(doc, page);
            
            contents.drawImage(pdImage, 0, 0);
            
            contents.close();
            doc.save(pdfPath);

         }
        finally
        {
            if( doc != null )
            {
                doc.close();
            }
        }
 	}
 	
 	public static boolean isPDF(String filePath){
 		return filePath.endsWith(".pdf");
 	}
 	
 	public static boolean isImage(String filePath) {
 		return filePath.endsWith(".jpg");
 	}
 	
 	/**
 	 * 合并文档生成PDF（包含图片或PDF文件）
 	 * @param destination	生成的PDF文件路径
 	 * @param sources	图片或PDF路径集合
 	 * @throws IOException
 	 */
 	public static void mergeToPDF(String destination,List<String> sources) throws IOException{
 		// 创建空pdf
        PDDocument doc = new PDDocument();
        doc.save(destination);
        doc.close();
 		// 如果是图片则添加到pdf中，如果是pdf则执行合并操作
        for(String source:sources){
        	if(isPDF(source)){
        		merge2PDF(destination,source);
        	}else if(isImage(source)){
        		addImageToPDF(destination,source);
        	}
        }
 	}
 	
 	
 	
 	public static void main(String[] args) throws IOException{
	    List<String> sources=new ArrayList<String>();
	    sources.add("F:\\1.20160125业务建模\\业务建模pdf版\\查询业务流程图及实体.pdf");
	    sources.add("C:\\Users\\Administrator\\Desktop\\wallhaven-339672.jpg");
	    sources.add("F:\\1.20160125业务建模\\业务建模pdf版\\存货仓单登记业务流程图及实体.pdf");
	    mergeToPDF("C:\\Users\\Administrator\\Desktop\\测试.pdf",sources);
	}
}
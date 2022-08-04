package com.yogovi;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.WordDictionary;

public class Fc {

	public static void main(String[] args) {
		Path path = Paths.get(new File(Fc.class.getClassLoader().getResource("dicts/jieba.dict").getPath()).getAbsolutePath()) ;
	    //加载自定义的词典进词库
	    WordDictionary.getInstance().loadUserDict( path ) ;
	    JiebaSegmenter segmenter = new JiebaSegmenter();
		//江苏省南京市江宁区景枫中心
		//安徽省芜湖市无为县泥汊镇中心小学
		System.out.println(segMore(segmenter, "吴鑫鑫,18012989434,安徽省芜湖市无为市泥汊镇中心小学"));
	}
	public static String segMore(JiebaSegmenter segmenter, String text) {
	    return seg(segmenter, text, SegMode.SEARCH);
	}
	private static String seg(JiebaSegmenter segmenter, String text, SegMode segMode) {
	    StringBuilder result = new StringBuilder();                
	    for(SegToken token : segmenter.process(text, segMode)){
	        result.append(token.word).append("|");
	    }
	    return result.toString(); 
	}
	
}

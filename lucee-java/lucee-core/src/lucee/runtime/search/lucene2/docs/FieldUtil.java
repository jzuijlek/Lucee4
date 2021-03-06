/**
 *
 * Copyright (c) 2014, the Railo Company Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package lucee.runtime.search.lucene2.docs;

import lucee.commons.lang.StringUtil;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public class FieldUtil {

	public static Field UnIndexed(String name, String value) {
		return new Field(name,value,Field.Store.YES,Field.Index.NO);
	}

	public static Field Text(String name, String value) {//print.out("text:"+name);
		return new Field(name,value,Field.Store.YES,Field.Index.ANALYZED);
	}

	public static Field Text(String name, String value,boolean store) {
		return new Field(name,value,store?Field.Store.YES:Field.Store.NO,Field.Index.ANALYZED);
	}

	public static void setTitle(Document doc, String title) {
		if(!StringUtil.isEmpty(title))		doc.add(Text("title", title));
	}

	public static void setSummary(Document doc, String summary,boolean index) {
		if(!StringUtil.isEmpty(summary))	doc.add(index?Text("summary",summary):UnIndexed("summary",summary));
	}

	public static void setKeywords(Document doc, String keywords) {
		if(!StringUtil.isEmpty(keywords))	doc.add(Text("keywords", keywords));
	}

	public static void setAuthor(Document doc, String author) {
		if(!StringUtil.isEmpty(author))		doc.add(Text("author", author));
	}

	public static void setURL(Document doc, String urlpath) {
		if(!StringUtil.isEmpty(urlpath))		doc.add(Text("url", urlpath));
	}
	public static void setCustom(Document doc, String custom, int index) {
		if(!StringUtil.isEmpty(custom))		doc.add(Text("custom"+index, custom));
	}

	public static void setContent(Document doc, String content) {
		if(!StringUtil.isEmpty(content))	doc.add(Text("contents", content));
	}

	public static void setRaw(Document doc, String raw) {
		//doc.add(new Field("raw",raw,Field.Store.YES,Field.Index.NO));
	}

	public static void setMimeType(Document doc, String mimeType) {
		if(!StringUtil.isEmpty(mimeType))	doc.add(FieldUtil.UnIndexed("mime-type", mimeType));
	}

	
}

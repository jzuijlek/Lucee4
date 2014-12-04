/**
 *
 * Copyright (c) 2014, the Railo Company LLC. All rights reserved.
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
package lucee.transformer.cfml.evaluator.impl;

import lucee.commons.io.CharsetUtil;
import lucee.commons.lang.StringUtil;
import lucee.runtime.config.Config;
import lucee.runtime.config.ConfigImpl;
import lucee.runtime.engine.ThreadLocalPageContext;
import lucee.runtime.exp.TemplateException;
import lucee.transformer.bytecode.Page;
import lucee.transformer.bytecode.Statement;
import lucee.transformer.bytecode.cast.CastBoolean;
import lucee.transformer.bytecode.cast.CastString;
import lucee.transformer.bytecode.expression.Expression;
import lucee.transformer.bytecode.literal.LitString;
import lucee.transformer.bytecode.statement.tag.Attribute;
import lucee.transformer.bytecode.statement.tag.Tag;
import lucee.transformer.bytecode.statement.tag.TagLoop;
import lucee.transformer.bytecode.util.ASMUtil;
import lucee.transformer.cfml.Data;
import lucee.transformer.cfml.ExprTransformer;
import lucee.transformer.cfml.TransfomerSettings;
import lucee.transformer.cfml.evaluator.EvaluatorException;
import lucee.transformer.cfml.evaluator.EvaluatorSupport;
import lucee.transformer.library.function.FunctionLib;
import lucee.transformer.library.tag.TagLib;
import lucee.transformer.library.tag.TagLibTag;
import lucee.transformer.util.CFMLString;

public final class Loop extends EvaluatorSupport {
	

	public TagLib execute(Config config,Tag tag, TagLibTag libTag, FunctionLib[] flibs,Data data) throws TemplateException {
		TagLoop loop=(TagLoop) tag;
		// label
		try {
			if(ASMUtil.isLiteralAttribute(tag, "label", ASMUtil.TYPE_STRING, false, true)) {
				LitString ls=(LitString) CastString.toExprString(tag.getAttribute("label").getValue());
				String l = ls.getString();
				if(!StringUtil.isEmpty(l,true)) {
					loop.setLabel(l.trim());
					tag.removeAttribute("label");
				}
			}
		}
		catch (EvaluatorException e) {
			throw new TemplateException(null, e);
		}
	    return null;
	}

	/**
	 *
	 * @see lucee.transformer.cfml.evaluator.EvaluatorSupport#evaluate(lucee.transformer.bytecode.statement.tag.Tag, lucee.transformer.library.tag.TagLibTag, lucee.transformer.library.function.FunctionLib[])
	 */
	public void evaluate(Tag tag,TagLibTag tagLibTag,FunctionLib[] flibs) throws EvaluatorException {
		TagLoop loop=(TagLoop) tag;
		
		// attribute maxrows and endrow not allowd at the same time
        if(tag.containsAttribute("maxrows") && tag.containsAttribute("endrow"))
        	throw new EvaluatorException("Wrong Context, you cannot use attribute maxrows and endrow at the same time.");
        
		// file loop      
        if(tag.containsAttribute("file")) {
            if(!tag.containsAttribute("index") && !tag.containsAttribute("item"))
                throw new EvaluatorException("Wrong Context, when you use attribute file you must also use attribute index and/or item");
            loop.setType(TagLoop.TYPE_FILE);
            return;
        }
        // list loop
        if(tag.containsAttribute("list")){
			if(!tag.containsAttribute("index") && !tag.containsAttribute("item"))
				throw new EvaluatorException("Wrong Context, when you use attribute list,you must define attribute index and/or item");
			loop.setType(TagLoop.TYPE_LIST);
            return;
		}
        // array loop
        if(tag.containsAttribute("array")){
			if(!tag.containsAttribute("index") && !tag.containsAttribute("item"))
				throw new EvaluatorException("Wrong Context, when you use attribute array, you must define attribute index and/or item");
			loop.setType(TagLoop.TYPE_ARRAY);
            return;
		}

        // array loop
        if(tag.containsAttribute("times")) {
        	
			if(tag.getAttributes().size()>1)
				throw new EvaluatorException("Wrong Context, when you use attribute times, no other attributes are allowed");
			loop.setType(TagLoop.TYPE_TIMES);
            return;
		}

        // struct loop      
        if(tag.containsAttribute("struct")) {
        	if(!tag.containsAttribute("index") && !tag.containsAttribute("item"))
				throw new EvaluatorException("Wrong Context, when you use attribute struct,you must define attribute index and/or item");
			loop.setType(TagLoop.TYPE_STRUCT);
            return;
        }

        // collection loop      
        if(tag.containsAttribute("collection")) {
        	if(!tag.containsAttribute("index") && !tag.containsAttribute("item"))
				throw new EvaluatorException("Wrong Context, when you use attribute collection,you must define attribute index and/or item");
			loop.setType(TagLoop.TYPE_COLLECTION);
            return;
        }
		// index loop	
		/*if(tag.containsAttribute("index")) {
			if(!tag.containsAttribute("from") || !tag.containsAttribute("to"))
				throw new EvaluatorException("Wrong Context, when you use attribute index you must also use attribute from and to or list or file");
			loop.setType(TagLoop.TYPE_INDEX);
            return;
		}*/
		if(tag.containsAttribute("from") || tag.containsAttribute("to")) {
			if(!tag.containsAttribute("from"))
				throw new EvaluatorException("Wrong Context, when you use attribute to, you must also use attribute from.");
			if(!tag.containsAttribute("to"))
				throw new EvaluatorException("Wrong Context, when you use attribute from, you must also use attribute to.");
			if(!tag.containsAttribute("index") && !tag.containsAttribute("item"))
				throw new EvaluatorException("Wrong Context, when you use attribute from and to, you must define attribute index or item.");
			
			if(tag.containsAttribute("index") && tag.containsAttribute("item"))
				throw new EvaluatorException("For this type of loop, you cannot use attribute index and item at the same time.");
			
			
			loop.setType(TagLoop.TYPE_FROM_TO);
            return;
		}
		
		
		// condition loop
		if(tag.containsAttribute("condition")){
			if(tag.isScriptBase())
				throw new EvaluatorException("tag loop-condition is not supported within cfscript, use instead a while statement.");
			
			TagLib tagLib=tagLibTag.getTagLib();
			ExprTransformer transformer;
			String text=ASMUtil.getAttributeString(tag, "condition");

			try {
				ConfigImpl config=(ConfigImpl) ThreadLocalPageContext.getConfig();
				
				transformer = tagLib.getExprTransfomer();
				Page page = ASMUtil.getAncestorPage(tag);
				Expression expr=transformer.transform(ASMUtil.getAncestorPage(tag),null,null,flibs,config.getCoreTagLib().getScriptTags(),new CFMLString(text,CharsetUtil.UTF8),TransfomerSettings.toSetting(page.getPageSource().getMapping(),null));
				tag.addAttribute(new Attribute(false,"condition",CastBoolean.toExprBoolean(expr),"boolean"));
			}
			catch (Exception e) {
				throw new EvaluatorException(e.getMessage());
			}
			loop.setType(TagLoop.TYPE_CONDITION);
            return;
		}
		// query loop
		if(tag.containsAttribute("query")){
			loop.setType(TagLoop.TYPE_QUERY);
            return;
		}
		Info info=getParentInfo(loop);
		// query group
		if(tag.containsAttribute("group") && info.hasParentWithQuery){
			loop.setType(TagLoop.TYPE_GROUP);
			return;
		}
		
		if(info.hasParentWithQuery) {
        	if(info.hasParentWithGroup) loop.setType(TagLoop.TYPE_INNER_GROUP);
        	else loop.setType(TagLoop.TYPE_INNER_QUERY);
        	return;
        }
        /*
         if(hasQuery) 
        	output.setType(TagOutput.TYPE_QUERY);
        
        else if(tag.containsAttribute("group") && hasParentWithQuery)
        	output.setType(TagOutput.TYPE_GROUP);
        
        else if(hasParentWithQuery) {
        	if(hasParentWithGroup) output.setType(TagOutput.TYPE_INNER_GROUP);
        	else output.setType(TagOutput.TYPE_INNER_QUERY);
        }
        else
        	 output.setType(TagOutput.TYPE_NORMAL);
        
       
         */
        
		loop.setType(TagLoop.TYPE_NOTHING);
		//throw new EvaluatorException("Wrong Context, invalid attributes in tag cfloop");
		
	}

	private Info getParentInfo(TagLoop loop) {
		
        // check if inside a query tag
		TagLoop parent = loop;
		Info info=new Info();
		info.hasParentWithGroup=false;
		info.hasParentWithQuery=false;
		//boolean hasQuery=loop.containsAttribute("query");
		
		while((parent=getParentTagLoop(parent))!=null) {
            if(!info.hasParentWithQuery)info.hasParentWithQuery=parent.hasQuery();
            if(!info.hasParentWithGroup)info.hasParentWithGroup=parent.hasGroup();
            if(info.hasParentWithQuery && info.hasParentWithGroup)break;
		}
		return info;
	}
	

	
	private static TagLoop getParentTagLoop(TagLoop stat) {
		Statement parent = stat;
		while(true)	{
			parent=parent.getParent();
			if(parent==null)return null;
			if(parent instanceof TagLoop)	return (TagLoop) parent;
		}
	}
	
	class Info {
		private boolean hasParentWithGroup=false;
		private boolean hasParentWithQuery=false;
	}
}

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

import lucee.transformer.bytecode.statement.tag.Tag;
import lucee.transformer.bytecode.util.ASMUtil;
import lucee.transformer.cfml.evaluator.EvaluatorException;
import lucee.transformer.cfml.evaluator.EvaluatorSupport;
import lucee.transformer.library.tag.TagLibTag;

public final class PDFParam extends EvaluatorSupport {


	/**
	 * @see lucee.transformer.cfml.evaluator.EvaluatorSupport#evaluate(org.w3c.dom.Element, lucee.transformer.library.tag.TagLibTag)
	 */
	public void evaluate(Tag tag,TagLibTag libTag) throws EvaluatorException { 
		String ns=libTag.getTagLib().getNameSpaceAndSeparator();
			String httpName=ns+"pdf";
		
		// check if tag is direct inside if
		if(!ASMUtil.hasAncestorTag(tag,httpName))
			throw new EvaluatorException("Wrong Context, tag "+libTag.getFullName()+" must be inside a "+httpName+" tag");	
	}

}
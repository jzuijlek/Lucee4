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
package lucee.runtime.tag;


import java.io.IOException;

import lucee.commons.lang.StringUtil;
import lucee.runtime.PageContextImpl;
import lucee.runtime.exp.ApplicationException;
import lucee.runtime.exp.PageException;

/**
* Writes the text specified in the text attribute to the 'head' section of a generated HTML page. 
* 	 The cfhtmlhead tag can be useful for embedding CSS code, or placing other HTML tags such, as
* 	 META, LINK, TITLE, or BASE in an HTML page header.
*/
public final class HtmlHead extends HtmlHeadBodyBase {

	public String getTagName() {
		return "htmlhead";
	}

	public void actionAppend() throws IOException, ApplicationException {

		((PageContextImpl) pageContext).getRootOut().appendHTMLHead(text);
	}

	public void actionWrite() throws IOException, ApplicationException {

		((PageContextImpl) pageContext).getRootOut().writeHTMLHead(text);
	}

	public void actionReset() throws IOException {

		((PageContextImpl) pageContext).getRootOut().resetHTMLHead();
	}

	public void actionRead() throws PageException, IOException {

		String str = ((PageContextImpl) pageContext).getRootOut().getHTMLHead();
		pageContext.setVariable(!StringUtil.isEmpty(variable,true) ? variable : "cfhtmlhead", str);
	}

	public void actionFlush() throws IOException {

		((PageContextImpl) pageContext).getRootOut().flushHTMLHead();
	}

}
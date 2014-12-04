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
package lucee.runtime.functions.system;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import lucee.commons.io.IOUtil;
import lucee.commons.io.res.util.ResourceUtil;
import lucee.runtime.PageContext;

public class IsZipFile {

	public static boolean call(PageContext pc, String path) {
		InputStream is=null;
		boolean hasEntries=false;
		try {
			//ZipEntry ze;
			ZipInputStream zis = new ZipInputStream(is=ResourceUtil.toResourceExisting(pc, path).getInputStream());
			while ((zis.getNextEntry()) != null ) {
	        	zis.closeEntry();
	        	hasEntries=true;
	        }
		} catch (Exception e) {
			return false;
		}
		finally {
			IOUtil.closeEL(is);
		}
		return hasEntries;
	}
}

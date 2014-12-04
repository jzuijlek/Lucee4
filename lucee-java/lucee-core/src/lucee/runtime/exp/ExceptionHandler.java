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
package lucee.runtime.exp;

import java.io.PrintWriter;

import lucee.commons.io.log.LogUtil;
import lucee.runtime.PageContext;
import lucee.runtime.config.Config;
import lucee.runtime.config.ConfigImpl;
import lucee.runtime.engine.ThreadLocalPageContext;
import lucee.runtime.op.Caster;
import lucee.commons.io.log.Log;

/**
 * Handle Excpetions
 */
public final class ExceptionHandler {

	public static void log(Config config, Throwable t) {
		
		PageException pe=Caster.toPageException(t);
		//pe.printStackTrace(config.getErrWriter()); 
		
		// apllication Log
		//LogUtil.log(((ConfigImpl)config).getLog("application"),Log.LEVEL_ERROR, "",pe);
		
		// exception.log
		//String st = ExceptionUtil.getStacktrace(pe,true);
		LogUtil.log(((ConfigImpl)config).getLog("exception"),Log.LEVEL_ERROR, "",pe);
		
		
	}

	public static void printStackTrace(PageContext pc, Throwable t) {
		PrintWriter pw = (pc.getConfig()).getErrWriter();
		t.printStackTrace(pw);
		pw.flush();
	}

	public static void printStackTrace(Throwable t) {
		PageContext pc = ThreadLocalPageContext.get();
		if(pc!=null)printStackTrace(pc,t);
		else t.printStackTrace();
	}
}
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
package lucee.runtime.listener;

import java.io.IOException;

import lucee.runtime.CFMLFactory;
import lucee.runtime.PageContext;
import lucee.runtime.PageContextImpl;
import lucee.runtime.PageSource;
import lucee.runtime.exp.MissingIncludeException;
import lucee.runtime.exp.PageException;
import lucee.runtime.op.Caster;
import lucee.runtime.type.UDF;

public final class ClassicAppListener extends AppListenerSupport {

	private int mode=MODE_CURRENT2ROOT;

	@Override
	public void onRequest(PageContext pc,PageSource requestedPage, RequestListener rl) throws PageException {
		
		PageSource application=//pc.isCFCRequest()?null:
			AppListenerUtil.getApplicationPageSource(pc,requestedPage,mode,AppListenerUtil.TYPE_CLASSIC,null);
		
		_onRequest(pc, requestedPage, application,rl);
	}
	
	static void _onRequest(PageContext pc,PageSource requestedPage,PageSource application, RequestListener rl) throws PageException {
		((PageContextImpl)pc).setAppListenerType(AppListenerUtil.TYPE_CLASSIC);
		
		// on requestStart
		if(application!=null)pc.doInclude(new PageSource[]{application},false);
		
		if(rl!=null) {
			requestedPage=rl.execute(pc, requestedPage);
			if(requestedPage==null) return;
		}
		
		// request
		try{
			pc.doInclude(new PageSource[]{requestedPage},false);
		}
		catch(MissingIncludeException mie){
			ApplicationContext ac = pc.getApplicationContext();
			boolean rethrow=true;
			if(ac instanceof ClassicApplicationContext) {
				ClassicApplicationContext cfc=(ClassicApplicationContext) ac;
				UDF udf = cfc.getOnMissingTemplate();
				if(udf!=null) {
					String targetPage=requestedPage.getFullRealpath();
					rethrow=(!Caster.toBooleanValue(udf.call(pc, new Object[]{targetPage}, true),true));
				}
			}
			if(rethrow)throw mie;
		}
		
		// on Request End
		if(application!=null){
			PageSource onReqEnd = application.getRealPage("OnRequestEnd.cfm");
	        if(onReqEnd.exists())pc.doInclude(new PageSource[]{onReqEnd},false);
		}
	}

	@Override
	public boolean onApplicationStart(PageContext pc) throws PageException {
		// do nothing
		return true;
	}

	@Override
	public void onSessionStart(PageContext pc) throws PageException {
		// do nothing
	}

	@Override
	public void onApplicationEnd(CFMLFactory factory, String applicationName) throws PageException {
		// do nothing	
	}

	@Override
	public void onSessionEnd(CFMLFactory cfmlFactory, String applicationName, String cfid) throws PageException {
		// do nothing
	}

	@Override
	public void onDebug(PageContext pc) throws PageException {
		_onDebug(pc);
	}
	
	public static void _onDebug(PageContext pc) throws PageException {
		try {
			if(pc.getConfig().debug())pc.getDebugger().writeOut(pc);
		} 
		catch (IOException e) {
			throw Caster.toPageException(e);
		}
	}

	@Override
	public void onError(PageContext pc,PageException pe) {
		_onError(pc, pe);
	}

	public static void _onError(PageContext pc,PageException pe) {
		pc.handlePageException(pe);
	}

	@Override
	public void onTimeout(PageContext pc) {
		_onTimeout(pc);
	}
	public static void _onTimeout(PageContext pc) {
	}

	@Override
	public void setMode(int mode) {
		this.mode=mode;
	}

	@Override
	public int getMode() {
		return mode;
	}

	@Override
	public String getType() {
		return "classic";
	}
	
	@Override
	public boolean hasOnApplicationStart(){
		return false;
	}
	
	public static boolean _hasOnApplicationStart(){
		return false;
	}
	
	@Override
	public boolean hasOnSessionStart(PageContext pc){
		return false;
	}
	
	public static boolean _hasOnSessionStart(PageContext pc){
		return false;
	}
}
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

import java.util.Iterator;

import lucee.commons.io.SystemUtil;
import lucee.commons.lang.StringUtil;
import lucee.runtime.Page;
import lucee.runtime.PageContext;
import lucee.runtime.PageContextImpl;
import lucee.runtime.config.ConfigImpl;
import lucee.runtime.engine.ThreadLocalPageContext;
import lucee.runtime.exp.ApplicationException;
import lucee.runtime.exp.ExpressionException;
import lucee.runtime.exp.PageException;
import lucee.runtime.exp.SecurityException;
import lucee.runtime.ext.tag.BodyTagImpl;
import lucee.runtime.ext.tag.DynamicAttributes;
import lucee.runtime.op.Caster;
import lucee.runtime.spooler.ExecutionPlan;
import lucee.runtime.spooler.ExecutionPlanImpl;
import lucee.runtime.thread.ChildSpoolerTask;
import lucee.runtime.thread.ChildThread;
import lucee.runtime.thread.ChildThreadImpl;
import lucee.runtime.thread.ThreadUtil;
import lucee.runtime.thread.ThreadsImpl;
import lucee.runtime.type.Array;
import lucee.runtime.type.Collection;
import lucee.runtime.type.Collection.Key;
import lucee.runtime.type.KeyImpl;
import lucee.runtime.type.Struct;
import lucee.runtime.type.StructImpl;
import lucee.runtime.type.scope.Threads;
import lucee.runtime.type.util.ListUtil;

// MUST change behavor of mltiple headers now is a array, it das so?

/**
* Lets you execute HTTP POST and GET operations on files. Using cfhttp, you can execute standard 
*   GET operations and create a query object from a text file. POST operations lets you upload MIME file 
*   types to a server, or post cookie, formfield, URL, file, or CGI variables directly to a specified server.
*
*
*
* 
**/
public final class ThreadTag extends BodyTagImpl implements DynamicAttributes {
	
	private static final int ACTION_JOIN = 0;
	private static final int ACTION_RUN = 1;
	private static final int ACTION_SLEEP = 2;
	private static final int ACTION_TERMINATE = 3;
	
	private static final int TYPE_DAEMON = 0;
	private static final int TYPE_TASK = 1;
	private static final ExecutionPlan[] EXECUTION_PLAN = new ExecutionPlan[0];
	
	private int action=ACTION_RUN;
	private long duration=-1;
	private String name;
	private String lcName;
	private int priority=Thread.NORM_PRIORITY;
	private long timeout=0;
	private PageContext pc;
	private int type=TYPE_DAEMON;
	private ExecutionPlan[] plans=EXECUTION_PLAN;
	private Struct attrs;
	

	@Override
	public void release()	{
		super.release();
		action=ACTION_RUN;
		duration=-1;
		name=null;
		lcName=null;
		priority=Thread.NORM_PRIORITY;
		type=TYPE_DAEMON;
		plans=EXECUTION_PLAN;
		timeout=0;
		attrs=null;
		pc=null;
	}
	
	/**
	 * @param action the action to set
	 */
	public void setAction(String strAction) throws ApplicationException {
		String lcAction = strAction.trim().toLowerCase();
		
		if("join".equals(lcAction)) 			this.action=ACTION_JOIN;
		else if("run".equals(lcAction)) 		this.action=ACTION_RUN;
		else if("sleep".equals(lcAction)) 	this.action=ACTION_SLEEP;
		else if("terminate".equals(lcAction)) this.action=ACTION_TERMINATE;
		else 
			throw new ApplicationException("invalid value ["+strAction+"] for attribute action","values for attribute action are:join,run,sleep,terminate");
	}


	/**
	 * @param duration the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = (long) duration;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if(StringUtil.isEmpty(name,true)) return;
		this.name = name.trim();
		this.lcName=this.name.toLowerCase();
	}


	/**
	 * @param strPriority the priority to set
	 */
	public void setPriority(String strPriority) throws ApplicationException {
		int p = ThreadUtil.toIntPriority(strPriority);
		if(p==-1) {
			throw new ApplicationException("invalid value ["+strPriority+"] for attribute priority","values for attribute priority are:low,high,normal");
		}
		priority=p;
	}
	

	/**
	 * @param strType the type to set
	 * @throws ApplicationException 
	 * @throws SecurityException 
	 */
	public void setType(String strType) throws ApplicationException, SecurityException {
		strType=strType.trim().toLowerCase();

		if("task".equals(strType))	{
			// SNSN
			/*SerialNumber sn = pageContext.getConfig().getSerialNumber();
		    if(sn.getVersion()==SerialNumber.VERSION_COMMUNITY)
		         throw new SecurityException("no access to this functionality with the "+sn.getStringVersion()+" version of lucee");
		    */
			
			
			//throw new ApplicationException("invalid value ["+strType+"] for attribute type","task is not supported at the moment");
			type=TYPE_TASK;
		}
		else if("daemon".equals(strType) || "deamon".equals(strType))	type=TYPE_DAEMON;
		else throw new ApplicationException("invalid value ["+strType+"] for attribute type","values for attribute type are:task,daemon (default)");
		
	}
	
	public void setRetryintervall(Object obj) throws PageException {
		setRetryinterval(obj);
	}
	
	public void setRetryinterval(Object obj) throws PageException {
		if(StringUtil.isEmpty(obj))return;
		Array arr = Caster.toArray(obj,null);
		if(arr==null){
			plans=new ExecutionPlan[]{toExecutionPlan(obj,1)};
		}
		else {
			Iterator<Object> it = arr.valueIterator();
			plans=new ExecutionPlan[arr.size()];
			int index=0;
			while(it.hasNext()) {
				plans[index++]=toExecutionPlan(it.next(),index==1?1:0);
			}
		}
		
	}
	
	
	


	private ExecutionPlan toExecutionPlan(Object obj,int plus) throws PageException {

		if(obj instanceof Struct){
			Struct sct=(Struct)obj;
			// GERT
			
			// tries
			Object oTries=sct.get("tries",null);
			if(oTries==null)throw new ExpressionException("missing key tries inside struct");
			int tries=Caster.toIntValue(oTries);
			if(tries<0)throw new ExpressionException("tries must contain a none negative value");
			
			// interval
			Object oInterval=sct.get("interval",null);
			if(oInterval==null)oInterval=sct.get("intervall",null);
			
			if(oInterval==null)throw new ExpressionException("missing key interval inside struct");
			int interval=toSeconds(oInterval);
			if(interval<0)throw new ExpressionException("interval should contain a positive value or 0");
			
			
			return new ExecutionPlanImpl(tries+plus,interval);
		}
		return new ExecutionPlanImpl(1+plus,toSeconds(obj));
	}
	
	private int toSeconds(Object obj) throws PageException {
		return (int)Caster.toTimespan(obj).getSeconds();
	}
	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(double timeout) {
		this.timeout = (long)timeout;
	}

	@Override
	public void setDynamicAttribute(String uri, String name, Object value) {
		if(attrs==null)attrs=new StructImpl();
		Key key = KeyImpl.getInstance(StringUtil.trim(name,""));
		attrs.setEL(key,value);
	}

	@Override
	public void setDynamicAttribute(String uri, Collection.Key name, Object value) {
		if(attrs==null)attrs=new StructImpl();
		Key key = KeyImpl.getInstance(StringUtil.trim(name.getString(),""));
		attrs.setEL(key,value);
	}

	@Override
	public int doStartTag() throws PageException	{
		pc=pageContext;
		switch(action) {
			case ACTION_JOIN:	
				doJoin();
			break;
			case ACTION_SLEEP:	
				required("thread", "sleep", "duration", duration,-1);	
				doSleep();
			break;
			case ACTION_TERMINATE:	
				required("thread", "terminate", "name", name);
				doTerminate();
			break;
			case ACTION_RUN:		
				required("thread", "run", "name", name);
				return EVAL_BODY_INCLUDE;
			
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws PageException {
		this.pc=pageContext;
		//if(ACTION_RUN==action) doRun();
		return EVAL_PAGE;
	}
	
	public void register(Page currentPage, int threadIndex) throws PageException	{
		if(ACTION_RUN!=action) return;
		
		if(((PageContextImpl)pc).getParentPageContext()!=null)
			throw new ApplicationException("could not create a thread within a child thread");
		
		try {
			Threads ts = pc.getThreadScope(lcName);
			
			if(type==TYPE_DAEMON){
				if(ts!=null)
					throw new ApplicationException("could not create a thread with the name ["+name+"]. name must be unique within a request");
				ChildThreadImpl ct = new ChildThreadImpl((PageContextImpl) pc,currentPage,name,threadIndex,attrs,false);
				pc.setThreadScope(name,new ThreadsImpl(ct));
				ct.setPriority(priority);
				ct.setDaemon(false);
				ct.start();
			}
			else {
				ChildThreadImpl ct = new ChildThreadImpl((PageContextImpl) pc,currentPage,name,threadIndex,attrs,true);
				ct.setPriority(priority);
				((ConfigImpl)pc.getConfig()).getSpoolerEngine().add(new ChildSpoolerTask(ct,plans));
			}
			
		} 
		catch (Throwable t) {
			throw Caster.toPageException(t);
		}
		finally {
			pc.reuse(this);// this method is not called from template when type is run, a call from template is to early,
		}
	}
	
	private void doSleep() throws ExpressionException {
		if(duration>=0) {
			SystemUtil.sleep(duration);
		}
		else throw new ExpressionException("The attribute duration must be greater or equal than 0, now ["+duration+"]");
		
	}

    private void doJoin() throws ApplicationException {
    	PageContextImpl mpc=(PageContextImpl)getMainPageContext(pc);
		
    	String[] names;
    	if(lcName==null) {
    		names=mpc.getThreadScopeNames();
    	}
    	else names=ListUtil.listToStringArray(lcName, ',');
    	
    	ChildThread ct;
    	Threads ts;
    	long start=System.currentTimeMillis(),_timeout=timeout>0?timeout:-1;
    	
    	for(int i=0;i<names.length;i++) {
    		if(StringUtil.isEmpty(names[i],true))continue;
    		//PageContextImpl mpc=(PageContextImpl)getMainPageContext(pc);
    		ts = mpc.getThreadScope(names[i]);
    		if(ts==null)
    			throw new ApplicationException("there is no thread running with the name ["+names[i]+"], only the following threads existing ["+ListUtil.arrayToList(mpc.getThreadScopeNames(),", ")+"]");
    		ct=ts.getChildThread();
    		
    		if(ct.isAlive()) {
    			try {
					if(_timeout!=-1)ct.join(_timeout);
					else ct.join();
				} 
    			catch (InterruptedException e) {}
    		}
    		if(_timeout!=-1){
    			_timeout=_timeout-(System.currentTimeMillis()-start);
    			if(_timeout<1) break;
    		}
    	}
    	
    }
	private void doTerminate() throws ApplicationException {
		PageContextImpl mpc=(PageContextImpl)getMainPageContext(pc);
		
		Threads ts = mpc.getThreadScope(lcName);
		
		if(ts==null)
			throw new ApplicationException("there is no thread running with the name ["+name+"]");
		ChildThread ct = ts.getChildThread();
		
		if(ct.isAlive()){
			ct.terminated();
			SystemUtil.stop(ct,ThreadLocalPageContext.getConfig(pageContext).getApplicationLogger());
		}
		
	}

	private PageContext getMainPageContext(PageContext pc) {
		if(pc==null)pc=pageContext;
		if(pc.getParentPageContext()==null) return pc;
		return pc.getParentPageContext();
	}

	@Override
	public void doInitBody()	{
		
	}

	@Override
	public int doAfterBody()	{
		return SKIP_BODY;
	}

	/**
	 * sets if has body or not
	 * @param hasBody
	 */
	public void hasBody(boolean hasBody) {
	    
	}
}
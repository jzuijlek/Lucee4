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
package lucee.runtime.cache;

import java.io.IOException;

import lucee.commons.io.cache.Cache;
import lucee.commons.io.cache.CacheEntry;
import lucee.commons.io.cache.CacheFilter;
import lucee.commons.io.cache.complex.CacheComplex;
import lucee.commons.lang.StringUtil;
import lucee.runtime.config.Config;
import lucee.runtime.type.Struct;
import lucee.runtime.type.StructImpl;
import lucee.runtime.type.dt.TimeSpan;
import lucee.runtime.type.dt.TimeSpanImpl;

public class CacheUtil {
 	public static Struct getInfo(CacheEntry ce) {
		return getInfo(new StructImpl(), ce);
	}
 	
	public static Struct getInfo(Struct info,CacheEntry ce) {
		if(info==null) info=new StructImpl();
		info.setEL("key", ce.getKey());
		info.setEL("created", ce.created());
		info.setEL("last_hit", ce.lastHit());
		info.setEL("last_modified", ce.lastModified());

		info.setEL("hit_count", new Double(ce.hitCount()));
		info.setEL("size", new Double(ce.size()));
		
		
		info.setEL("idle_time_span", toTimespan(ce.idleTimeSpan()));		
		info.setEL("live_time_span", toTimespan(ce.liveTimeSpan()));
		
		
		return info;
	}


	public static Struct getInfo(Cache c) {
		return getInfo(new StructImpl(), c);
	}

	public static Struct getInfo(Struct info, Cache c) {
		if(info==null) info=new StructImpl();

		long value = c.hitCount();
		if(value>=0)info.setEL("hit_count", new Double(value));
		value = c.missCount();
		if(value>=0)info.setEL("miss_count", new Double(value));
		
		return info;
	}

	
	public static Object toTimespan(long timespan) {
		if(timespan==0)return "";
		
		TimeSpan ts = TimeSpanImpl.fromMillis(timespan);
		if(ts==null)return "";
		return ts;
	}


	public static String toString(CacheEntry ce) {

		return "created:	"+ce.created()
		+"\nlast-hit:	"+ce.lastHit()
		+"\nlast-modified:	"+ce.lastModified()
		
		+"\nidle-time:	"+ce.idleTimeSpan()
		+"\nlive-time	:"+ce.liveTimeSpan()
		
		+"\nhit-count:	"+ce.hitCount()
		+"\nsize:		"+ce.size();
	}


	public static boolean allowAll(CacheFilter filter) {
		if(filter==null)return true;
		String p = StringUtil.trim(filter.toPattern(),"");
		return p.equals("*") || p.equals("");
	}

	/**
	 * in difference to the getInstance method of the CacheConnection this method produces a wrapper Cache (if necessary) that creates Entry objects to make sure the Cache has meta data.
	 * @param cc
	 * @param config
	 * @return
	 * @throws IOException 
	 */
	public static Cache getInstance(CacheConnection cc, Config config) throws IOException {
		return  cc.getInstance(config);
		/*Cache c = cc.getInstance(config);
		if("org.lucee.extension.io.cache.memcache.MemCacheRaw".equals(c.getClass().getName())) {
			return new CacheComplex(cc,c);
		}
		return c;*/
	}

}

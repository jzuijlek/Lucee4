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
package lucee.runtime.functions.cache;

import java.io.IOException;

import lucee.runtime.PageContext;
import lucee.runtime.config.ConfigImpl;
import lucee.runtime.exp.PageException;
import lucee.runtime.ext.function.Function;
import lucee.runtime.op.Caster;
import lucee.runtime.type.Collection;
import lucee.runtime.type.KeyImpl;
import lucee.runtime.type.Struct;
import lucee.runtime.type.StructImpl;
import lucee.runtime.type.util.KeyConstants;
import lucee.commons.io.cache.Cache;
import lucee.commons.io.cache.CacheEntry;

/**
 * 
 */
public final class CacheGetMetadata implements Function {
	
	private static final long serialVersionUID = -470089623854482521L;
	
	private static final Collection.Key CACHE_HITCOUNT = KeyImpl.intern("cache_hitcount");
	private static final Collection.Key CACHE_MISSCOUNT = KeyImpl.intern("cache_misscount");
	private static final Collection.Key CACHE_CUSTOM = KeyImpl.intern("cache_custom");
	private static final Collection.Key CREATED_TIME = KeyImpl.intern("createdtime");
	private static final Collection.Key IDLE_TIME = KeyImpl.intern("idletime");
	private static final Collection.Key LAST_HIT = KeyImpl.intern("lasthit");
	private static final Collection.Key LAST_UPDATED = KeyImpl.intern("lastupdated");

	public static Struct call(PageContext pc, String id) throws PageException {
		return call(pc, id,null);
	}
	
	public static Struct call(PageContext pc, String id, String cacheName) throws PageException {
		try {
			Cache cache = Util.getCache(pc,cacheName,ConfigImpl.CACHE_DEFAULT_OBJECT);
			CacheEntry entry = cache.getCacheEntry(Util.key(id));
			
			Struct info=new StructImpl();
			info.set(CACHE_HITCOUNT, new Double(cache.hitCount()));
			info.set(CACHE_MISSCOUNT, new Double(cache.missCount()));
			info.set(CACHE_CUSTOM, cache.getCustomInfo());
			info.set(KeyConstants._custom, entry.getCustomInfo());
			
			info.set(CREATED_TIME, entry.created());
			info.set(KeyConstants._hitcount, new Double(entry.hitCount()));
			info.set(IDLE_TIME, new Double(entry.idleTimeSpan()));
			info.set(LAST_HIT, entry.lastHit());
			info.set(LAST_UPDATED, entry.lastModified());
			info.set(KeyConstants._size, new Double(entry.size()));
			info.set(KeyConstants._timespan, new Double(entry.liveTimeSpan()));
			return info;
		} catch (IOException e) {
			throw Caster.toPageException(e);
		}
	}
}
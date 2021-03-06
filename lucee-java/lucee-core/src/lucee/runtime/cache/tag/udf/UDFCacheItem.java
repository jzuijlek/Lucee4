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
package lucee.runtime.cache.tag.udf;

import java.io.Serializable;

import lucee.commons.digest.HashUtil;
import lucee.runtime.PageContext;
import lucee.runtime.cache.tag.CacheItem;
import lucee.runtime.dump.DumpData;
import lucee.runtime.dump.DumpProperties;
import lucee.runtime.dump.DumpTable;
import lucee.runtime.dump.DumpUtil;
import lucee.runtime.dump.Dumpable;
import lucee.runtime.dump.SimpleDumpData;

public class UDFCacheItem implements CacheItem, Serializable, Dumpable {

	private static final long serialVersionUID = -3616023500492159529L;

	public final String output;
	public final Object returnValue;
	private String udfName;
	private String meta;
	private long executionTimeNS;

	private final long payload;

	
	public UDFCacheItem(String output, Object returnValue, String udfName, String meta, long executionTimeNS) {
		this.output = output;
		this.returnValue = returnValue;
		this.udfName = udfName;
		this.meta = meta;
		this.executionTimeNS=executionTimeNS;
		this.payload=lucee.commons.lang.SizeOf.size(returnValue)+output.length();
	}

	@Override
	public DumpData toDumpData(PageContext pageContext, int maxlevel, DumpProperties properties) {
		DumpTable table = new DumpTable("#669999","#ccffff","#000000");
		table.setTitle("UDFCacheEntry");
		table.appendRow(1,new SimpleDumpData("Return Value"),DumpUtil.toDumpData(returnValue, pageContext, maxlevel, properties));
		table.appendRow(1,new SimpleDumpData("Output"),DumpUtil.toDumpData(new SimpleDumpData(output), pageContext, maxlevel, properties));
		return table;
	}
	
	public String toString(){
		return output;
	}

	@Override
	public String getHashFromValue() {
		return Long.toString(HashUtil.create64BitHash(output+":"+UDFArgConverter.serialize(returnValue)));
	}

	@Override
	public String getName() {
		return udfName;
	}

	@Override
	public long getPayload() {
		return payload;
	}

	@Override
	public String getMeta() {
		return meta;
	}

	@Override
	public long getExecutionTime() {
		return executionTimeNS;
	}

}

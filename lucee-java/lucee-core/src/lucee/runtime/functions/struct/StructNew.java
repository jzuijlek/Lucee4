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
/**
 * Implements the CFML Function structnew
 */
package lucee.runtime.functions.struct;

import lucee.runtime.PageContext;
import lucee.runtime.exp.FunctionException;
import lucee.runtime.exp.PageException;
import lucee.runtime.functions.BIF;
import lucee.runtime.op.Caster;
import lucee.runtime.type.Struct;
import lucee.runtime.type.StructImpl;

public final class StructNew extends BIF {

	private static final long serialVersionUID = 2439168907287957648L;

	public static Struct call(PageContext pc ) {
        return new StructImpl();
    }
    public static Struct call(PageContext pc ,String type) throws FunctionException {
        type=type.toLowerCase();
        if(type.equals("linked")) return new StructImpl(Struct.TYPE_LINKED);
        else if(type.equals("ordered")) return new StructImpl(Struct.TYPE_LINKED);
        else if(type.equals("weak")) return new StructImpl(Struct.TYPE_WEAKED);
        else if(type.equals("syncronized")) return new StructImpl(Struct.TYPE_SYNC);
        else if(type.equals("synchronized")) return new StructImpl(Struct.TYPE_SYNC);
        else if(type.equals("sync")) return new StructImpl(Struct.TYPE_SYNC);
        else if(type.equals("soft")) return new StructImpl(StructImpl.TYPE_SOFT);
        else if(type.equals("normal")) return new StructImpl();
        
        else throw new FunctionException(pc,"structNew",1,"type","valid values are [normal, weak, linked, soft, synchronized]");
    }
	@Override
	public Object invoke(PageContext pc, Object[] args) throws PageException {
		if(args.length==1) return call(pc,Caster.toString(args[0]));
		return call(pc);
		
	}
}
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
package lucee.transformer.bytecode.statement.tag;

import lucee.transformer.bytecode.BytecodeContext;
import lucee.transformer.bytecode.BytecodeException;
import lucee.transformer.bytecode.Position;
import lucee.transformer.bytecode.util.Types;

import org.objectweb.asm.Type;
import org.objectweb.asm.commons.Method;

public final class TagReThrow extends TagBaseNoFinal {

	public TagReThrow(Position start,Position end) {
		super(start,end);
	}

	// void throwCatch()
	private static final Method THROW_CATCH = new Method("throwCatch",Type.VOID_TYPE,new Type[]{});

	/**
	 *
	 * @see lucee.transformer.bytecode.statement.StatementBase#_writeOut(org.objectweb.asm.commons.GeneratorAdapter)
	 */
	public void _writeOut(BytecodeContext bc) throws BytecodeException {
		bc.getAdapter().loadArg(0);
		bc.getAdapter().invokeVirtual(Types.PAGE_CONTEXT, THROW_CATCH);
	}

}

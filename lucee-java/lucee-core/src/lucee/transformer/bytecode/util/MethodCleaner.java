/**
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
package lucee.transformer.bytecode.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lucee.print;
import lucee.commons.io.IOUtil;
import lucee.commons.io.res.Resource;
import lucee.commons.io.res.ResourceProvider;
import lucee.commons.io.res.ResourcesImpl;
import lucee.commons.io.res.util.ResourceUtil;
import lucee.commons.lang.ClassException;
import lucee.commons.lang.ClassUtil;
import lucee.commons.lang.StringUtil;
import lucee.runtime.PageContext;
import lucee.runtime.engine.ThreadLocalPageContext;
import lucee.runtime.exp.ExpressionException;
import lucee.runtime.exp.PageException;
import lucee.runtime.exp.PageRuntimeException;
import lucee.runtime.op.Caster;
import lucee.runtime.type.util.ListUtil;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MethodCleaner extends ClassVisitor implements Opcodes {

	private final String methodName;
	//private Class[] arguments;
	private String strArgs;
	private Class rtn;
	private String msg;

	MethodCleaner(ClassVisitor cv, String methodName, Class[] args, Class rtn, String msg) {
		super(ASM4, cv);
		this.methodName=methodName;
		//this.arguments = arguments;
		
		StringBuilder sb=new StringBuilder("(");
		for(int i=0;i<args.length;i++){
			sb.append(Type.getDescriptor(args[i]));
		}
		sb.append(")");
		sb.append(Type.getDescriptor(rtn));
		strArgs=sb.toString();
		this.rtn=rtn;
		this.msg=StringUtil.isEmpty(msg)?null:msg;
	}


    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        cv.visit(version, access, name, signature, superName, interfaces);
    }
    
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
    	if(name.equals(methodName) && desc.equals(strArgs)) {
    		MethodVisitor mv = cv.visitMethod(access,  name,  desc,  signature,  exceptions);
    		mv.visitCode();
    		
    		if(msg==null)empty(mv);
    		else exception(mv);
    		
    		mv.visitEnd();
        	return mv;
    	}
    	return super.visitMethod(access,  name,  desc,  signature,  exceptions);
    
	}
    
	private static List<Object> parse(String str) {
		List<Object> list=new ArrayList<Object>();
		int last=0,index1,index2;
		while((index1=str.indexOf("{arg:",last))!=-1) {
			list.add(str.substring(last,index1));
			index2=str.indexOf('}',index1+5);
			try {
				list.add(Caster.toInteger(str.substring(index1+5,index2)));
			}
			catch (PageException pe) {throw new PageRuntimeException(pe);}
			
			last=index2+1;
			//break;
		}
		if(str.length()>last) {
			list.add(str.substring(last));
		}
		
		
		return list;
	}


	private void exception(MethodVisitor mv) {
		List<Object> list = parse(msg);
		if(list.size()==1) {
			mv.visitTypeInsn(NEW, "java/lang/RuntimeException");
			mv.visitInsn(DUP);
			mv.visitLdcInsn(msg);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V");
			mv.visitInsn(ATHROW);
			//mv.visitMaxs(3, 1);
		}
		else {
			mv.visitTypeInsn(NEW, "java/lang/RuntimeException");
			mv.visitInsn(DUP);
			mv.visitTypeInsn(NEW, "java/lang/StringBuffer");
			mv.visitInsn(DUP);
			
			Iterator<Object> it = list.iterator();
			int count=0;
			Object o;
			while(it.hasNext()){
				o=it.next();
				
				// add left
				mv.visitLdcInsn(o);
				if(count++==0) {
					mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuffer", "<init>", "(Ljava/lang/String;)V");
				}
				else {
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuffer", "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
				}
				
				// add arg
				if(it.hasNext()) {
					Integer i=(Integer) it.next();
					mv.visitVarInsn(ALOAD, i.intValue());
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuffer", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuffer;");
				}
			}
			
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuffer", "toString", "()Ljava/lang/String;");
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V");
			mv.visitInsn(ATHROW);
			
		}
		
		
		
	}


	private void empty(MethodVisitor mv) {
		// void
		if(rtn==void.class) {
			mv.visitInsn(RETURN);
		}
		// int,boolean,short,char,byte
		else if(rtn==int.class) {
			mv.visitInsn(ICONST_0);
			mv.visitInsn(IRETURN);
			mv.visitMaxs(1, 1);
		}
		// double
		else if(rtn==double.class) {
			mv.visitInsn(DCONST_0);
			mv.visitInsn(DRETURN);
			mv.visitMaxs(2, 1);
		}
		// float
		else if(rtn==float.class) {
			mv.visitInsn(FCONST_0);
			mv.visitInsn(FRETURN);
			mv.visitMaxs(1, 1);
		}
		// long
		else if(rtn==long.class) {
			mv.visitInsn(LCONST_0);
			mv.visitInsn(LRETURN);
			mv.visitMaxs(2, 1);
		}
		// Object
		else {
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(1, 1);
		}
	}

	public static byte[] modifie(byte[] src, String methodName,Class[] args, Class rtn, String msg){
		ClassReader cr = new ClassReader(src);
		ClassWriter cw = ASMUtil.getClassWriter();
		ClassVisitor ca = new MethodCleaner(cw,methodName,args,rtn,msg);
		cr.accept(ca, 0);
		return cw.toByteArray();
	}
	
	public static void modifie(String path, String methodName,String[] argNames, String rtnName, String msg) throws IOException, ExpressionException{
		print.e("->"+path);
		Resource res = ResourceUtil.toResourceExisting(ThreadLocalPageContext.getConfig(), path);
		print.e(res);
		Class[] args=new Class[argNames.length];
		for(int i=0;i<argNames.length;i++){
			args[i]=ClassUtil.loadClass(argNames[i]);
		}
		Class rtn=ClassUtil.loadClass(rtnName);
		byte[] result = modifie(IOUtil.toBytes(res), methodName, args, rtn,msg);
		IOUtil.write(res, result);
	}
}
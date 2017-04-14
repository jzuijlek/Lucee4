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
package lucee.runtime.db;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TimeZone;

import lucee.commons.lang.ClassException;
import lucee.commons.lang.StringUtil;
import lucee.runtime.config.ConfigWebFactory;
import lucee.runtime.op.Caster;
import lucee.runtime.type.Collection.Key;
import lucee.runtime.type.KeyImpl;
import lucee.runtime.type.Struct;
import lucee.runtime.type.util.CollectionUtil;


/**
 * 
 */
public final class DataSourceImpl  extends DataSourceSupport {

    private String connStr;
    private String host;
    private String database;
    private int port;
    private String connStrTranslated;
    private Struct custom;
	private boolean validate;
	private String dbdriver;
	private final boolean literalTimestampWithTSOffset;
    
	/**
	 * constructor of the class
	 * @param name 
	 * @param className
	 * @param host 
	 * @param dsn
	 * @param database 
	 * @param port 
	 * @param username
	 * @param password
	 * @param connectionLimit 
	 * @param connectionTimeout 
	 * @param blob 
	 * @param clob 
	 * @param allow 
	 * @param custom 
	 * @param readOnly 
	 * @throws ClassException 
	 */
    public DataSourceImpl(String name,String className, String host, String dsn, String database, int port, String username, String password, 
            int connectionLimit, int connectionTimeout,long metaCacheTimeout, boolean blob, boolean clob, int allow, Struct custom, boolean readOnly, 
            boolean validate, boolean storage, TimeZone timezone, String dbdriver, boolean literalTimestampWithTSOffset) throws ClassException {

        this(name, toClass(className), host, dsn, database, port, username, password, connectionLimit, connectionTimeout,metaCacheTimeout, blob, clob, allow, custom, readOnly, validate, storage, timezone, dbdriver,literalTimestampWithTSOffset);
	}

	private DataSourceImpl(String name, Class<?> clazz, String host, String dsn, String database, int port, String username, String password,
            int connectionLimit, int connectionTimeout, long metaCacheTimeout, boolean blob, boolean clob, int allow, Struct custom, boolean readOnly,
            boolean validate, boolean storage, TimeZone timezone, String dbdriver, boolean literalTimestampWithTSOffset) {

		super(name, clazz,username,ConfigWebFactory.decrypt(password),blob,clob,connectionLimit, connectionTimeout, metaCacheTimeout, timezone, allow<0?ALLOW_ALL:allow, storage, readOnly);
			
        this.host=host;
        this.database=database;
        this.connStr=dsn; 
        this.port=port;

        this.custom=custom;
        this.validate=validate;
        
        this.connStrTranslated=dsn; 
        translateDsn();

		this.dbdriver = dbdriver;
		this.literalTimestampWithTSOffset=literalTimestampWithTSOffset;
        
        //	throw new DatabaseException("can't find class ["+classname+"] for jdbc driver, check if driver (jar file) is inside lib folder",e.getMessage(),null,null,null);
        
	}
    private void translateDsn() {
        connStrTranslated=replace(connStrTranslated,"host",host,false);
        connStrTranslated=replace(connStrTranslated,"database",database,false);
        connStrTranslated=replace(connStrTranslated,"port",Caster.toString(port),false);
        connStrTranslated=replace(connStrTranslated,"username",getUsername(),false);
        connStrTranslated=replace(connStrTranslated,"password",getPassword(),false);
        
        //Collection.Key[] keys = custom==null?new Collection.Key[0]:custom.keys();
        if(custom!=null) {
        	Iterator<Entry<Key, Object>> it = custom.entryIterator();
        	Entry<Key, Object> e;
            while(it.hasNext()) {
	        	e = it.next();
	            connStrTranslated=replace(connStrTranslated,e.getKey().getString(),Caster.toString(e.getValue(),""),true);
	        }
        }
    }

    private String replace(String src, String name, String value,boolean doQueryString) {
        if(StringUtil.indexOfIgnoreCase(src,"{"+name+"}")!=-1) {
            return StringUtil.replace(connStrTranslated,"{"+name+"}",value,false);
        }
        if(!doQueryString) return src;
        if(getClazz().getName().indexOf("microsoft")!=-1 || getClazz().getName().indexOf("jtds")!=-1)
        	return src+=';'+name+'='+value;
        else if(getClazz().getName().indexOf("teradata")!=-1)
        	return src+='/'+name+'='+value;
        return src+=((src.indexOf('?')!=-1)?'&':'?')+name+'='+value;
    }

    @Override
    public String getDsnOriginal() {
        return getConnectionString();
    }

    @Override
    public String getConnectionString() {
        return connStr;
    }
    
    @Override
    public String getDsnTranslated() {
        return getConnectionStringTranslated();
    }
    
    @Override
    public String getConnectionStringTranslated() {
        return connStrTranslated;
    }

    @Override
    public String getDatabase() {
        return database;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getHost() {
        return host;
    }
    
    @Override
    public Object clone() {
        return new DataSourceImpl(getName(),getClazz(), host, connStr, database, port, getUsername(), getPassword(), getConnectionLimit(), getConnectionTimeout(),getMetaCacheTimeout(), isBlob(), isClob(), allow, custom, isReadOnly(),validate,isStorage(),getTimeZone(), dbdriver,literalTimestampWithTSOffset);
    }

    @Override
    public DataSource cloneReadOnly() {
        return new DataSourceImpl(getName(),getClazz(), host, connStr, database, port, getUsername(), getPassword(), getConnectionLimit(), getConnectionTimeout(),getMetaCacheTimeout(), isBlob(), isClob(), allow,custom, true,validate,isStorage(),getTimeZone(), dbdriver,literalTimestampWithTSOffset);
    }

    @Override
    public String getCustomValue(String key) {
        return Caster.toString(custom.get(KeyImpl.init(key),null),"");
    }
    
    @Override
    public String[] getCustomNames() {
        return CollectionUtil.keysAsString(custom);
    }
    
    @Override
    public Struct getCustoms() {
        return (Struct)custom.clone();
    }
    
    public boolean getLiteralTimestampWithTSOffset() {
        return literalTimestampWithTSOffset;
    }

    @Override
    public boolean validate() {
		return validate;
	}

	public String getDbDriver() {
		return dbdriver;
	}
}
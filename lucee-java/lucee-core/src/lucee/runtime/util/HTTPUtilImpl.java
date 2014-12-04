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
package lucee.runtime.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import lucee.commons.net.URLDecoder;
import lucee.commons.net.URLEncoder;
import lucee.commons.net.http.HTTPEngine;
import lucee.commons.net.http.HTTPResponse;
import lucee.commons.net.http.Header;
import lucee.runtime.net.proxy.ProxyDataImpl;
import lucee.runtime.util.HTTPUtil;

public class HTTPUtilImpl implements HTTPUtil {
	


	private static HTTPUtil instance=new HTTPUtilImpl();

	private HTTPUtilImpl(){}
	
	public static HTTPUtil getInstance() {
		return instance;
	}

	/**
	 * @see lucee.runtime.util.HTTPUtil#decode(java.lang.String, java.lang.String)
	 */
	public String decode(String str, String charset)throws UnsupportedEncodingException {
		return URLDecoder.decode(str, charset,false);
	}

	/**
	 * @see lucee.runtime.util.HTTPUtil#delete(java.net.URL, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, lucee.commons.net.http.Header[])
	 */
	public HTTPResponse delete(URL url, String username, String password,
			int timeout, String charset, String useragent, String proxyserver,
			int proxyport, String proxyuser, String proxypassword,
			Header[] headers) throws IOException {
		return HTTPEngine.delete(url, username, password, timeout,HTTPEngine.MAX_REDIRECT, charset, useragent, ProxyDataImpl.getInstance(proxyserver, proxyport, proxyuser, proxypassword), headers);
	}

	/**
	 * @param str
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String encode(String str, String charset)throws UnsupportedEncodingException {
		return URLEncoder.encode(str, charset);
	}

	/**
	 * @see lucee.runtime.util.HTTPUtil#head(java.net.URL, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, lucee.commons.net.http.Header[])
	 */
	public HTTPResponse head(URL url, String username, String password,
			int timeout, String charset, String useragent, String proxyserver,
			int proxyport, String proxyuser, String proxypassword,
			Header[] headers) throws IOException {
		return HTTPEngine.head(url, username, password, timeout,HTTPEngine.MAX_REDIRECT, charset, useragent, ProxyDataImpl.getInstance(proxyserver, proxyport, proxyuser, proxypassword), headers);
	}

	/**
	 * @see lucee.runtime.util.HTTPUtil#get(java.net.URL, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, lucee.commons.net.http.Header[])
	 */
	public HTTPResponse get(URL url, String username, String password,
			int timeout, String charset, String useragent, String proxyserver,
			int proxyport, String proxyuser, String proxypassword,
			Header[] headers) throws IOException {
		return HTTPEngine.get(url, username, password, timeout,HTTPEngine.MAX_REDIRECT, charset, useragent, ProxyDataImpl.getInstance(proxyserver, proxyport, proxyuser, proxypassword), headers);
	}

	/**
	 * @see lucee.runtime.util.HTTPUtil#put(java.net.URL, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, lucee.commons.net.http.Header[], java.lang.Object)
	 */
	public HTTPResponse put(URL url, String username, String password,
			int timeout, String charset, String useragent, String proxyserver,
			int proxyport, String proxyuser, String proxypassword,
			Header[] headers, Object body) throws IOException {
		return put(url, username, proxypassword, timeout, null, charset, useragent, proxyserver, proxyport, proxyuser, proxypassword, headers, body);
	}
	
	// FUTURE add to interface
	public HTTPResponse put(URL url, String username, String password,
			int timeout, String mimetype, String charset, String useragent, String proxyserver,
			int proxyport, String proxyuser, String proxypassword,
			Header[] headers, Object body) throws IOException {
		return HTTPEngine.put(url, username, password, timeout,HTTPEngine.MAX_REDIRECT, mimetype, charset, useragent, ProxyDataImpl.getInstance(proxyserver, proxyport, proxyuser, proxypassword), headers, body);
	}

	@Override
	public URL toURL(String strUrl, int port) throws MalformedURLException {
		return toURL(strUrl, port, true);
	}
	
	public URL toURL(String strUrl, int port, boolean encodeIfNecessary) throws MalformedURLException {
		return lucee.commons.net.HTTPUtil.toURL(strUrl, port,encodeIfNecessary);
	}

	
	
	/**
	 * @see lucee.commons.net.HTTPUtil#toURL(java.lang.String)
	 */
	public URL toURL(String strUrl) throws MalformedURLException {
		return lucee.commons.net.HTTPUtil.toURL(strUrl,true);
	}
	
	public URI toURI(String strUrl) throws URISyntaxException {
		return lucee.commons.net.HTTPUtil.toURI(strUrl);
	}
	
	public URI toURI(String strUrl, int port) throws URISyntaxException {
		return lucee.commons.net.HTTPUtil.toURI(strUrl,port);
	}


}

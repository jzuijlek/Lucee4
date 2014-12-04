<!--- 
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
 ---><!--- <cfif request.adminType EQ "web">
	<cfthrow message="no access to this functionality">
</cfif>
 --->
<cfset error.message="">
<cfset error.detail="">

<!--- 
Defaults --->
<cfparam name="url.action2" default="list">
<cfparam name="form.mainAction" default="none">
<cfparam name="form.subAction" default="none">

<cftry>
	<cfswitch expression="#form.mainAction#">
	<!--- CHANGE --->
		<cfcase value="#stText.Buttons.Change#">
				<cfadmin 
					action="updateSerial"
					type="#request.adminType#"
					password="#session["password"&request.adminType]#"
					serial="#trim(form.serialNumber)#">
		</cfcase>
	</cfswitch>
	<cfcatch>
		<cfset error.message=cfcatch.message>
		<cfset error.detail=cfcatch.Detail>
	</cfcatch>
</cftry>


<!--- 
Redirtect to entry --->
<cfif cgi.request_method EQ "POST" and error.message EQ "">
	<cflocation url="#request.self#?action=#url.action#" addtoken="no">
</cfif>

<!--- 
Error Output --->
<cfset printError(error)>

	<cfadmin 
		action="getSerial"
		type="#request.adminType#"
		password="#session["password"&request.adminType]#"
		returnVariable="serial">
        
<!--- 
(server.ColdFusion.ProductLevel NEQ "enterprise" and server.ColdFusion.ProductLevel NEQ "develop")
	or 
 --->
<cfif request.adminType EQ "server">
<!--- 
Create Datasource --->
<cfoutput><table class="tbl" width="600">
<tr>
	<td colspan="3">#stText.Overview.SerialNumberDescription#</td>
</tr>
<tr>
	<td colspan="3"><cfmodule template="tp.cfm"  width="1" height="1"></td>
</tr>
<cfform onerror="customError" action="#request.self#?action=#url.action#" method="post">
<tr>
	<th scope="row">#stText.Overview.SerialNumber#</th>
	<td>
		<!--- <span class="comment">The new Password for the Administrator</span><br> --->
		<cfinput type="text" name="serialNumber" value="#serial#" 
		style="width:400px;" required="no">
	</td>
</tr>

<tr>
	<td colspan="2">
		<input type="submit" class="button submit" name="mainAction" value="#stText.Buttons.Change#">
		<input type="reset" class="reset" name="cancel" value="#stText.Buttons.Cancel#">
	</td>
</tr>
</cfform></cfoutput>
</table>
</cfif>
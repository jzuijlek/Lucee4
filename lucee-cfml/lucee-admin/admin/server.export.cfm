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
 ---><cfsilent>
<!--- 
Defaults --->
<cfparam name="url.action2" default="list">
<cfparam name="form.mainAction" default="none">
<cfparam name="form.subAction" default="none">


<cfadmin 
	action="getLocales"
	locale="#stText.locale#"
	returnVariable="locales">
	
<cfadmin 
	action="getTimeZones"
	locale="#stText.locale#"
	returnVariable="timezones">
	
<cfadmin 
	action="getRegional"
	type="#request.adminType#"
	password="#session["password"&request.adminType]#"
	returnVariable="regional">

<cfadmin 
	action="getScope"
	type="#request.adminType#"
	password="#session["password"&request.adminType]#"
	returnVariable="scope">
	
<cfadmin 
	action="getInfo"
	type="#request.adminType#"
	password="#session["password"&request.adminType]#"
	returnVariable="info">

<cfadmin 
	type="#request.adminType#"
	password="#session["password"&request.adminType]#"
	action="getPerformanceSettings"
	returnVariable="PerformanceSettings">
	
<cfadmin 
	action="getApplicationSetting"
	type="#request.adminType#"
	password="#session["password"&request.adminType]#"
	returnVariable="appSettings">
<cfset requestTimeout=
		appSettings.requestTimeout_second +
		(appSettings.requestTimeout_minute*60) +
		(appSettings.requestTimeout_hour*3600) +
		(appSettings.requestTimeout_day*3600*24)>


<cfadmin 
	action="getCharset"
	type="#request.adminType#"
	password="#session["password"&request.adminType]#"
	returnVariable="charset">		

<cfadmin 
	action="getOutputSetting"
	type="#request.adminType#"
	password="#session["password"&request.adminType]#"
	returnVariable="outputSetting">


<cfadmin 
	action="getMappings"
	type="#request.adminType#"
	password="#session["password"&request.adminType]#"
	returnVariable="mappings">
	

<!--- cache --->
<cfadmin 
	action="getCacheConnections"
	type="#request.adminType#"
	password="#session["password"&request.adminType]#"
	returnVariable="cacheConnections">
<cfset defaults={}>
    <cfloop index="type" list="object,template,query,resource,function,include">
		<cfloop query="cacheConnections">
		<cfif cacheConnections.default EQ type>
			<cfset defaults[type]=cacheConnections.name>
		</cfif>
		</cfloop>
	</cfloop>
<cfscript>
hasObj=!isNull(defaults.object) && len(defaults.object);
hasTem=!isNull(defaults.template) && len(defaults.template);
hasQry=!isNull(defaults.query) && len(defaults.query);
hasRes=!isNull(defaults.resource) && len(defaults.resource);
hasFun=!isNull(defaults.function) && len(defaults.function);
hasInc=!isNull(defaults.include) && len(defaults.include);
hasCache=hasObj || hasTem || hasQry || hasRes || hasFun || hasInc;
</cfscript>

<!--- datasource --->
<cfadmin 
	action="getDatasources"
	type="#request.adminType#"
	password="#session["password"&request.adminType]#"
	returnVariable="datasources">

</cfsilent>


<cfoutput>
	

	<cfsavecontent variable="codeSample">
component {

	this.name = "#info.label ?: '&lt;application-name&gt;' #"; // name of the application context

// regional
	// default locale used for formating dates, numbers ...
	this.locale = "#regional.locale#"; 
	// default timezone used
	this.timezone = "#regional.timezone#"; 

// scope handling
	// lifespan of a untouched application scope
	this.applicationTimeout = createTimeSpan( #scope.applicationTimeout_day#, #scope.applicationTimeout_hour#, #scope.applicationTimeout_minute#, #scope.applicationTimeout_second# ); 
	
	// session handling enabled or not
	this.sessionManagement = #scope.sessionManagement#; 
	// cfml or jee based sessions
	this.sessionType = "#scope.sessionType#"; 
	// untouched session lifespan
	this.sessionTimeout = createTimeSpan( #scope.sessionTimeout_day#, #scope.sessionTimeout_hour#, #scope.sessionTimeout_minute#, #scope.sessionTimeout_second# ); 
	this.sessionStorage = "#scope.sessionStorage#";
	
	// client scope enabled or not
	this.clientManagement = #scope.clientManagement#; 
	this.clientTimeout = createTimeSpan( #scope.clientTimeout_day#, #scope.clientTimeout_hour#, #scope.clientTimeout_minute#, #scope.clientTimeout_second# );
	this.clientStorage = "#scope.clientStorage#";
						
	// using domain cookies or not
	this.setDomainCookies = #scope.domainCookies#; 
	this.setClientCookies = #scope.clientCookies#;

	// prefer the local scope at unscoped write
	this.localMode = "#scope.LocalMode#"; 
	
	// buffer the output of a tag/function body to output in case of a exception
	this.bufferOutput = #outputSetting.bufferOutput#; 
	this.compression = #outputSetting.AllowCompression#;
	this.suppressRemoteComponentContent = #outputSetting.suppressContent#;
	
	// If set to false lucee ignores type defintions with function arguments and return values
	this.typeChecking = #PerformanceSettings.typeChecking#;
	
	
// request
	// max lifespan of a running request
	this.requestTimeout=createTimeSpan(#appSettings.requestTimeout_day#,#appSettings.requestTimeout_hour#,#appSettings.requestTimeout_minute#,#appSettings.requestTimeout_second#); 

// charset
	this.charset.web="#charset.webCharset#";
	this.charset.resource="#charset.resourceCharset#";
	
	this.scopeCascading = "#scope.scopeCascadingType#";
<cfif hasCache>
	// cache
<cfif hasObj>	this.cache.object = "#!hasObj?"&lt;cache-name>":defaults.object#";
</cfif><cfif hasTem>	this.cache.template = "#!hasTem?"&lt;cache-name>":defaults.template#";
</cfif><cfif hasQry>	this.cache.query = "#!hasQry?"&lt;cache-name>":defaults.query#";
</cfif><cfif hasRes>	this.cache.resource = "#!hasRes?"&lt;cache-name>":defaults.resource#";
</cfif><cfif hasFun>	this.cache.function = "#!hasFun?"&lt;cache-name>":defaults.function#";
</cfif><cfif hasInc>	this.cache.include = "#!hasInc?"&lt;cache-name>":defaults.include#";</cfif>
</cfif>	

//////////////////////////////////////////////
//               DATASOURCES                //
//////////////////////////////////////////////
	<cfloop query="#datasources#"><cfscript>
optional=[];
// not supported yet optional.append('default:false // default: false');
if(datasources.blob) optional.append('blob:#datasources.blob# // default: false');
if(datasources.clob) optional.append('clob:#datasources.clob# // default: false');
if(isNumeric(datasources.connectionLimit))optional.append('connectionLimit:#datasources.connectionLimit# // default:-1');
if(datasources.connectionTimeout NEQ 1)optional.append('connectionTimeout:#datasources.connectionTimeout# // default: 1; unit: seconds');
if(datasources.metaCacheTimeout NEQ 60000)optional.append(',metaCacheTimeout:#datasources.metaCacheTimeout# // default: 60000; unit: milliseconds');
if(len(datasources.timezone))optional.append("timezone:'#replace(datasources.timezone,"'","''","all")#'");
if(datasources.storage) optional.append('storage:#datasources.storage# // default: false');
if(datasources.readOnly) optional.append('readOnly:#datasources.readOnly# // default: false');
</cfscript>
	this.datasources<cfif isValid('variableName',datasources.name) and !find('.',datasources.name)>["#datasources.name#"]<cfelse>['#datasources.name#']</cfif> = {
	  class: '#datasources.classname#'
	, connectionString: '#replace(datasources.dsnTranslated,"'","''","all")#'<cfif len(datasources.password)>
	, username: '#replace(datasources.username,"'","''","all")#'
	, password: "#datasources.passwordEncrypted#"</cfif><cfif optional.len()>
	
	// optional settings
	<cfloop array="#optional#" index="i" item="value">, #value#<cfif i LT optional.len()>
	</cfif></cfloop></cfif>
	};
	</cfloop>

//////////////////////////////////////////////
//               MAPPINGS                   //
//////////////////////////////////////////////
<cfloop query="mappings"><cfif mappings.hidden || mappings.virtual=="/lucee-context" || mappings.virtual=="/lucee-server-context"><cfcontinue></cfif><cfset del=""><cfset count=0>
this.mappings=["#mappings.virtual#"]=<cfif len(mappings.strPhysical) && !len(mappings.strArchive)>"#mappings.strPhysical#"<cfelse>{<cfif len(mappings.strPhysical)><cfset count++>
		physical:"#mappings.strPhysical#"<cfset del=","></cfif><cfif len(mappings.strArchive)><cfset count++>
		#del#archive:"#mappings.strArchive#"<cfset del=","></cfif><cfif count==2 && !mappings.PhysicalFirst>
		#del#primary:"<cfif mappings.PhysicalFirst>physical<cfelse>archive</cfif>"<cfset del=","></cfif>}</cfif>;
</cfloop>
}
</cfsavecontent>


<cfif form.subAction EQ stText.Buttons.export>
	<cfheader name="Content-Disposition" value="inline; filename=Application.cfc">
	<cfcontent reset="true" variable="#toBinary(codeSample,"utf-8")#" type="application/cfml">
	<cfabort>
</cfif>



<h1>#stText.settings.exportAppCFC#</h1>	
<div class="pageintro">#stText.settings.exportAppCFCDesc#</div>
<cfform onerror="customError" action="#request.self#?action=#url.action#" method="post">
<input type="submit" class="button submit" onclick="disableBlockUI=true;" name="subAction" value="#stText.Buttons.export#">
</cfform>


<cfset renderCodingTip( codeSample,false, false )>

</cfoutput>
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
 ---><cfif thistag.EXECUTIONMODE EQ "start">
	<cfmodule template="level2.cfm">
	
	<cfset c="caller">
	
	<cfset caller.fromLevel1="caller.1">
	<cfset caller.recievedfromLevel2=variables.fromLevel2>
	<cfset caller.recieved.from.Level2=variables.from.Level2>
	<cfset "#c#.rec.ieved.from.Level2"=variables.from.Level2>
	
	<cfset "#c#.fromLevel1Eval"="caller.1.eval">
	
</cfif>
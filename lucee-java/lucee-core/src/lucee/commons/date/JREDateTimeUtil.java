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
package lucee.commons.date;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import lucee.runtime.PageContext;
import lucee.runtime.PageContextImpl;
import lucee.runtime.engine.ThreadLocalPageContext;
import lucee.runtime.op.Caster;
import lucee.runtime.type.dt.DateTime;

public class JREDateTimeUtil extends DateTimeUtil {

	private static CalendarThreadLocal _calendar=new CalendarThreadLocal();
	private static CalendarThreadLocal calendar=new CalendarThreadLocal();
	private static LocaleCalendarThreadLocal _localeCalendar=new LocaleCalendarThreadLocal();
	private static LocaleCalendarThreadLocal localeCalendar=new LocaleCalendarThreadLocal();
	
	//Calendar string;
	
	JREDateTimeUtil() {
		
	}

	 long _toTime(TimeZone tz, int year, int month, int day, int hour,int minute, int second, int milliSecond) {
		if(tz==null)tz=ThreadLocalPageContext.getTimeZone(tz);
		Calendar time = _getThreadCalendar(tz);
		time.set(year,month-1,day,hour,minute,second);
		time.set(Calendar.MILLISECOND,milliSecond);  
		return time.getTimeInMillis();
	}

	private static int _get(TimeZone tz, DateTime dt, int field) {
		Calendar c = _getThreadCalendar(tz);
		c.setTimeInMillis(dt.getTime());
		return c.get(field);
	}
	

	private static int _get(Locale l,TimeZone tz, DateTime dt, int field) {
		Calendar c = _getThreadCalendar(l,tz);
		c.setTimeInMillis(dt.getTime());
		return c.get(field);
	}

	@Override
	public int getYear(TimeZone tz, DateTime dt) {
		return _get(tz,dt,Calendar.YEAR);
	}

	@Override
	public int getMonth(TimeZone tz, DateTime dt) {
		return _get(tz,dt,Calendar.MONTH)+1;
	}

	@Override
	public int getDay(TimeZone tz, DateTime dt) {
		return _get(tz,dt,Calendar.DAY_OF_MONTH);
	}

	@Override
	public int getHour(TimeZone tz, DateTime dt) {
		return _get(tz,dt,Calendar.HOUR_OF_DAY);
	}

	@Override
	public int getMinute(TimeZone tz, DateTime dt) {
		return _get(tz,dt,Calendar.MINUTE);
	}

	@Override
	public int getSecond(TimeZone tz, DateTime dt) {
		return _get(tz,dt,Calendar.SECOND);
	}

	@Override
	public int getMilliSecond(TimeZone tz, DateTime dt) {
		return _get(tz,dt,Calendar.MILLISECOND);
	}

	@Override
	public synchronized int getDayOfYear(Locale locale,TimeZone tz, DateTime dt) {
		return _get(locale,tz,dt,Calendar.DAY_OF_YEAR);
	}

	@Override
	public synchronized int getDayOfWeek(Locale locale,TimeZone tz, DateTime dt) {
		return _get(locale,tz,dt,Calendar.DAY_OF_WEEK);
	}
	
	@Override
	public synchronized int getFirstDayOfMonth(TimeZone tz, DateTime dt) {
		Calendar c = _getThreadCalendar(tz);
		c.setTimeInMillis(dt.getTime());
		c.set(Calendar.DATE,1);
		return c.get(Calendar.DAY_OF_YEAR);
	}

	@Override
	public synchronized int getWeekOfYear(Locale locale,TimeZone tz, DateTime dt) {
		
		Calendar c=_getThreadCalendar(locale,tz);
		c.setTimeInMillis(dt.getTime());
    	int week=c.get(Calendar.WEEK_OF_YEAR);
		
		if(week==1 && c.get(Calendar.MONTH)==Calendar.DECEMBER) {
			if(isLeapYear(c.get(Calendar.YEAR)) && c.get(Calendar.DAY_OF_WEEK)==1){
				return 54;
			}
			return 53;
		}
		return week;
	}

	public synchronized long getMilliSecondsInDay(TimeZone tz,long time) {
		Calendar c = _getThreadCalendar(tz);
		c.setTimeInMillis(time);
		return	(c.get(Calendar.HOUR_OF_DAY)*3600000)+
				(c.get(Calendar.MINUTE)*60000)+
				(c.get(Calendar.SECOND)*1000)+
				(c.get(Calendar.MILLISECOND));
	}

	public synchronized int getDaysInMonth(TimeZone tz, DateTime dt) {
		Calendar c = _getThreadCalendar(tz);
		c.setTimeInMillis(dt.getTime());
		return daysInMonth(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1);
	}


	public String toString(DateTime dt, TimeZone tz) {
		PageContext pc=ThreadLocalPageContext.get();
		Calendar c = _getThreadCalendar(tz);
		c.setTimeInMillis(dt.getTime());
			//"HH:mm:ss"
		StringBuilder sb=new StringBuilder();
		
    	sb.append("{ts '");
    	toString(sb,c.get(Calendar.YEAR),4);
    	sb.append("-");
    	toString(sb,c.get(Calendar.MONTH)+1,2);
    	sb.append("-");
    	toString(sb,c.get(Calendar.DATE),2);
    	sb.append(" ");
    	toString(sb,c.get(Calendar.HOUR_OF_DAY),2);
    	sb.append(":");
    	toString(sb,c.get(Calendar.MINUTE),2);
    	sb.append(":");
    	toString(sb,c.get(Calendar.SECOND),2);
    	
    	if(pc instanceof PageContextImpl) {
    		if(((PageContextImpl)pc).getTimestampWithTSOffset()) addTimeZoneOffset(c,sb);
    	}
    	sb.append("'}");
        	 
        return sb.toString();
	}

	private void addTimeZoneOffset(Calendar c, StringBuilder sb) {
		int min = (c.get(Calendar.ZONE_OFFSET)+c.get(Calendar.DST_OFFSET))/60000;
		char op;
		if(min<0) {
			op='-';
			min=min-min-min;
		}
		else op='+';
		
		int hours=min/60;
		min=min-(hours*60);
		sb.append(op);
		toString(sb,hours,2);
		sb.append(':');
		toString(sb,min,2);
	}

	public static Calendar newInstance(TimeZone tz,Locale l) {
		if(tz==null)tz=ThreadLocalPageContext.getTimeZone();
		return Calendar.getInstance(tz,l);
	}

	/**
	 * important:this function returns always the same instance for a specific thread, 
	 * so make sure only use one thread calendar instance at time.
	 * @return calendar instance
	 */
	public static Calendar getThreadCalendar(){
		Calendar c = calendar.get();
		c.clear();
		return c;
	}
	
	/**
	 * important:this function returns always the same instance for a specific thread, 
	 * so make sure only use one thread calendar instance at time.
	 * @return calendar instance
	 */
	public static Calendar getThreadCalendar(TimeZone tz){
		Calendar c = calendar.get();
		c.clear();
		if(tz==null)tz=ThreadLocalPageContext.getTimeZone();
		c.setTimeZone(tz);
		return c;
	}
	
	/**
	 * important:this function returns always the same instance for a specific thread, 
	 * so make sure only use one thread calendar instance at time.
	 * @return calendar instance
	 */
	public static Calendar getThreadCalendar(Locale l,TimeZone tz){
		if(tz==null)tz=ThreadLocalPageContext.getTimeZone();
		Calendar c = localeCalendar.get(tz,l);
		c.setTimeZone(tz);
		return c;
	}
	

	
	/*
	 * internally we use a other instance to avoid conflicts
	 */
	private static Calendar _getThreadCalendar(TimeZone tz){
		Calendar c = _calendar.get();
		c.clear();
		if(tz==null)tz=ThreadLocalPageContext.getTimeZone();
		c.setTimeZone(tz);
		return c;
	}
	
	/*
	 * internally we use a other instance to avoid conflicts
	 */
	private static Calendar _getThreadCalendar(Locale l,TimeZone tz){
		Calendar c = _localeCalendar.get(tz,l);
		if(tz==null)tz=ThreadLocalPageContext.getTimeZone();
		c.setTimeZone(tz);
		return c;
	}
	
	
	/*public  static Calendar newInstance(Locale l) {
		Calendar c=Calendar.getInstance(l);
		return c;
	}*/

	static void toString(StringBuilder sb,int i, int amount) {
		String str = Caster.toString(i);

		amount = amount - str.length();
		while( amount-- > 0 ){
			sb.append( '0');
		}
		sb.append(str);
	}
}


class CalendarThreadLocal extends ThreadLocal<Calendar> {
	protected synchronized Calendar initialValue() {
        return Calendar.getInstance();
    }
}


class LocaleCalendarThreadLocal extends ThreadLocal<Map<String,Calendar>> {
	protected synchronized Map<String,Calendar> initialValue() {
        return new HashMap<String, Calendar>();
    }

	public Calendar get(TimeZone tz,Locale l) {
		Map<String, Calendar> map = get();
		Calendar c = map.get(l+":"+tz);
		if(c==null) {
			c=JREDateTimeUtil.newInstance(tz,l);
			map.put(l+":"+tz, c);
		}
		else c.clear();
		return c;
	}
}

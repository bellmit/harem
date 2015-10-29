//$Id: CalendarField.java 14213 2006-08-31 23:57:47Z dberkland $
package com.yimayhd.harem.util;

import java.util.Calendar;

/**
 * Date field for date math functions of {@link DateUtils}.<br>
 * Copyright (c) 2006 Datavantage Corporation
 *
 * @author dberkland
 * @version $Revision: 14213 $
 * @created Aug 25, 2006
 */
public enum CalendarField {
  /** the Year field for date math functions of {@link DateUtils} */
  YEAR(Calendar.YEAR),
  /** the Month field for date math functions of {@link DateUtils} */
  MONTH(Calendar.MONTH),
  /** the Day field for date math functions of {@link DateUtils} */
  WEEK(Calendar.WEEK_OF_YEAR),
  /** the Day field for date math functions of {@link DateUtils} */
  DAY(Calendar.DATE),
  /** the hour field for date math functions of {@link DateUtils} */
  HOUR(Calendar.HOUR),
  /** the Minute field for date math functions of {@link DateUtils} */
  MINUTE(Calendar.MINUTE),
  /** the Second field for date math functions of {@link DateUtils} */
  SECOND(Calendar.SECOND),
  /** the Millisecond field for date math functions of {@link DateUtils} */
  MILLISECOND(Calendar.MILLISECOND);

  private CalendarField(int argField) {

    id = argField;
  }

  final int id;

}

/*
 * $Log$
 * Revision 1.1  2006/08/31 23:57:47  dberkland
 * created
 *
 */

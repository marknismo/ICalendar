package xyz.markclin.dateTime
/**
 * This file contains helper functions to handle date and time
 *
 * For the convenience to work with icalendar date format, datetimeFormatter is configured as "yyyyMMdd'T'HHmmss'Z'"
 * e.g. DTSTART:20200320T000000Z
 *
 */
import java.time.*
import java.time.format.DateTimeFormatter



/**
 * A group of *members*.
 *
 * This class has no useful logic; it's just a documentation example.
 *
 * @param T the type of a member in this group.
 * @property name the name of this group.
 * @constructor Creates an empty group.
 */



fun String.toZoneId():ZoneId{
    return ZoneId.of(this)
}


fun String.toUTCtoSourceZone(zone:ZoneId):ZonedDateTime{
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
    val localDateTime = LocalDateTime.parse(this, formatter)
    val utcZone = "UTC".toZoneId()
    val utcZdt = ZonedDateTime.of(localDateTime,utcZone)

    return utcZdt.withZoneSameInstant(zone)
}


fun String.toUserZonedDateTime(zone:ZoneId):ZonedDateTime{
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHH':'mm")
    val localDateTime = LocalDateTime.parse(this, formatter)

    return ZonedDateTime.of(localDateTime,zone)
}



fun ZonedDateTime.toUTCString():String{
    //convert ZonedDateTime to epochSecond
    val epochSeconds= this.toEpochSecond()

    //convert epochSeconds to dateFormat
    val localDateTime = LocalDateTime.ofEpochSecond(epochSeconds, 0, ZoneOffset.UTC);
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")

    return localDateTime.format(formatter)
}




fun epochToZonedDateTime():ZonedDateTime{
    val i = Instant.ofEpochSecond(0);
    return ZonedDateTime.ofInstant(i, "UTC".toZoneId())
}



fun LocalDate.toDateString():String{
    val icalFormat = DateTimeFormatter.ofPattern("yyyyMMdd")
    return this.format(icalFormat)
}


fun String.toLocalDate():LocalDate{
    val icalFormat = DateTimeFormatter.ofPattern("yyyyMMdd")
    return LocalDate.parse(this, icalFormat)

}


fun String.toLocalTime():LocalTime{
    val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    return LocalTime.parse(this,timeFormat)
}


package xyz.markclin



import java.io.FileInputStream
import java.io.FileOutputStream
import net.fortuna.ical4j.data.CalendarBuilder as IcalCalendarBuilder
import net.fortuna.ical4j.data.CalendarOutputter as IcalCalendarOutputter
import net.fortuna.ical4j.util.RandomUidGenerator as IcalRandomUidGenerator
import net.fortuna.ical4j.util.CompatibilityHints as IcalCompatibilityHints
import xyz.markclin.dateTime.*
import xyz.markclin.data.EventData
import xyz.markclin.data.UserInput



fun main(args: Array<String>) {


    val builder = IcalCalendarBuilder()
    val ug = IcalRandomUidGenerator()
    val outputter = IcalCalendarOutputter()
    val userInput = UserInput()
    userInput.readFromFile()
    userInput.initUserInput()

    val fin = FileInputStream(userInput.inputFile)
    val fout = FileOutputStream(userInput.outputFile)
    val imported_calendar = builder.build(fin)

    val sourceZone = userInput.sourceTimezone.toZoneId()
    val targetZone = userInput.targetTimezone.toZoneId()
    val targetRefDateZdt = ("${userInput.targetRefDate}${userInput.targetRefTime}".toUserZonedDateTime(targetZone))
    val sourceRefDateZdt = ("${userInput.sourceRefDate}${userInput.sourceRefTime}".toUserZonedDateTime(sourceZone))


    //initialize EventData
    val eventData = EventData(
        targetZone,
        targetRefDateZdt.toLocalTime(),
        targetRefDateZdt.toLocalDate(),
        sourceZone,
        sourceRefDateZdt
    )

    for (meeting in imported_calendar.getComponents()) {
        for (property in meeting.getProperties()) {
            when (property.getName()) {
                "UID" -> property.setValue(ug.generateUid().getValue())
                "DTSTART" -> {
                    val dtStart = property.getValue()
                    if (dtStart.contains("T", ignoreCase = true)) {
                        eventData.setDtStart(dtStart)
                        property.setValue(eventData.getDtStartUTCString())
                    } else {
                        eventData.setDtStartDate(dtStart)
                        property.setValue(eventData.getInputStartDate())
                    }
                }
                "DTEND" -> {
                    val dtEnd = property.getValue()
                    if (dtEnd.contains("T", ignoreCase = true)) {
                        eventData.setDtEnd(dtEnd)
                        property.setValue(eventData.getDtEndUTCString())
                    } else {
                        eventData.setDtEndDate(dtEnd)
                        property.setValue(eventData.getDtEndDate())
                    }
                }
            }
        }
    }

    IcalCompatibilityHints.setHintEnabled(IcalCompatibilityHints.KEY_RELAXED_VALIDATION, true)
    outputter.output(imported_calendar, fout)
    fin.close()
    fout.close()

}

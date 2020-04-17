package xyz.markclin.data

import xyz.markclin.dateTime.*
import java.io.File
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit



class UserInput(){

    var inputFile:String=""
        private set

    var sourceTimezone:String=""
        private set

    var targetTimezone:String=""
        private set

    var targetRefDate:String=""
        private set

    var targetRefTime:String=""
        private set

    var outputFile:String="modified.ics"
        private set

    var sourceRefTime:String=""

    var sourceRefDate:String=""

    private val KVMap = mutableMapOf<String,String>()

    fun readFromFile(){
        println("\nThis program will create a new calendar based on an existing calendar to start at your specified date and time.")
        println("See the comments in config.toml on how to configure settings.\n")
        try {

            File("config.toml").useLines {
                lines -> lines.forEach {
                    if(!(it.startsWith("#") || it.isBlank())){
                        val lineList=it.split("=")
                        KVMap += lineList[0] to lineList[1]
                    }
                }
            }

        }catch(e: Exception){
            println("Error reading config.toml")
        }
    }

    fun initUserInput(){
        for((key,value) in KVMap){
           when(key){
               "inputFile" ->  inputFile=value
               "outputFile" ->  outputFile=value
               "targetTimezone" ->  targetTimezone=value
               "targetRefDate" ->  targetRefDate=value
               "targetRefTime" ->  targetRefTime=value
               "sourceTimezone" ->  sourceTimezone=value
               "sourceRefDate" ->  sourceRefDate=value
               "sourceRefTime" ->  sourceRefTime=value
            }
        }
    }



//    private fun readInput():String{
//        var userInput = readLine()
//        while( userInput == null || userInput.isBlank()){
//            print("Please enter a value: ")
//            userInput= readLine()
//        }
//        return userInput
//    }


//    private fun getAnswers() {
//        try {
//
//            println("Enter the filename of the calendar to copy: ")
//            inputFile = readInput()
//
//            println("Enter the organizer email : ")
//            organizer_email = readInput()
//
//
//            println("Enter the timezone of the source calendar: e.g. UTC-07")
//            sourceTimezone= readInput()
//
//            println("Enter the timezone of the target calendar: e.g. UTC+08:00 :")
//            targetTimezone = readInput()
//
//            println("Enter the start date in the source calendar: e.g. yyyyMMdd :")
//            sourceRefDate = readInput()
//
//            println("Enter the start date in the timezone of the target calendar e.g. yyyyMMdd : ")
//            targetRefDate = readInput()
//
//            println("Enter the start of business hour of the source calendar: e.g. 09:00 :")
//            sourceRefTime = readInput()
//
//
//            println("Enter the start time in the timezone of the target calendar e.g. 08:30 : ")
//            targetRefTime = readInput()
//
//            println("Enter the name of the output file : ")
//            outputFile = readInput()
//        } catch (e: Exception) {
//            println(e)
//        }
//    }
}


class EventData(
    private var targetZone: ZoneId,
    private var targetRefTime: LocalTime,
    private var targetRefDate: LocalDate,
    private var sourceZone: ZoneId,
    private var sourceRefZonedDateTime: ZonedDateTime
    ){

    private val initZoneDateTime = epochToZonedDateTime()
    private var targetDtStart: ZonedDateTime =initZoneDateTime
    private var targetDtEnd: ZonedDateTime = initZoneDateTime
    private var dtStart: ZonedDateTime =initZoneDateTime
    private var dtEnd: ZonedDateTime =initZoneDateTime
    private var dtStartReminderDate =initZoneDateTime.toLocalDate()
    private var dtEndReminderDate = initZoneDateTime.toLocalDate()


    private fun getDaysInterval():Long{
        return ChronoUnit.DAYS.between(sourceRefZonedDateTime.toLocalDate(), dtStart.toLocalDate())
    }


    private fun getEventInterval():Long{
        return ChronoUnit.MINUTES.between(sourceRefZonedDateTime.toLocalTime(), dtStart.toLocalTime())
    }


    private fun getEventDuration():Long{
        return ChronoUnit.MINUTES.between(dtStart, dtEnd)
    }


    fun setDtStart(dtStartString:String){
        dtStart=dtStartString.toUTCtoSourceZone(sourceZone)

        //What day should this event be added?
        val eventStartDate= targetRefDate.plusDays(getDaysInterval())

        //What time should this event start?
        val eventStartTime=targetRefTime.plusMinutes(getEventInterval())


        //Set targetDtStart
        targetDtStart=ZonedDateTime.of(eventStartDate,eventStartTime,targetZone)

    }


    fun getDtStartUTCString():String{
        return targetDtStart.toUTCString()
    }



    fun setDtEnd(dtEndString:String){
        dtEnd = dtEndString.toUTCtoSourceZone(sourceZone)
        targetDtEnd=targetDtStart.plusMinutes(getEventDuration())
    }


    fun getDtEndUTCString():String{
        return targetDtEnd.toUTCString()
    }



    fun getInputStartDate():String{
        return targetRefDate.toDateString()
    }

    fun setDtStartDate(dtStartDateString:String){
        dtStartReminderDate=dtStartDateString.toLocalDate()
    }

    fun setDtEndDate(dtEndDateString:String){
        dtEndReminderDate=dtEndDateString.toLocalDate()
    }

    fun getDtEndDate():String{
        val dtDateDuration = ChronoUnit.DAYS.between(dtStartReminderDate, dtEndReminderDate)
        return targetRefDate.plusDays(dtDateDuration).toDateString()
    }
}
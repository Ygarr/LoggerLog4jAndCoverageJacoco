package com.company;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.MissingResourceException;

/**
 * Класс для генерации сообщений-приветствий.
 * Промежуточные данные при работе методов класса
 * записываются в лог-файл messenger.log,
 * который создается в корневой папке проекта.
 */
public class Messenger {

    private static Logger logger = LogManager.getLogger(Messenger.class);

    public static void main(String[] args) {
        printCurrentTimeMessage();
    }

    /**
     * Метод выводит в консоль сообщение-приветствие,
     * соответсвующее текущему времени суток и локали пользователя.
     *  в зависимости от языка локали...
     */
    public static void printCurrentTimeMessage() {
        int currentHour = new GregorianCalendar().get(Calendar.HOUR_OF_DAY);
        Locale userLocale = Locale.getDefault();
        System.out.println(getMessage(currentHour, userLocale));
    }

    /**
     * Метод выдающий сообщение, которое зависит от переданных методу
     * значения часа (в формате от 0 до 23) и локали.
     * Если язык локали - русский, то сообщения
     * выдаются на русском, иначе - на английском.
     */
    public static String getMessage(int hour, Locale locale) {

        logger.debug("---getMessage()_____________begin---");

        logger.debug("Got current hour: " + hour);
        String timeOfDay = defineTimeOfDay(hour);
        logger.debug("Current time of day: " + timeOfDay);
        logger.debug("Got user's locale: " + locale);
        //Если локаль РУ, то делаем ее АНГЛ
        locale = locale == null || !"ru".equals(locale.getLanguage()) ? Locale.ENGLISH : locale;
        logger.debug("Locale after validation: " + locale);

        String message;
        try {
            logger.debug("Connecting to message resource...");
            ResourceBundle bundle = ResourceBundle.getBundle("Messages", locale);///???????????????????????????????????/
            message = bundle.getString(timeOfDay);
            logger.debug("Connection succeeded!");
        } catch (MissingResourceException ex){
            logger.debug("Connection failed!");
            logger.debug("!!!" + ex.getClass().getName());
            logger.debug("!!!" + ex.getMessage());
            message = "Can't connect to message resource. Read messenger.log.";
        }
        logger.debug("Returned message: " + message + " Теперь проверь логи");
        logger.debug("----getMessage()________________end----\n\r");

        return message;
    }

    /**
     * Метод определяет время суток по переданному значению
     * часа (в формате от 0 до 23).
     */
    private static String defineTimeOfDay(int hour) {
        logger.debug("--defineTimeOfDay() method begin--");
        String timeOfDay = "";
        if (hour >= 6 && hour < 9) timeOfDay = "morning";
        if (hour >= 9 && hour < 19) timeOfDay = "day";
        if (hour >= 19 && hour < 23) timeOfDay = "evening";
        if (hour >= 23 || hour < 6) timeOfDay = "night";
        logger.debug("---defineTimeOfDay() method end---");
        return timeOfDay;
    }

}

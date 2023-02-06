package Util;

import android.content.Context;
import android.util.Log;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Converter {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    public static Date gettDate(String date) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return f.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.YEAR) >= 1753) {
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            //return String.format("{0}/{1}/{2}", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
            //return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" + String.valueOf(calendar.get(Calendar.YEAR));
            return f.format(calendar.getTime());
        }
        return null;
    }

    public static String formatDate(String date, String pattern) {

        String result = null;
        SimpleDateFormat textFormat = new SimpleDateFormat(pattern);

        try {
            result = textFormat.format(textFormat.parse(date));
        } catch (Exception ex) {
            Log.e("ERROR", ex.toString());
        }

        return result;
    }

    public static String formatDateToString(String date) {
        String result = null;

        if (date != null) {

            try {
                LocalDateTime lDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                result = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(lDate).toString();
            } catch (Exception ex) {
                Log.e("Convertion Error", "(Util.DatePicker.formatDate) Değer \" + date + \" tarih tipine dönüştürülemedi.");
            }
        }

        return result;
    }

    public static String getItemFromStringArray(Context context, int arrayId, int itemIndex) {
        String[] items = context.getResources().getStringArray(arrayId);
        if (items.length >= itemIndex) {
            return items[itemIndex];
        }
        return null;
    }

    public static <T> String removeDuplicates(final T[] array) {
        List<T> noDuplicates = new ArrayList<T>();
        String result = "";
        for (T arrayElem : array) {
            if (!noDuplicates.contains(arrayElem)) {
                noDuplicates.add(arrayElem);
                result += (!result.isEmpty() ? "," + arrayElem : arrayElem);
            }
        }
        return result;
        //return (T[]) noDuplicates.toArray();
    }
}

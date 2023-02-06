package Util;

import android.content.Context;
import android.text.TextUtils;

import com.example.northwindsampledb.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    Context _context;
    public String Message;

    public Validation(Context context) {
        _context = context;
    }

    boolean regEx(String pattern, String value) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    boolean isNull(String value) {
        if (TextUtils.isEmpty(value)) {
            Message = _context.getResources().getString(R.string.required);
            return true;
        }
        return false;
    }

    public boolean isValidEmail(String email, boolean isRequired) {
        if (isRequired && isNull(email)) {
            return false;
        }
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(_context.getResources().getString(R.string.EMAIL_PATTERN));
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String pass) {
        if (!pass.isEmpty() && pass.length() >= 6) {
            return true;
        }
        return false;
    }

    public boolean isValidNumber(String num) {
        if (!num.isEmpty() && num.length() == 10) {
            return true;
        }
        return false;
    }

    public boolean isValidName(String name) {
        if (!name.isEmpty() && name.length() < 30) {
            return true;
        }
        return false;
    }

    public boolean isValidDate(String date) {
/*        Pattern pattern = Pattern.compile(_context.getResources().getString(R.string.DATE_PATTERN));
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();*/
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.parse(date);
            return true;
        }
        catch(ParseException e){
            return false;
        }
    }

    public boolean isValid(String validaitonTypes, String value, boolean isNull, String dataType, String maxLength) {

        if(!isNull && isNull(value)) {
            Message = _context.getResources().getString(R.string.required);
            return false;
        }

        if(dataType.equals("Date") && !isNull(value)) {
            if (!isValidDate(value)) {
                Message = _context.getResources().getString(R.string.invalid_date);
                return false;
            }
        }

        String[] typeList = validaitonTypes.split(",");
        for (int i=0; i < typeList.length; i++) {
            switch (value){
                case "Range":
                    break;
                case "StringLength":
                    if (maxLength != null && !maxLength.equals("") && value.length() > Integer.parseInt(maxLength)) {
                        Message = _context.getResources().getString(R.string.invalid_string_range);
                        return false;
                    }
                    break;
                case "RegularExpression":
                    break;
                case "CreditCard":
                    break;
                case "EmailAddress":
                    break;
                case "FileExtension":
                    break;
                case "MaxLength":
                    break;
                case "MinLength":
                    break;
                case "Phone":
                    break;
            }
        }

        return true;
    }
}

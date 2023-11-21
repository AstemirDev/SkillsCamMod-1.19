package org.astemir.cammod.client.screen.ui.utils;

public class TextUtils {

    public static String cutFromTo(String text,int begin,int end){
        if (!(begin < 0 || begin > end || end > text.length())) {
            return text.substring(begin,end);
        }
        return text;
    }

    public static String cutFrom(String text,int index){
        return cutFromTo(text,index,text.length());
    }

    public static String cutTo(String text,int index){
        return cutFromTo(text,0,index);
    }

    public static String deleteFromTo(String text,int begin,int end){
        String beginPart = cutTo(text,begin);
        String endPart = cutFrom(text,end);
        if (!beginPart.equals(text) && !endPart.equals(text)){
            return beginPart+endPart;
        }
        return text;
    }

    public static String deleteFrom(String text,int index){
        return deleteFromTo(text,index,text.length());
    }

    public static String deleteTo(String text,int index){
        return deleteFromTo(text,0,index);
    }

    public static String addAt(String text,String addedText,int index) {
        String beginPart = cutTo(text, index);
        String endPart = cutFrom(text, index);
        if (!beginPart.equals(text) && !endPart.equals(text)) {
            return beginPart+addedText+endPart;
        }
        return text;
    }
}

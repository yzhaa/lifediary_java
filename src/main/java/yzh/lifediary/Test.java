package yzh.lifediary;

import yzh.lifediary.util.PictureUtil;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

public class Test {


    private List<? super String  > list;


    public static void main(String[] args) throws NoSuchFieldException {


        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext();) {
            if(iterator.next()==1) iterator.remove();
        }

        for (Integer a:list) {
            System.out.println(a);
            list.remove(a);

        }
    }


}

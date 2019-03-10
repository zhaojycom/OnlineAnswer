package com.zhaojy.onlineanswer.utils;

import com.zhaojy.onlineanswer.bean.ErrorsSort;
import com.zhaojy.onlineanswer.bean.QuestionSort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 中文排序工具
 *
 * @author: zhaojy
 * @data:On 2018/9/2.
 */

public class ChineseSortUtil {

    /**
     * 中文集合排序题目分类
     *
     * @param contactsList
     */
    public static void sort(List<QuestionSort> contactsList) {
        Collections.sort(contactsList, new Comparator<QuestionSort>() {
            @Override
            public int compare(QuestionSort o1, QuestionSort o2) {
                Comparator comparator = com.ibm.icu.text.Collator.getInstance(com.ibm.icu.util.ULocale.SIMPLIFIED_CHINESE);

                return comparator.compare(o1.getName(), o2.getName());
            }
        });
    }

    /**
     * 中文集合排序我的错题分类
     *
     * @param contactsList
     */
    public static void sortErrorSorts(List<ErrorsSort> contactsList) {
        Collections.sort(contactsList, new Comparator<ErrorsSort>() {
            @Override
            public int compare(ErrorsSort o1, ErrorsSort o2) {
                Comparator comparator = com.ibm.icu.text.Collator.getInstance(com.ibm.icu.util.ULocale.SIMPLIFIED_CHINESE);

                return comparator.compare(o1.getName(), o2.getName());
            }
        });
    }

}

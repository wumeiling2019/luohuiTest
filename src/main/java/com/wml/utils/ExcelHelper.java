package com.wml.utils;

import com.wml.model.ExcelInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel取值帮手
 */
public class ExcelHelper {

    /**
     * 根据excel的map 转换为数组
     *  第一个为 请求参数
     *  第二个为用例说明,
     *  第三个参数为执行用例的预期结果
     * @param caseExcelMap
     * @return  返回excel中用例对应的object类型list
     */
    public static Object[] getObjArrByMap(Map<String,String> caseExcelMap){
        Map<String,String> caseParam = new HashMap<String,String>();
        Map<String,String> caseDesc = new HashMap<String,String>();
        Map<String,String> casePreset =new HashMap<String,String>();
        ExcelInfo ci = new ExcelInfo();
        for (String key : caseExcelMap.keySet()) {
            if (key.indexOf("{$d}")== 0){
                caseDesc.put(key.replace("{$d}", ""), caseExcelMap.get(key));
            }
            else if(key.indexOf("{$p}") == 0){
                casePreset.put(key.replace("{$p}", ""), caseExcelMap.get(key));
            }
            else {
                String strValue = caseExcelMap.get(key);
                if (!strValue.equals("")){
                    caseParam.put(key, strValue);
                }
            }
        }
        ci.setCaseDesc(caseDesc);
        ci.setCaseParam(caseParam);
        ci.setCasePreset(casePreset);
        return new Object[]{ci};
    }

    /**
     * 根据excel获取的list转换为  Object[][]
     * @param caseExcelList
     * @return
     */
    public static Object[][] getObjArrByList(List<Map<String,String>> caseExcelList){
        List<Map<String,String>> caseExcuteList = getExcuteList(caseExcelList);
        Object[][] objArray = new Object[caseExcuteList.size()][];
        for(int i = 0;i<caseExcuteList.size();i++){
            objArray[i] = getObjArrByMap(caseExcuteList.get(i));
        }
        return objArray;

    }

    /**
     * 筛选出需要执行的用例
     * @param caseExcelList
     * @return
     */
    private static List<Map<String,String>> getExcuteList(List<Map<String,String>> caseExcelList){
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for( Map<String,String> m : caseExcelList){
            String str = m.get("{$d}isexcute").trim().toLowerCase();
            if (str.equals("y")){
                list.add(m);
            }
        }
        return list;
    }

}

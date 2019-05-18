package com.wml.model;

import java.util.Map;

/**
 * excel对应的model
 */
public class ExcelInfo {

    //{$d}isexcute 为y的时候表示需要执行

    //用例参数 在excel中只以字段名开头  请求参数
    private Map<String,String> caseParam;
    //用例说明 在excel中以{$d}开头   用例说明
    private Map<String,String> caseDesc;
    //用例预期结果 在excel中以{$p}开头  预期结果
    private Map<String,String> casePreset;

    public Map<String, String> getCaseParam() {
        return caseParam;
    }
    public void setCaseParam(Map<String, String> caseParam) {
        this.caseParam = caseParam;
    }
    public Map<String, String> getCaseDesc() {
        return caseDesc;
    }
    public void setCaseDesc(Map<String, String> caseDesc) {
        this.caseDesc = caseDesc;
    }
    public Map<String, String> getCasePreset() {
        return casePreset;
    }
    public void setCasePreset(Map<String, String> casePreset) {
        this.casePreset = casePreset;
    }
}

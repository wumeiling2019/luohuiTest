package com.wml.cases;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wml.config.TestConfig;
import com.wml.model.ExcelInfo;
import com.wml.model.HzFirstInfo;
import com.wml.model.HzSecondInfo;
import com.wml.model.NetMoneyFirstInfo;
import com.wml.utils.CommonMethodUtil;
import com.wml.utils.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class  BaseApi {

    /**
     * 执行post请求
     * @param urlname  url请求地址
     * @param reqParam  请求参数
     * @return
     * @throws IOException
     */
    public static String post(String urlname, JSONObject reqParam) throws IOException {
        String resEntity = "";
        HttpPost post = new HttpPost(urlname);
        post.setHeader("Content-Type","application/json");
        StringEntity entity = new StringEntity(reqParam.toString(),"utf-8");
        post.setEntity(entity);
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == 200){
            resEntity = EntityUtils.toString(response.getEntity(),"utf-8");
        }
        return resEntity;
    }

    /**
     *  第一步 设置参数
     * @param H5orApp  1为H5,2为App
     * @param json_path  json存放路径
     * @param firstInfo 用户第一步信息
     * @return
     */
    public static JSONObject setReqParam(Integer H5orApp, String json_path, HzFirstInfo firstInfo){
        String json = FileUtils.getContentByLine(json_path);
        JSONObject reqParam = JSONObject.parseObject(json);

        // 第一个可变参数 data中的phone
        JSONObject data = JSONObject.parseObject(reqParam.getString("data"));
        // 设置手机号
        if(data.getString("phone") == null){
            data.put("phoneNumber",firstInfo.getPhone());
        }else {
            data.put("phone",firstInfo.getPhone());
        }
        // 设置验证码
        if(!firstInfo.getCode().isEmpty()){
            data.put("verifyCode", firstInfo.getCode());
        }

        // 设置渠道
        if(!firstInfo.getChannelCode().isEmpty()){
            data.put("channelCode", firstInfo.getChannelCode());
        }

        // 设置姓名和性别
        if(!firstInfo.getMemberName().isEmpty()){
            data.put("memberName", firstInfo.getMemberName());
            data.put("gender", firstInfo.getGender());
        }
        reqParam.put("data",data);


        // 第二个可变参数 sign
        String sign = "";
        if(H5orApp == 1){
            sign = CommonMethodUtil.getMD5String(reqParam.getString("apiKey") + ""
                    + data.toString() + TestConfig.SALT_H5);
        }else if(H5orApp == 2){
            sign = CommonMethodUtil.getMD5String(reqParam.getString("apiKey") + ""
                    + data.toString() + TestConfig.SALT_APP);
        }
        reqParam.put("sign",sign);
        return reqParam;
    }

    /**
     * 大网钱设置第一步
     * @param json_path
     * @param phone
     * @return
     */
    public static JSONObject setNocodeReqParam( String json_path, String phone){
        String json = FileUtils.getContentByLine(json_path);
        JSONObject reqParam = JSONObject.parseObject(json);

        // 第一个可变参数 data中的phone
        JSONObject data = JSONObject.parseObject(reqParam.getString("data"));
        // 设置手机号
        data.put("mobile",phone);
        // 第二个可变参数 sign
        String sign = CommonMethodUtil.getMD5String(reqParam.getString("apiKey") + ""
                + data.toString() + TestConfig.SALT_H5);
        reqParam.put("sign",sign);
        return reqParam;

    }

    /**
     * 第二部修改userId
     * @param json_path
     * @param userId
     * @return
     */
    public static JSONObject setSecondData(String json_path,String userId){
        String json = FileUtils.getContentByLine(json_path);
        JSONObject reqParam = JSONObject.parseObject(json);

        // 可变参数 data中的phone
        JSONObject data = JSONObject.parseObject(reqParam.getString("data"));
        data.put("loanUserCode",userId);
        reqParam.put("data",data);

        // 设置sign
        String sign = CommonMethodUtil.getMD5String(reqParam.getString("apiKey") + ""
                + data.toString() + TestConfig.SALT_H5);

        reqParam.put("sign",sign);
        return reqParam;
    }

    /**
     * 华赞第二步根据excel中的值设置
     * @param json_path
     * @param userId
     * @return
     */
    public static JSONObject setSecondData(String json_path, String userId, ExcelInfo excelInfo)
            throws JsonProcessingException {
        String json = FileUtils.getContentByLine(json_path);
        JSONObject reqParam = JSONObject.parseObject(json);

        // 可变参数 data中的phone
        HzSecondInfo secondInfo = setSecondDataExcel(userId, excelInfo);
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(secondInfo);
        reqParam.put("data",data);

        // 设置sign
        String sign = CommonMethodUtil.getMD5String(reqParam.getString("apiKey") + ""
                + data.toString() + TestConfig.SALT_H5);
        reqParam.put("sign",sign);
        return reqParam;
    }

    /**
     * 华赞设置excel中第二页详情页数据
     * @param userId
     * @param excelInfo
     * @return
     */
    public static HzSecondInfo setSecondDataExcel(String userId, ExcelInfo excelInfo){
        HzSecondInfo secondInfo = new HzSecondInfo();

        // 设置城市
        secondInfo.setLoanCityNameSecond(excelInfo.getCaseParam().get("loanCityNameSecond"));
        // 设置生日
        secondInfo.setBirthday(excelInfo.getCaseParam().get("birthday"));
        // 设置userId
        secondInfo.setLoanUserCode(userId);

        // 设置titleReqList
        List<HzSecondInfo.TitleReqListEntity> titleReqList = new ArrayList<HzSecondInfo.TitleReqListEntity>();
        HzSecondInfo.TitleReqListEntity titleReqEntity;

        List<String> optionKey;
        for(int i=1;i <= 15;i++){
            String titleKeyStr = "titleKey";
            String optionKeyStr = "optionKey";
            titleKeyStr = titleKeyStr + i;
            optionKeyStr = optionKeyStr + i;
            titleReqEntity = new HzSecondInfo.TitleReqListEntity();
            titleReqEntity.setTitleKey(excelInfo.getCaseParam().get(titleKeyStr));
            optionKey = new ArrayList<String>();
            optionKey.add(excelInfo.getCaseParam().get(optionKeyStr));
            titleReqEntity.setOptionKey(optionKey);
            titleReqList.add(titleReqEntity);
        }
        // 设置详情
        secondInfo.setTitleReqList(titleReqList);
        return secondInfo;
    }

    public static JSONObject setNetMoneyFirstExcel(String json_path,
                                                   String phone,ExcelInfo excelInfo){
        String json = FileUtils.getContentByLine(json_path);
        JSONObject reqParam = JSONObject.parseObject(json);

        JSONObject data = JSONObject.parseObject(reqParam.getString("data"));
        data.put("username",excelInfo.getCaseParam().get("username"));
        data.put("appSex",Integer.parseInt(excelInfo.getCaseParam().get("appSex")));
        data.put("creditCard",Integer.parseInt(excelInfo.getCaseParam().get("creditCard")));
        data.put("mobile",phone);
        reqParam.put("data",data);

        // 设置sign
        String sign = CommonMethodUtil.getMD5String(reqParam.getString("apiKey") + ""
                + data.toString() + TestConfig.SALT_H5);
        reqParam.put("sign",sign);
        return reqParam;
    }

    public static JSONObject setNetMoneySecondExcel(String json_path,
                                                   String dwqCode,ExcelInfo excelInfo){
        String json = FileUtils.getContentByLine(json_path);
        JSONObject reqParam = JSONObject.parseObject(json);
        JSONObject data = JSONObject.parseObject(reqParam.getString("data"));
        data.put("city",excelInfo.getCaseParam().get("city"));
        data.put("appBirthday",excelInfo.getCaseParam().get("appBirthday"));
        data.put("loanMoney",excelInfo.getCaseParam().get("loanMoney"));
        data.put("security",Integer.parseInt(excelInfo.getCaseParam().get("security")));
        data.put("fund",Integer.parseInt(excelInfo.getCaseParam().get("fund")));
        data.put("house",Integer.parseInt(excelInfo.getCaseParam().get("house")));
        data.put("hasHouseLoan",Integer.parseInt(excelInfo.getCaseParam().get("hasHouseLoan")));
        data.put("car",Integer.parseInt(excelInfo.getCaseParam().get("car")));
        data.put("hasCarLoan",Integer.parseInt(excelInfo.getCaseParam().get("hasCarLoan")));
        data.put("policy",Integer.parseInt(excelInfo.getCaseParam().get("policy")));
        data.put("weilidai",Integer.parseInt(excelInfo.getCaseParam().get("weilidai")));
        data.put("dwqCode",dwqCode);
        reqParam.put("data", data);

        // 设置sign
        String sign = CommonMethodUtil.getMD5String(reqParam.getString("apiKey") + ""
                + data.toString() + TestConfig.SALT_H5);
        reqParam.put("sign",sign);
        return reqParam;

    }
}

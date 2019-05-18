package com.wml.cases.hz;

import com.wml.model.ExcelInfo;
import com.wml.model.HzFirstInfo;
import com.wml.utils.ExcelHelper;
import com.wml.utils.ExcelUtils;
import org.json.JSONObject;
import com.wml.cases.BaseApi;
import com.wml.cases.BaseTest;
import com.wml.config.SmsCodeTypeEnum;
import com.wml.utils.CommonMethodUtil;
import com.wml.utils.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HzH5Register extends BaseTest {

    private String H5_getCode_filePath = "requestJson/hz/getHzH5VerCode.txt";
    private String H5_first_filePath = "requestJson/hz/H5CodeFirstRegister.txt";
    private String H5_second_filePath = "requestJson/hz/H5CodeSecondRegister.txt";
    private String H5_excel = "F:\\testDataExcel\\hzTestData.xls";
    private String H5_url = "url/url.txt";
    private String userId;

    @DataProvider(name = "dataInfo")
    public Object[][] dataInfo1(){
        Object[][] myObj = null;
        List<Map<String, String>> list = ExcelUtils.readXlsx(H5_excel);
        myObj = ExcelHelper.getObjArrByList(list);
        return myObj;
    }

    /**
     * 随机注册
     */
    @Test(invocationCount =100,threadPoolSize = 1)
    public void hzRegister() throws IOException {
        // 获取手机号
        String phone = CommonMethodUtil.getTelphone();

        // 获取验证码
        String code = getCode(phone);

        // 推广页第一步注册
        if(!code.isEmpty()){
            H5_first_register(code, phone);
        }
        CommonMethodUtil.sleep(1000);
        // 推广页第二步注册
        if(userId != null){
            ExcelInfo excelInfo = new ExcelInfo();
            H5_second_register(1,excelInfo);
        }
        CommonMethodUtil.sleep(1000);
    }

    /**
     * 根据手机号注册
     */
    public void hzRegister(String phone) throws IOException {

        // 获取验证码
        String code = getCode(phone);

        // 推广页第一步注册
        if(!code.isEmpty()){
            H5_first_register(code, phone);
        }
        CommonMethodUtil.sleep(1000);
        // 推广页第二步注册
        if(userId != null){
            ExcelInfo excelInfo = new ExcelInfo();
            H5_second_register(1,excelInfo);
        }
        CommonMethodUtil.sleep(1000);
    }

    /**
     * 根据excel执行测试用例
     * @param excelInfo
     * @throws IOException
     */
    @Test(dataProvider = "dataInfo" )
    public void H5_Register_excel(ExcelInfo excelInfo) throws IOException {
        // 获取手机号
        String phone = CommonMethodUtil.getTelphone();

        // 获取验证码
        String code = getCode(phone);

        // 推广页第一步注册
        if(!code.isEmpty()){
            H5_firstExcel_register(code, phone, excelInfo);
        }
        CommonMethodUtil.sleep(1000);
        // 推广页第二步注册
        if(userId != null){
            H5_second_register(2,excelInfo);
        }
        CommonMethodUtil.sleep(1500);
    }

    /**
     * 获取验证码
     * @param phone
     * @return
     * @throws IOException
     */
    public String getCode(String phone) throws IOException {
        String code = "";

        // 请求url
        String url = FileUtils.getListTxtContent(H5_url,"hzH5");
        if (url != null){
            HzFirstInfo firstInfo = new HzFirstInfo();
            firstInfo.setPhone(phone);
            firstInfo.setCode("");
            firstInfo.setChannelCode("");
            String resEntity = BaseApi.post(url, BaseApi.setReqParam(1,H5_getCode_filePath,firstInfo));
            if (!resEntity.isEmpty()){
                // 取缓存中的验证码
                code = CommonMethodUtil.getRedisCode(phone,
                        SmsCodeTypeEnum.HUAZAN_GENERALIZE_REG).replace("\"", "");
            }
        }
        return code;
    }

    /**
     * 华赞H5第一步注册
     * @param code
     * @param phone
     * @throws IOException
     */
    public void H5_first_register(String code,String phone) throws IOException {
        // 请求url
        String url = FileUtils.getListTxtContent(H5_url,"hzH5");
        if (url != null){
            HzFirstInfo firstInfo = new HzFirstInfo();
            firstInfo.setPhone(phone);
            firstInfo.setCode(code);
            firstInfo.setChannelCode("");
            String resEntity = BaseApi.post(url, BaseApi.setReqParam(1,H5_first_filePath,firstInfo));
            setUserId(resEntity,phone);
        }
    }

    /**
     * 华赞H5第一步注册
     * @param code
     * @param phone
     * @throws IOException
     */
    public void H5_firstExcel_register(String code,String phone,ExcelInfo excelInfo) throws IOException {
        // 请求url
        String url = FileUtils.getListTxtContent(H5_url,"hzH5");
        if (url != null){
            // 设置请求参数
            HzFirstInfo firstInfo = new HzFirstInfo();
            firstInfo.setPhone(phone);
            firstInfo.setCode(code);
            firstInfo.setMemberName(excelInfo.getCaseParam().get("memberName"));
            firstInfo.setGender(Integer.parseInt(excelInfo.getCaseParam().get("gender")));
            firstInfo.setChannelCode(excelInfo.getCaseParam().get("channelCode"));
            // 执行post请求
            String resEntity = BaseApi.post(url, BaseApi.setReqParam(1,H5_first_filePath,firstInfo));
            setUserId(resEntity,phone);
        }
    }

    /**
     * 根据第一步结果设置userId
     * @param resEntity
     * @param phone
     */
    public void setUserId(String resEntity,String phone){
        if (!resEntity.isEmpty()){
            System.out.println(phone);
            // 获取userId
            JSONObject resArr = new JSONObject(resEntity);
            if(!resArr.get("code").equals("success")){
                System.out.println("第一步成功失败，该用例未执行成功");
            }else{
                JSONObject resData = (JSONObject) resArr.get("data");
                userId = resData.get("userId").toString();
            }
        }
    }

    /**
     * 华赞推广第二页
     * @param type 1为随机，2为excel
     * @throws IOException
     */
    public void H5_second_register(Integer type,ExcelInfo excelInfo) throws IOException {
        // 请求url
        String url = FileUtils.getListTxtContent(H5_url,"hzH5");
        if (url != null){
            String resEntity;
            if(type == 1){
                resEntity = BaseApi.post(url, BaseApi.setSecondData(H5_second_filePath, userId));
            }else{
                resEntity = BaseApi.post(url, BaseApi.setSecondData(H5_second_filePath, userId,excelInfo));
            }

            if (!resEntity.isEmpty()){
                System.out.println(userId);
            }
        }
    }


}

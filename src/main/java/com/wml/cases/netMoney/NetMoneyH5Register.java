package com.wml.cases.netMoney;

import com.wml.cases.BaseApi;
import com.wml.cases.BaseTest;
import com.wml.model.ExcelInfo;
import com.wml.model.HzFirstInfo;
import com.wml.model.NetMoneyFirstInfo;
import com.wml.utils.CommonMethodUtil;
import com.wml.utils.ExcelHelper;
import com.wml.utils.ExcelUtils;
import com.wml.utils.FileUtils;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NetMoneyH5Register extends BaseTest {

    private String H5_first_filePath = "requestJson/netMoney/NetMoneyFirst.txt";
    private String H5_second_filePath = "requestJson/netMoney/NetMoneySecond.txt";
    private String H5_excel = "F:\\testDataExcel\\netMoneyTestData.xls";
    private String H5_url = "url/url.txt";
    private String dwqCode;

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
    @Test(invocationCount = 30,threadPoolSize = 1)
    public void netMoneyH5Register() throws IOException {
        // 获取手机号
        String phone = CommonMethodUtil.getTelphone();
        ExcelInfo excelInfo = new ExcelInfo();
        H5_first_register(1,phone,excelInfo);
        CommonMethodUtil.sleep(1000);

        // 推广页第二步注册
        if(dwqCode != null){
            H5_second_register(1,excelInfo);
        }
        CommonMethodUtil.sleep(1000);
    }

    /**
     * 根据手机号注册
     */
    public void netMoneyH5Register(String phone) throws IOException {

        ExcelInfo excelInfo = new ExcelInfo();
        H5_first_register(1,phone,excelInfo);
        CommonMethodUtil.sleep(500);
        // 推广页第二步注册
        if(dwqCode != null){
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
    public void netMoneyH5Register_excel(ExcelInfo excelInfo) throws IOException {
        // 获取手机号
        String phone = CommonMethodUtil.getTelphone();
        H5_first_register(2,phone,excelInfo);
        CommonMethodUtil.sleep(1000);

        // 推广页第二步注册
        if(dwqCode != null){
            H5_second_register(2,excelInfo);
        }
        CommonMethodUtil.sleep(1000);
    }

    /**
     * 大网钱H5第一步注册
     * @param type 1为随机，2为excel
     * @param phone
     * @throws IOException
     */
    public void H5_first_register(Integer type,String phone, ExcelInfo excelInfo) throws IOException {
        // 请求url
        String url = FileUtils.getListTxtContent(H5_url,"NetMoneyH5");
        if (url != null){
            String resEntity;
            if(type == 1){
                resEntity = BaseApi.post(url, BaseApi.setNocodeReqParam(H5_first_filePath,phone));
            }else{
                resEntity = BaseApi.post(url, BaseApi.setNetMoneyFirstExcel(H5_first_filePath,phone,excelInfo));
            }
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
                dwqCode = resData.get("dwqCode").toString();
            }


        }
    }

    /**
     * 大网钱推广第二页
     * @param type 1为随机，2为excel
     * @throws IOException
     */
    public void H5_second_register(Integer type,ExcelInfo excelInfo) throws IOException {
        // 请求url
        String url = FileUtils.getListTxtContent(H5_url,"NetMoneyH5");
        if (url != null){
            String resEntity;
            if(type == 1){
                resEntity = BaseApi.post(url, BaseApi.setSecondData(H5_second_filePath, dwqCode));
            }else{
                resEntity = BaseApi.post(url, BaseApi.setNetMoneySecondExcel(H5_second_filePath, dwqCode,excelInfo));
            }

            if (!resEntity.isEmpty()){
                System.out.println(dwqCode);
            }
        }
    }
}

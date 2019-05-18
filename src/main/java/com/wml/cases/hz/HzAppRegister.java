package com.wml.cases.hz;

import com.wml.cases.BaseApi;
import com.wml.cases.BaseTest;
import com.wml.config.SmsCodeTypeEnum;
import com.wml.model.HzFirstInfo;
import com.wml.utils.CommonMethodUtil;
import com.wml.utils.FileUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class HzAppRegister extends BaseTest {

    private String App_getCode_filePath = "requestJson/hz/getHzAppVerCode.txt";
    private String App_Rigister_filePath = "requestJson/hz/AppHzRegister.txt";
    private String App_url = "url/url.txt";


    @Test(invocationCount = 1,threadPoolSize = 1)
    public void AppRegister() throws IOException {
        // 获取手机号
        String phone = CommonMethodUtil.getTelphone();

        // 获取验证码
        String code = getCode(phone);
        CommonMethodUtil.sleep(500);

        //华赞App推广注册
        if(!code.isEmpty()){
            App_register(code, phone);
        }
    }

    /**
     * 根据手机号华赞App激活
     * @param phone
     * @throws IOException
     */
    public void AppRegister(String phone) throws IOException {
        // 获取验证码
        String code = getCode(phone);
        CommonMethodUtil.sleep(500);
        //华赞App推广注册
        if(!code.isEmpty()){
            App_register(code, phone);
        }
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
        String url = FileUtils.getListTxtContent(App_url,"App");
        if (url != null){
            HzFirstInfo firstInfo = new HzFirstInfo();
            firstInfo.setPhone(phone);
            firstInfo.setCode("");
            String resEntity = BaseApi.post(url, BaseApi.setReqParam(2,App_getCode_filePath,firstInfo));
            if (!resEntity.isEmpty()){
                // 取缓存中的验证码
                code = CommonMethodUtil.getRedisCode(phone,
                        SmsCodeTypeEnum.HUAZAN_LOGIN).replace("\"", "");
            }
        }

        return code;
    }

    /**
     * 华赞App验证码注册
     * @param code
     * @param phone
     * @throws IOException
     */
    public void App_register(String code,String phone) throws IOException {
        // 请求url
        String url = FileUtils.getListTxtContent(App_url,"App");
        if (url != null){
            HzFirstInfo firstInfo = new HzFirstInfo();
            firstInfo.setPhone(phone);
            firstInfo.setCode(code);
            String resEntity = BaseApi.post(url, BaseApi.setReqParam(2,App_Rigister_filePath,firstInfo));
            if (!resEntity.isEmpty()){
                System.out.println(phone);
            }
        }
    }
}

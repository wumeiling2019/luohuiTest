package com.wml.cases.qdx;
import com.wml.cases.BaseApi;
import com.wml.config.SmsCodeTypeEnum;
import com.wml.model.HzFirstInfo;
import com.wml.utils.CommonMethodUtil;
import com.wml.cases.BaseTest;
import com.wml.utils.FileUtils;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * 抢单侠推广H5注册
 */
public class QdxH5Register extends BaseTest {

    private String H5_getCode_filePath = "requestJson/qdx/getQdxH5VerCode.txt";
    private String H5_Rigister_filePath = "requestJson/qdx/H5Register.txt";
    private String H5_url = "url/url.txt";

    @Test(invocationCount = 1,threadPoolSize = 1)
    public void H5Register() throws IOException {
        // 获取手机号
        String phone = CommonMethodUtil.getTelphone();

        // 获取验证码
        String code = getCode(phone);
        CommonMethodUtil.sleep(500);
        //抢单侠推广注册
        if(!code.isEmpty()){
            H5_register(code, phone);
        }

    }

    /**
     * 根据手机号注册抢单侠H5
     * @param phone
     * @throws IOException
     */
    public void H5Register(String phone) throws IOException {

        // 获取验证码
        String code = getCode(phone);
        CommonMethodUtil.sleep(500);
        //抢单侠推广注册
        if(!code.isEmpty()){
            H5_register(code, phone);
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
        String url = FileUtils.getListTxtContent(H5_url,"qdxH5");
        if (url != null){
            HzFirstInfo firstInfo = new HzFirstInfo();
            firstInfo.setPhone(phone);
            firstInfo.setCode("");
            String resEntity = BaseApi.post(url, BaseApi.setReqParam(1,H5_getCode_filePath,firstInfo));
            if (!resEntity.isEmpty()){
                // 取缓存中的验证码
                code = CommonMethodUtil.getRedisCode(phone,
                        SmsCodeTypeEnum.QIANGDANXIA_GENERALIZE_REG).replace("\"", "");
            }
        }

        return code;
    }

    /**
     * 抢单侠H5注册
     * @param code
     * @param phone
     * @throws IOException
     */
    public void H5_register(String code,String phone) throws IOException {
        // 请求url
        String url = FileUtils.getListTxtContent(H5_url,"qdxH5");
        if (url != null){
            HzFirstInfo firstInfo = new HzFirstInfo();
            firstInfo.setPhone(phone);
            firstInfo.setCode(code);
            String resEntity = BaseApi.post(url, BaseApi.setReqParam(1,H5_Rigister_filePath,firstInfo));
            if (!resEntity.isEmpty()){
                System.out.println(phone);
            }
        }
    }




}

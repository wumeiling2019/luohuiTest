package com.wml.cases.hz;

import com.wml.cases.BaseTest;
import com.wml.utils.CommonMethodUtil;
import org.testng.annotations.Test;

import java.io.IOException;

public class HzH5App extends BaseTest{

    private String phone = CommonMethodUtil.getTelphone();

    @Test(invocationCount = 1,threadPoolSize = 1)
    public void HzH5App_register() throws IOException {
        // 华赞H5注册
        HzH5Register hzH5Register = new HzH5Register();
        hzH5Register.hzRegister(phone);

        // 华赞App激活
        HzAppRegister hzAppRegister = new HzAppRegister();
        hzAppRegister.AppRegister(phone);
    }
}

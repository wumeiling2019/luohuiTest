package com.wml.cases.qdx;

import com.wml.cases.BaseTest;
import com.wml.utils.CommonMethodUtil;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * 抢单侠H5推广后并激活
 *
 **/
public class QdxH5App extends BaseTest {

    private String phone = CommonMethodUtil.getTelphone();

    @Test(invocationCount = 20,threadPoolSize = 1)
    public void QdxH5App_register() throws IOException {
        // 抢单侠H5注册
        QdxH5Register qdxH5Register = new QdxH5Register();
        qdxH5Register.H5Register(phone);

        // 抢单侠App激活
        QdxAppRegister qdxAppRegister = new QdxAppRegister();
        qdxAppRegister.AppRegister(phone);
    }

}

package com.wml.cases;

import com.wml.config.TestConfig;
import com.wml.utils.ExcelHelper;
import com.wml.utils.ExcelUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

public class BaseTest {

    @BeforeTest
    public void beforeTest() {
        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }


}

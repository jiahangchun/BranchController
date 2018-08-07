package com.jiahangchun;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.InvalidAlgorithmParameterException;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/8/6 下午3:14
 **/
public class AesTest {

    public static Gson gson;

    static {
        GsonBuilder gb = new GsonBuilder();
        gb.disableHtmlEscaping();
        gb.setFieldNamingPolicy(
                FieldNamingPolicy.IDENTITY);
        gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = gb.create();
    }

    public static void main(String[] args) throws Exception {
        int count=0;
//        for(int i=0;i<10000;i++) {
            AesTest aesTest = new AesTest();
            aesTest.testMobile();
            count++;
//        }
        System.out.println("count="+count);
    }


    public void testMobile() throws Exception {
        //        String cacheKey="session_key_"+"shop01C5DIG1J1_"+"userId";

//          1111
//        String encryedMobile="BbXZLDZ%2FK2mJr2upY66lrqfpUz4Tuid0v9hXd8s9qmz%2BfWVcyl0zSzFjgRyvhfmUCCVFk0fGTQD2n%2FR7EGsqoOzpQ7ri%2BZRjX%2FFxfrBW4dDIbTTRuiiwqh5APVBSiwnSTmLMXRh7GyqIQjV6o%2BXi0DnLTo%2B38HYk7opHlQfjABHN4G3eWVDmxfzsil35Ava%2FQ7W6WcKe5QBjE6xFg9guBg%3D%3D";
//        String iv="nzqRsu3pPtoxM9yFXAdTZA%3D%3D";

        //charles
//        String encryedMobile="BbXZLDZ/K2mJr2upY66lrqfpUz4Tuid0v9hXd8s9qmz+fWVcyl0zSzFjgRyvhfmUCCVFk0fGTQD2n/R7EGsqoOzpQ7ri+ZRjX/FxfrBW4dDIbTTRuiiwqh5APVBSiwnSTmLMXRh7GyqIQjV6o+Xi0DnLTo+38HYk7opHlQfjABHN4G3eWVDmxfzsil35Ava/Q7W6WcKe5QBjE6xFg9guBg==";
//        String iv="nzqRsu3pPtoxM9yFXAdTZA==";
//        String sessionKey="ScF5oBK2OlHsMjVzJeCRTQ==";


//        String encryedMobile="7VZMK+r7SavhPHV6gSGEzeim55hju1+JisjJJdZqOcXE65zQoVMnWKSI3mDm9o+ipZId5vVwqqhwW2kviGJuWlcjyOlcPQEECL3Hm7B+LUe1zXTpd3RT5YNtmAXd34Vy28I7i5dqtf2wYY1K0sUe2m7RWYKUGF9DgSk7i2w+tQN3xGrXAnDf84L3DjpndrK5tbxxCMfKGr8RQkOH87z55A==";
//        String iv="9BNkcNpzJ4/Vaf4K5MKGUA==";
////        String sessionKey="ScF5oBK2OlHsMjVzJeCRTQ==";
//        String sessionKey="YP3CMAFsM88LBmMNHS14kg==";


        //null
        String encryedMobile="xFXxoZ4kPtQX4L+SCVn0VCvHbC0v8e8mFzvHX/kAiWh0g8qRM7LLyjqMsOgJRodtixXLAg96uPS5yKkSRNBfenaJuWQ6l6fIUY0qbZo6QWaoJ4OOSBimqgupVHSTP0cI+UE8tURm3BeAVecmGIkA7DqQs9S5lxLajStxjJS5aSCzEWcA7hZWhFanky92ddef7okLCpYycFmOPEmWjMDyAA==";
        String iv="50tkRn5Z0sbpaqBvc3OX/w==";
        String sessionKey="vDL2qM/190yW4P7lp8Rbsg==";


        //第一次
//        encryedMobile b7c9vAh5R4BT8OQani3q1SyePoD5dGx3GJNcda12Z/G+ll9V+n6HbeRAGpCNPAj2mcLn9wWiYA3aAkcXkljexSmv2bvob+b4HLFb+hivVZTitsVyL1nFlYQPZRom3AQ+U0hISAvT9/EVDbtwzA1RfRGC64j5hRou8Hlu/nsEkD3LpS62BSTr4i4AVIOscurY8D1K93U6TRefvWVIboAYSQ==
//        sessionKey 9queowM4Q2joiA4f1Du/7w==
//        iv y3FSx0RTreEaIJLaX4egng==

//        2
//        fOy87KeR5ch42kNfEmEO7pqv3PzSLMmcOBLxPIHKInzOYtZICdhn8Z13k7pTvWzGT71oc/T33yVoSqAz5QGNdtcDCqzANG3rwVV909MduZtLQcpT2udCHoDphlKT/38EKwi4Ewb6ViXyN32hKwuCYSOmyIGm9T/+o/klsVEJfDwyzcSh3iqXFHlQMfIu83WGxW8vqDmcZW70F3uAY4YCdw==
//         9queowM4Q2joiA4f1Du/7w==
//        V1208dgkLKtalUw7yx8/Vg==

        AES aes = new AES();
        byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryedMobile), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
        if (null != resultByte && resultByte.length > 0) {
            String mobileStr = new String(resultByte, "UTF-8");
            Mobile mobile = gson.fromJson(mobileStr,Mobile.class);
            System.out.println(mobile.getPurePhoneNumber());
        }
    }

    public class Mobile{
        String phoneNumber;
        String purePhoneNumber;
        String countryCode;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPurePhoneNumber() {
            return purePhoneNumber;
        }

        public void setPurePhoneNumber(String purePhoneNumber) {
            this.purePhoneNumber = purePhoneNumber;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }
    }

}

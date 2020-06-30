package com.myzing.demoschedule.service;

import com.myzing.demoschedule.bean.FromLogin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

public class ScheduleService {

    public String getCookies(FromLogin fromLogin) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host", "qldt.actvn.edu.vn");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("txtUserName", fromLogin.getUsername());
        body.add("txtPassword", fromLogin.getPassword());
        body.add("btnSubmit", "Đăng nhập");
        body.add("__EVENTTARGET", "");
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        String url = "http://qldt.actvn.edu.vn/CMCSoft.IU.Web.Info/Login.aspx";
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if(response.getHeaders().get("Set-Cookie").size()>1){
            return response.getHeaders().get("Set-Cookie").get(1);
        }
        return response.getHeaders().get("Set-Cookie").get(0);
    }

    public ByteArrayOutputStream getExcel(String cookies) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host", "qldt.actvn.edu.vn");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        httpHeaders.add("Cookie", cookies.replaceAll("\\[", "").replaceAll("]", ""));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        String url = "http://qldt.actvn.edu.vn/CMCSoft.IU.Web.Info/Reports/Form/StudentTimeTable.aspx";

        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (response.getBody() != null) {
            Document doc = Jsoup.parse(response.getBody());
            Element bodyRe = doc.body();
            Elements info = bodyRe.select("option[selected=\"selected\"]");
            body.add("__VIEWSTATE", bodyRe.select("#__VIEWSTATE").get(0).val());
            body.add("__VIEWSTATEGENERATOR", bodyRe.select("#__VIEWSTATEGENERATOR").get(0).val());
            body.add("__EVENTARGUMENT", "");
            body.add("__LASTFOCUS", "");
            body.add("PageHeader1_hidisNotify", bodyRe.select("#PageHeader1_hidisNotify").get(0).val());
            body.add("PageHeader1_hidValueNotify", bodyRe.select("#PageHeader1_hidValueNotify").get(0).val());
            body.add("PageHeader1$drpNgonNgu", info.get(0).val());
            body.add("hidXetHeSoHocPhiTheoDoiTuong", bodyRe.select("#hidXetHeSoHocPhiTheoDoiTuong").get(0).val());
            body.add("hidTuitionFactorMode", bodyRe.select("#hidTuitionFactorMode").get(0).val());
            body.add("hidDiscountFactor", "");
            body.add("hidReduceTuitionType", "");
            body.add("hidXetHeSoHocPhiTheoDoiTuong", bodyRe.select("#hidXetHeSoHocPhiTheoDoiTuong").get(0).val());
            body.add("hidTuitionFactorMode", bodyRe.select("#hidTuitionFactorMode").get(0).val());
            body.add("hidLoaiUuTienHeSoHocPhi", bodyRe.select("#hidLoaiUuTienHeSoHocPhi").get(0).val());
            body.add("hidSecondFieldId", "");
            body.add("hidSecondAyId", "");
            body.add("hidSecondFacultyId", "");
            body.add("hidSecondAdminClassId", "");
            body.add("hidSecondFieldLevel", "");
            body.add("drpSemester", info.get(1).val());
            body.add("drpTerm", info.get(2).val());
            body.add("drpType", "B");
            body.add("btnView", "Xuất file Excel");
            body.add("hidXetHeSoHocPhiDoiTuongTheoNganh", bodyRe.select("#hidXetHeSoHocPhiDoiTuongTheoNganh").get(0).val());
            body.add("hidUnitPriceDetail", bodyRe.select("#hidUnitPriceDetail").get(0).val());
            body.add("hidFacultyId", bodyRe.select("#hidFacultyId").get(0).val());
            body.add("hidFieldLevel", bodyRe.select("#hidFacultyId").get(0).val());
            body.add("hidPrintLocationByCode", bodyRe.select("#hidPrintLocationByCode").get(0).val());
            body.add("hidUnitPriceKP", "");
            body.add("btnView", "Xuất file Excel");
            body.add("hidStudentId", bodyRe.select("#hidStudentId").get(0).val());
            body.add("hidAcademicYearId", bodyRe.select("#hidAcademicYearId").get(0).val());
            body.add("hidFieldId", bodyRe.select("#hidFieldId").get(0).val());
            body.add("hidSemester", bodyRe.select("#hidSemester").get(0).val());
            body.add("hidTerm", bodyRe.select("#hidTerm").get(0).val());
            body.add("hidShowTeacher", bodyRe.select("#hidShowTeacher").get(0).val());
            body.add("hidUnitPrice", bodyRe.select("#hidUnitPrice").get(0).val());
            body.add("hidTuitionCalculating", bodyRe.select("#hidTuitionCalculating").get(0).val());
            body.add("hidTrainingSystemId", bodyRe.select("#hidTrainingSystemId").get(0).val());
            body.add("hidAdminClassId", bodyRe.select("#hidAdminClassId").get(0).val());
            body.add("hidTuitionStudentType", bodyRe.select("#hidTuitionStudentType").get(0).val());
            body.add("hidStudentTypeId", "");
            body.add("hidUntuitionStudentTypeId", "");
            body.add("hidUniversityCode", bodyRe.select("#hidUniversityCode").get(0).val());
            body.add("hidTuanBatDauHK2", "");
            body.add("hidSoTietSang", "");
            body.add("hidSoTietChieu", "");
            body.add("hidSoTietToi", "");
            request = new HttpEntity<>(body, httpHeaders);
//            ResponseEntity<String> response1 = restTemplate.postForEntity(url, request, String.class);
            ResponseEntity<byte[]> response1 = restTemplate.exchange(url, HttpMethod.POST, request, byte[].class);
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            outByteStream.write(response1.getBody());
//            wb.write(outByteStream);
            System.out.println();
            return outByteStream;
//            Path path = Files.write(Paths.get("C:/Users/SOpen/Desktop/ThoiKhoaBieuSinhVien.xls"), response1.getBody());

//            System.out.println(path);
        } else {
            System.out.println("Lỗi không lấy được thông tin file lịch học");
            return null;
        }
    }
}

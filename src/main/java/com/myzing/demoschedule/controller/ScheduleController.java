package com.myzing.demoschedule.controller;

import com.myzing.demoschedule.bean.FromLogin;
import com.myzing.demoschedule.service.ScheduleService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/rest/v1/schedules")
public class ScheduleController {
    ScheduleService scheduleService = new ScheduleService();
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/login")
    public @ResponseBody
    ResponseEntity<byte[]> login(@RequestBody FromLogin form) {
        ByteArrayOutputStream result;
        try {
            String cookies = scheduleService.getCookies(form);
            if (cookies != null) {
                result=  scheduleService.getExcel(cookies);
            } else {
                System.out.println("Lỗi không đăng nhập được");
                return null;
            }
            MultiValueMap<String, String> headers = new HttpHeaders();
            List<String> list = new ArrayList<>();
            list.add("application/vnd.ms-excel");
            headers.put(HttpHeaders.CONTENT_TYPE, list);
            headers.add("Content-Disposition","attachment;filename=file.xls");
            return new ResponseEntity<>(result.toByteArray(),headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}

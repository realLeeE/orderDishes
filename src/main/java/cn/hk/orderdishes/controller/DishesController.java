package cn.hk.orderdishes.controller;

import cn.hk.orderdishes.entity.Dishes;
import cn.hk.orderdishes.service.DishesService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.sun.corba.se.impl.orbutil.concurrent.SyncUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Classname DishesController
 * @Description 点菜
 * @Date 2021/7/23 9:24
 * @Author liyi
 */
@Controller
@RequestMapping(value = "")
public class DishesController {

    @Autowired
    private DishesService dishesService;
    @Value("${dishesUrl}")
    public String dishesUrl;


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @ResponseBody
    @PostMapping("/dishes/{meatNum}/{vegetableNum}")
    public String dishes(@RequestParam(value = "file", required = false) MultipartFile file
            , @PathParam("meatNum") Integer meatNum
            , @PathParam("vegetableNum") Integer vegetableNum) throws IOException {
        List<Dishes> dishes = new ArrayList<>();
        if (null != file && !file.isEmpty()) {
            InputStream inputStream = file.getInputStream();
//            file.transferTo(new File(dishesUrl)); //上传到目录

            byte[] bytes = file.getBytes();
            FileOutputStream fileOutputStream = new FileOutputStream(dishesUrl);
            IoUtil.write(fileOutputStream, true, bytes);

            ExcelReader reader = ExcelUtil.getReader(inputStream);
            dishes = reader.readAll(Dishes.class);
        } else { //取上一次菜单文件
            ExcelReader reader = ExcelUtil.getReader(dishesUrl);
            dishes = reader.readAll(Dishes.class);
        }
        Set<Dishes> dishes1 = dishesService.doOrder(dishes, meatNum, vegetableNum);
        StringBuilder sb = new StringBuilder();
        final Integer[] total = {0};
        dishes1.forEach(ele -> {
            sb.append(ele.getName()).append(" ");
            total[0] += ele.getPrice();
        });
        sb.append(" 价格预计：").append(total[0]);

        return sb.toString();
    }


    @PostMapping(value = "/getDishes")
    @ResponseBody
    public void getExcel(HttpServletResponse response) throws IOException {
        File file = new File(dishesUrl);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + "菜单.xls");

        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            OutputStream os = response.getOutputStream();

            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        }

    }


}

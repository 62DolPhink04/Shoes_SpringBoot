package com.VanLang.application.controller;

import com.VanLang.application.entity.Product;
import com.VanLang.application.entity.Post;
import com.VanLang.application.service.ProductService;
import com.VanLang.application.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeoController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PostService postService;

    // Domain thật trên Render
    private static final String BASE_URL = "https://shoes-springboot.onrender.com";

    // ==========================================
    // 1. TẠO SITEMAP.XML (ĐỘNG)
    // ==========================================
    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String createSitemap() {
        StringBuilder xml = new StringBuilder();

        // Header chuẩn XML
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");

        // Các trang tĩnh
        xml.append(buildUrl("/", "1.0"));
        xml.append(buildUrl("/san-pham", "0.9"));
        xml.append(buildUrl("/tin-tuc", "0.8"));
        xml.append(buildUrl("/lien-he", "0.5"));

        // Các trang Sản phẩm (Lấy từ DB)
        // Mình giữ nguyên tên hàm 'getAllProduct' như code bạn gửi
        List<Product> products = productService.getAllProduct();
        for (Product product : products) {
            // Giả sử link là /shop/detail/{id}
            xml.append(buildUrl("/shop/detail/" + product.getId(), "0.9"));
        }

        // Các trang Bài viết (Lấy từ DB)
        // Mình giữ nguyên tên hàm 'getLatesPost' như code bạn gửi
        List<Post> posts = postService.getLatesPost();
        for (Post post : posts) {
            // Giả sử link là /tin-tuc/{slug}/{id}
            xml.append(buildUrl("/tin-tuc/" + post.getSlug() + "/" + post.getId(), "0.8"));
        }

        xml.append("</urlset>");
        return xml.toString();
    }

    // ==========================================
    // 2. TẠO ROBOTS.TXT (ĐỘNG - THÊM MỚI)
    // ==========================================
    @GetMapping(value = "/robots.txt", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getRobotsTxt() {
        return "User-agent: *\n" +
                // Cho phép tài nguyên
                "Allow: /css/\n" +
                "Allow: /js/\n" +
                "Allow: /images/\n" +
                "Allow: /vendor/\n" +
                "Allow: /adminlte/\n" +
                "\n" +
                // Cấm trang quản trị & riêng tư
                "Disallow: /admin/\n" +
                "Disallow: /api/\n" +
                "Disallow: /error\n" +
                "Disallow: /cart\n" +
                "Disallow: /gio-hang\n" +
                "Disallow: /checkout\n" +
                "Disallow: /thanh-toan\n" +
                "Disallow: /account\n" +
                "Disallow: /tai-khoan\n" +
                "Disallow: /login\n" +
                "Disallow: /register\n" +
                "Disallow: /forgot-password\n" +
                "\n" +
                // Cấm tìm kiếm nội bộ
                "Disallow: /search\n" +
                "Disallow: /tim-kiem\n" +
                "Disallow: /*?q=\n" +
                "\n" +
                // Khai báo Sitemap
                "Sitemap: " + BASE_URL + "/sitemap.xml";
    }

    // ==========================================
    // HÀM PHỤ TRỢ
    // ==========================================
    private String buildUrl(String path, String priority) {
        return "<url>" +
                "<loc>" + BASE_URL + path + "</loc>" +
                "<changefreq>daily</changefreq>" +
                "<priority>" + priority + "</priority>" +
                "</url>";
    }
}
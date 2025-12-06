package com.VanLang.application.controller;

import com.VanLang.application.entity.Product;
import com.VanLang.application.entity.Post;
import com.VanLang.application.service.ProductService;
import com.VanLang.application.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeoController {

    // 1. Khai báo Service để lấy dữ liệu
    @Autowired
    private ProductService productService;

    @Autowired
    private PostService postService;

    // 2. Điền domain thật của bạn trên Render vào đây
    private static final String BASE_URL = "https://shoes-springboot.onrender.com";

    // Cấu hình trả về định dạng XML
    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String createSitemap() {
        StringBuilder xml = new StringBuilder();

        // --- BẮT ĐẦU FILE XML ---
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");

        // --- PHẦN 1: CÁC TRANG TĨNH (Cố định) ---
        xml.append(buildUrl("/", "1.0"));           // Trang chủ (Quan trọng nhất)
        xml.append(buildUrl("/san-pham", "0.9"));   // Trang danh sách sản phẩm
        xml.append(buildUrl("/tin-tuc", "0.8"));    // Trang tin tức
        xml.append(buildUrl("/lien-he", "0.5"));    // Trang liên hệ

        // --- PHẦN 2: SẢN PHẨM (Lấy từ Database) ---
        // LƯU Ý: Bạn cần kiểm tra lại tên hàm trong ProductService của bạn
        // Nó có thể là: findAll(), getListProduct(), getAllProducts()... hãy sửa lại cho đúng.
        List<Product> products = productService.getAllProduct();

        for (Product product : products) {
            // Giả sử đường dẫn xem chi tiết giày là: /shop/detail/{id}
            // Nếu bạn dùng slug thì đổi thành product.getSlug()
            xml.append(buildUrl("/shop/detail/" + product.getId(), "0.9"));
        }

        // --- PHẦN 3: BÀI VIẾT (Lấy từ Database) ---
        // Kiểm tra lại tên hàm trong PostService
        List<Post> posts = postService.getLatesPost();

        for (Post post : posts) {
            // Giả sử đường dẫn xem bài viết là: /tin-tuc/{slug}/{id}
            xml.append(buildUrl("/tin-tuc/" + post.getSlug() + "/" + post.getId(), "0.8"));
        }

        // --- KẾT THÚC FILE XML ---
        xml.append("</urlset>");

        return xml.toString();
    }

    // Hàm phụ trợ để viết thẻ URL cho gọn
    private String buildUrl(String path, String priority) {
        return "<url>" +
                "<loc>" + BASE_URL + path + "</loc>" +
                "<changefreq>daily</changefreq>" +
                "<priority>" + priority + "</priority>" +
                "</url>";
    }
}
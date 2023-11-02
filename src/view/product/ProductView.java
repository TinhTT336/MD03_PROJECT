package view.product;

import config.StringFormatter;
import config.Validation;
import constant.FileName;
import model.category.Category;
import model.order.Order;
import model.orderDetail.OrderDetail;
import model.product.Product;
import service.Service;
import service.productService.ProductService;
import view.category.CategoryView;
import view.user.HomeView;

import java.util.List;
import java.util.stream.Collectors;

import static config.Color.*;

public class ProductView {
    private Service<Product, Long> productService;
    private Service<Category, Long> categoryService;
    private Service<Order, Long> orderService;

    public ProductView() {
        this.productService = new Service<>(FileName.PRODUCT);
        this.categoryService = new Service<>(FileName.CATEGORY);
        this.orderService = new Service<>(FileName.ORDER);
    }

    public void showProductManagement() {
        do {
            System.out.println(PURPLE + "+------------------------------------------------------------------------------------------------+");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "   TMESTICS   \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84                                   Xin chào: %-28s\n", HomeView.userLogin.getFullName() + PURPLE + "                 |");
            System.out.println("|------------------------------------------------------------------------------------------------|");
            System.out.println("|     " + WHITE_BOLD_BRIGHT + "                               \uD83D\uDCE6 QUẢN LÝ SẢN PHẨM                                         " + PURPLE + "|");
            System.out.println("|------------------------------------------------------------------------------------------------|");
            System.out.println("|" + RESET + "                                 1. \uD83D\uDC41️ DANH SÁCH SẢN PHẨM                                       " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 2. ➕ THÊM MỚI SẢN PHẨM                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 3. ✏️ CHỈNH SỬA SẢN PHẨM                                       " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 4. \uD83D\uDD13 ẨN/HIỆN SẢN PHẨM                                         " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 5. \uD83D\uDD0D TÌM KIẾM SẢN PHẨM                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 6. \uD83D\uDD00 SẮP XẾP SẢN PHẨM THEO GIÁ                                " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 7. \uD83D\uDD3D LỌC SẢN PHẨM THEO TRẠNG THÁI                             " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 0. ↩️ QUAY LẠI                                                 " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+\n" + RESET);
            System.out.println("Nhập lựa chọn: ");

            switch (Validation.validateInt()) {
                case 1:
                    showListProduct(productService.findAll());
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    editProduct();
                    break;
                case 4:
//                    deleteProduct();
                    hideProduct();
                    break;
                case 5:
                    searchProduct();
                    break;
                case 6:
                    sortProductByPrice();
                    break;
                case 7:
                    filterProductByStatus();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                    break;
            }
        } while (true);
    }

    private void hideProduct() {
        System.out.println("Nhập mã sản phẩm muốn ẩn/hiện: ");
        int hiddenId = Validation.validateInt();

        Product hiddenProduct = productService.findById((long) hiddenId);
        if (hiddenProduct == null) {
            System.out.println(RED + " Không có sản phẩm với mã vừa nhập " + RESET);
        } else {
            System.out.println("Thông tin sản phẩm muốn chỉnh sửa: ");
            showProduct(hiddenProduct);

            hiddenProduct.setStatus(!hiddenProduct.isStatus());
            productService.save(hiddenProduct);
            if (hiddenProduct.isStatus()) {
                System.out.println("Đã hiện sản phẩm!");
            } else {
                System.out.println("Đã ẩn sản phẩm!");
            }
        }
    }

    public void filterProductByStatus() {
        System.out.println("Lựa chọn trạng thái sản phẩm muốn lọc: ");
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + "   1. CÒN HÀNG             |   2. HẾT HÀNG          |   0. QUAY LẠI              " + PURPLE + "|");
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------+" + RESET);

        System.out.println("Nhập lựa chọn: ");
        switch (Validation.validateInt()) {
            case 1:
                List<Product> trueProduct = productService.findAll().stream().filter(Product::isStatus).collect(Collectors.toList());
                showListProduct(trueProduct);
                break;
            case 2:
                List<Product> falseProduct = productService.findAll().stream().filter(product -> !product.isStatus()).collect(Collectors.toList());
                showListProduct(falseProduct);
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                break;
        }
    }

    public void sortProductByPrice() {
        System.out.println("Lựa chọn cách sắp xếp sản phẩm theo giá: ");
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------+");
        System.out.println("|" + RESET + "   1. GIÁ TĂNG DẦN          |    2. GIÁ GIẢM DẦN        |    0. QUAY LẠI         " + PURPLE + "|");
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------+" + RESET);

        System.out.println("Nhập lựa chọn: ");
        switch (Validation.validateInt()) {
            case 1:
                List<Product> productList = new ProductService().sortProductByIncreasePrice();
                showListProduct(productList);
                break;
            case 2:
                List<Product> products = new ProductService().sortProductByDecreasePrice();
                showListProduct(products);
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                break;
        }
    }

    public void searchProduct() {
        System.out.println("Nhập tên sản phẩm muốn tìm kiếm");
        String productName = Validation.validateString();

        List<Product> productList = new ProductService().searchProduct(productName);
        if (productList == null) {
            showTHead();
            System.out.println("|" + RED + "  Không tìm thấy sản phẩm nào!!!" + RESET);
            showTLine();
        } else {
            showListProduct(productList);
            System.out.println(" Tìm được " + productList.size() + " sản phẩm với tên sản phẩm " + "'" + productName + "'");
            System.out.println();
        }
    }

    private void deleteProduct() {
        System.out.println("Nhập mã sản phẩm muốn xoá: ");
        int deleteId = Validation.validateInt();
        Product deleteProduct = productService.findById((long) deleteId);

        if (deleteProduct == null) {
            System.out.println(RED + " Không có sản phẩm với mã vừa nhập " + RESET);
        } else {
            System.out.println("Thông tin sản phẩm muốn xoá: ");
            showProduct(deleteProduct);

            if (deleteProduct.getStock() > 0) {
                System.out.println("Sản phẩm đang có tồn kho, không được phép xoá");
            } else if (deleteProduct.isStatus()) {
                System.out.println("Sản phẩm đang kinh doanh, không được phép xoá");
            }
            for (Order order : orderService.findAll()) {
                for (OrderDetail orderDetail : order.getOrderDetail()) {
                    if (orderDetail.getProductId() == deleteId) {
                        System.out.println("Sản phẩm đang được bán, không được phép xoá");
                    }
                }
            }
            productService.deleteById((long) deleteId);
            System.out.println("Xoá sản phẩm thành công !!!");
        }
    }

    private void editProduct() {
        System.out.println("Nhập mã sản phẩm muốn chỉnh sửa: ");
        int editId = Validation.validateInt();

        Product editProduct = productService.findById((long) editId);
        if (editProduct == null) {
            System.out.println(RED + " Không có sản phẩm với mã vừa nhập " + RESET);
        } else {
            System.out.println("Thông tin sản phẩm muốn chỉnh sửa: ");
            showProduct(editProduct);

            System.out.println("Chọn thông tin muốn chỉnh sửa: ");
            showTLine();
            System.out.println(PURPLE + "|" + RESET + "  1. TÊN SẢN PHẨM      |    2. DANH MỤC SẢN PHẨM     |       3. MÔ TẢ     |    4. ĐƠN GIÁ    |   5. TỒN KHO   |  0. QUAY LẠI     " + PURPLE + "|");
            showTLine();
            System.out.println("Nhập lựa chọn: ");
            switch (Validation.validateInt()) {
                case 1:
                    System.out.println("Nhập tên sản phẩm muốn chỉnh sửa: ");
                    inputProductName(editProduct);
                    break;
                case 2:
                    editProduct.setCategory(chooseCategory(editProduct));
                    break;
                case 3:
                    System.out.println("Nhập mô tả sản phẩm muốn chỉnh sửa: ");
                    editProduct.setDescription(Validation.validateString());
                    break;
                case 4:
                    System.out.println("Nhập đơn giá sản phẩm muốn chỉnh sửa: ");
                    inputUnitPrice(editProduct);
                    break;
                case 5:
                    System.out.println("Nhập tồn kho sản phẩm muốn chỉnh sửa: ");
                    inputStock(editProduct);
                    break;
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                    break;
            }
            productService.save(editProduct);
            System.out.println("Cập nhật sản phẩm thành công!!!");
        }
    }

    private void addProduct() {
        System.out.println("Nhập số lượng sản phẩm muốn thêm mới");
        int n = Validation.validateInt();

        for (int i = 0; i < n; i++) {
            if (n > 1) {
                System.out.println("Thêm mới sản phẩm thứ " + (i + 1));
            }
            Product newProduct = new Product();
            newProduct.setId(productService.getNewId());

            System.out.println("Nhập tên sản phẩm: ");
            inputProductName(newProduct);

            chooseCategory(newProduct);
            System.out.println("Nhập mô tả sản phẩm: ");
            newProduct.setDescription(Validation.validateString());

            System.out.println("Nhập đơn giá sản phẩm: ");
            inputUnitPrice(newProduct);

            System.out.println("Nhập số lượng tồn kho của sản phẩm: ");
            inputStock(newProduct);

            productService.save(newProduct);
            if (n > 1) {
                System.out.println(PURPLE + "Thêm mới sản phẩm thứ " + (i + 1) + " thành công !!!" + RESET);
            } else {
                System.out.println(PURPLE + "Thêm mới sản phẩm thành công !!!" + RESET);
            }
            System.out.println(" ");
        }

    }

    private void inputStock(Product newProduct) {
        while (true) {
            int stock = Validation.validateInt();
            if (stock < 0) {
                System.out.println(RED + "Tồn kho sản phẩm phải lớn hơn 0" + RESET);
            } else {
                newProduct.setStock(stock);
                break;
            }
        }
    }

    private void inputUnitPrice(Product newProduct) {
        while (true) {
            double price = Double.parseDouble(Validation.validateString());
            if (price < 0) {
                System.out.println(RED + "Đơn giá phải lớn hơn 0" + RESET);
            } else {
                newProduct.setUnitPrice(price);
                break;
            }
        }
    }

    private void inputProductName(Product newProduct) {
        while (true) {
            String productName = Validation.validateString();
            boolean isExist = false;
            for (Product product : productService.findAll()) {
                if (product.getProductName().equalsIgnoreCase(productName)) {
                    isExist = true;
                }
            }
            if (isExist) {
                System.out.println(RED + " Tên sản phẩm đã tồn tại, vui lòng nhập lại" + RESET);
            } else {
                newProduct.setProductName(productName);
                break;
            }
        }
    }

    public void showListProduct(List<Product> products) {
        System.out.println(WHITE_BOLD_BRIGHT + "\nDANH SÁCH SẢN PHẨM                        " + RESET);
        showTHead();
        if (products.isEmpty()) {
            System.out.println("|" + RED + "  Không có sản phẩm nào!!!" + RESET);
            showTLine();
        } else {
            List<Product> productList = products.stream().sorted((p1, p2) -> (int) (-p1.getId() - p2.getId())).collect(Collectors.toList());
            for (Product product : productList) {
                showTBody(product);
            }
            showTLine();
        }
    }

    public void showTrueProduct(List<Product> products) {
        System.out.println(WHITE_BOLD_BRIGHT + "\nDANH SÁCH SẢN PHẨM                        " + RESET);
        showTHead();
        if (products.isEmpty()) {
            System.out.println("|" + RED + "  Không có sản phẩm nào!!!" + RESET);
            showTLine();
        } else {
            for (Product product : products) {
                if (product.isStatus() && product.getCategory().isStatus()) {
                    showTBody(product);
                }
            }
            showTLine();
        }
    }

    public void searchTrueProduct() {
        System.out.println("Nhập tên sản phẩm muốn tìm kiếm:");
        String productName = Validation.validateString();

        List<Product> productList = new ProductService().searchTrueProduct(productName);
        if (productList == null || productList.isEmpty()) {
            showTHead();
            System.out.println("|" + RED + "  Không tìm thấy sản phẩm nào!!!" + RESET);
            showTLine();
        } else {
            showTrueProduct(productList);
            System.out.println(" Tìm được " + productList.size() + " sản phẩm với tên sản phẩm " + "'" + productName + "'");
            System.out.println();
        }
    }

    public void searchTrueProductByCat() {
        System.out.println("Nhập tên danh mục muốn tìm kiếm:");
        String catName = Validation.validateString();
        int count = 0;

        System.out.println(WHITE_BOLD_BRIGHT + "\nDANH SÁCH SẢN PHẨM TÌM KIẾM                        " + RESET);
        showTHead();
        for (Product product : productService.findAll()) {
            if (product.isStatus() && product.getCategory().isStatus()) {
                if (product.getCategory().getCategoryName().toLowerCase().contains(catName.toLowerCase())) {
                    count++;
                    showTBody(product);
                }
            }
        }

        if (count > 0) {
            showTLine();
            System.out.println("Tìm được " + count + " sản phẩm trong danh mục " + "'" + catName + "'");
            System.out.println();
        } else {
            System.out.println("|" + RED + "  Không tìm thấy sản phẩm nào!!!" + RESET);
            showTLine();
            System.out.println();
        }
    }

    public void showCategoryDetail() {
        System.out.println("Các chức năng để lựa chọn: ");
        System.out.println(PURPLE + "+-----------------------------------------------------------------------+");
        System.out.println("|" + RESET + " 1. XEM DANH SÁCH SẢN PHẨM TRONG DANH MỤC    |    0. QUAY LẠI          " + PURPLE + "|");
        System.out.println("+-----------------------------------------------------------------------+" + RESET);
        System.out.println("Nhập lựa chọn: ");

        switch (Validation.validateInt()) {
            case 1:
                System.out.println("Nhập mã danh mục muốn hiển thị chi tiết sản phẩm: ");
                int showIdCat = Validation.validateInt();
                Category showCat = categoryService.findById((long) showIdCat);
                if (showCat == null) {
                    System.out.println(RED + " Không tìm thấy danh mục với mã vừa nhập!!!" + RESET);
                } else {
                    if (!showCat.isStatus()) {
                        System.out.println(RED + " Không tìm thấy danh mục với mã vừa nhập!!!" + RESET);
                        return;
                    }
                    List<Product> productList = new ProductService().searchTrueProductByCatID(showIdCat);
                    if (productList == null || productList.isEmpty()) {
                        System.out.println("Danh mục " + "'" + showCat.getCategoryName() + "'" + " không có sản phẩm nào.");
                        System.out.println();
                    } else {
                        showTrueProduct(productList);
                    }
                }
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                break;
        }
    }

    private Category chooseCategory(Product newProduct) {
        System.out.println("Danh mục sản phẩm có thể chọn: ");
        new CategoryView().showCategoryListForPro();

        System.out.println("Nhập mã danh mục: ");
        Category category = categoryService.findById((long) Validation.validateInt());
        if (category == null) {
            System.out.println(RED + " Không có danh mục với mã vừa nhập, vui lòng chọn lại hoặc thêm mới " + RESET);
            System.out.println(PURPLE + "+--------------------------------------------------------+");
            System.out.println("|" + RESET + " 1. CHỌN LẠI DANH MỤC       | 2. THÊM MỚI DANH MỤC      " + PURPLE + "|");
            System.out.println(PURPLE + "+--------------------------------------------------------+" + RESET);
            switch (Validation.validateInt()) {
                case 1:
                    while (true) {
                        System.out.println("Danh mục sản phẩm có thể chọn: ");
                        new CategoryView().showCategoryListForPro();

                        System.out.println("Nhập danh mục: ");
                        category = categoryService.findById((long) Validation.validateInt());
                        if (category == null) {
                            System.out.println(RED + " Không có danh mục với mã vừa nhập, vui lòng chọn lại " + RESET);
                        } else {
                            newProduct.setCategory(category);
                            break;
                        }
                    }
                    break;
                case 2:
                    new CategoryView().addOneCategory();
                    break;
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                    break;
            }
        } else {
            if (category.isStatus()) {
                newProduct.setCategory(category);
            }
        }
        return category;
    }

    public void showProductDetail() {
        System.out.println("Các chức năng để lựa chọn: ");
        System.out.println(PURPLE + "+--------------------------------------------------------+");
        System.out.println("|" + RESET + " 1. XEM CHI TIẾT SẢN PHẨM    |    0. QUAY LẠI           " + PURPLE + "|");
        System.out.println("+--------------------------------------------------------+" + RESET);

        switch (Validation.validateInt()) {
            case 1:
                System.out.println("Nhập mã sản phẩm để xem chi tiết: ");
                int proDetailId = Validation.validateInt();
                Product productDetail = productService.findById((long) proDetailId);
                showTHead();
                if (productDetail == null) {
                    System.out.println("|" + RED + "  Không có sản phẩm với mã vừa nhập!!!" + RESET);
                } else {
                    System.out.println("CHI TIẾT SẢN PHẨM");
                    showProduct(productDetail);
                }
                break;
            case 0:
                return;
            default:
                System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại" + RESET);
                break;
        }
    }

    public void showProduct(Product product) {
        showTHead();
        showTBody(product);
        showTLine();
    }

    public void showTHead() {
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------------------------------------------------------+ " + RESET);
        System.out.println(PURPLE + "|" + RESET + "  ID |                TÊN                 |  DANH MỤC  |                MÔ TẢ               | ĐƠN GIÁ(VND) | TỒN KHO | TRẠNG THÁI" + PURPLE + "|");
        showTLine();
    }

    public void showTBody(Product product) {
        System.out.printf(PURPLE + "|" + RESET + " %-4d| %-35.35s| %-11s| %-35.35s|   %-10s |     %-4d| %-10s" + PURPLE + "|\n",
                product.getId(), product.getProductName(), product.getCategory().getCategoryName(), product.getDescription(), StringFormatter.formatCurrency(product.getUnitPrice()), product.getStock(), (product.isStatus() ? "Còn hàng" : "Hết hàng"));
    }

    public void showTLine() {
        System.out.println(PURPLE + "+---------------------------------------------------------------------------------------------------------------------------------+ " + RESET);
    }

    public String subString(String description) {
        int maxLength = 35;

        if (description.length() > maxLength) {
            StringBuilder sb = new StringBuilder();
            int index = 0;
            while (index < description.length()) {
                sb.append(description.substring(index, Math.min(index + maxLength, description.length())));
                sb.append(System.lineSeparator());
                index += maxLength;
            }
            description = sb.toString();
        }
        return description;
    }
}

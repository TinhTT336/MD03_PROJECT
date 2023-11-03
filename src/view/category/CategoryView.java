package view.category;

import config.Validation;
import constant.FileName;
import model.category.Category;
import model.product.Product;
import service.Service;
import service.categoryService.CategoryService;
import view.product.ProductView;
import view.user.HomeView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static config.Color.*;
import static config.Color.PURPLE;

public class CategoryView {
    private Service<Category, Long> categoryService;
    private Service<Product, Long> productService;

    public CategoryView() {
        this.categoryService = new Service<>(FileName.CATEGORY);
        this.productService = new Service<>(FileName.PRODUCT);
    }

    public void showCategoryManagement() {
        do {
            System.out.println(PURPLE + "+------------------------------------------------------------------------------------------------+");
            System.out.printf("|" + WHITE_BOLD_BRIGHT + "   TMESTICS   \uD83D\uDC8B(¯`•.¸.•´¯)\uD83D\uDC84                                   Xin chào: %-28s\n", HomeView.userLogin.getFullName() + PURPLE + "                 |");
            System.out.println("+------------------------------------------------------------------------------------------------+");
            System.out.println("|" + WHITE_BOLD_BRIGHT + "                                    \uD83D\uDCC2 QUẢN LÝ DANH MỤC                                         " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+");
            System.out.println("|" + RESET + "                                 1. \uD83D\uDCC2 DANH SÁCH DANH MỤC                                       " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 2. ➕ THÊM MỚI DANH MỤC                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 3. ✏️ CHỈNH SỬA DANH MỤC                                       " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 4. \uD83D\uDD13 ẨN/HIỆN DANH MỤC                                         " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 5. \uD83D\uDD0D TÌM KIẾM DANH MỤC                                        " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 6. \uD83D\uDD00 SẮP XẾP DANH MỤC THEO TÊN                                " + PURPLE + "|");
            System.out.println("|" + RESET + "                                 0. ↩️ QUAY LẠI                                                 " + PURPLE + "|");
            System.out.println("+------------------------------------------------------------------------------------------------+" + RESET);
            System.out.println("Nhập lựa chọn: ");

            switch (Validation.validateInt()) {
                case 1:
                    showCategoryList(categoryService.findAll());
                    if (!categoryService.findAll().isEmpty()) {
                        new ProductView().showCategoryDetail();
                    }
                    break;
                case 2:
                    addCategory();
                    break;
                case 3:
                    editCategory();
                    break;
                case 4:
//                    deleteCategory();
                    hideCategory();
                    break;
                case 5:
                    findCategoryByName();
                    break;
                case 6:
                    sortCategoryByName();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(RED + "Không có chức năng phù hợp, vui lòng chọn lại!!!" + RESET);
                    break;
            }
        } while (true);
    }

    private void hideCategory() {
        System.out.println("Nhập mã danh mục muốn ẩn/hiện: ");
        int hiddenId = Validation.validateInt();

        Category hiddenCategory = categoryService.findById((long) hiddenId);
        if (hiddenCategory == null) {
            System.out.println(RED + "Không có sản phẩm với mã vừa nhập!!! " + RESET);
        } else {
            showCategory(hiddenCategory);

            hiddenCategory.setStatus(!hiddenCategory.isStatus());
            categoryService.save(hiddenCategory);
            updateProductList(hiddenCategory);

            if (hiddenCategory.isStatus()) {
                System.out.println(PURPLE_BRIGHT+"Đã hiện danh mục!"+RESET);
            } else {
                System.out.println(PURPLE_BRIGHT+"Đã ẩn danh mục!"+RESET);
            }
        }
    }

    private void updateProductList(Category category) {
        for (Product product : productService.findAll()) {
            if (product.getCategory().getId().equals(category.getId())) {
                product.setCategory(category);
            }
            productService.save(product);
        }
    }

    private void sortCategoryByName() {
        new CategoryService().sortByName();
        System.out.println("Danh mục sản phẩm sau khi sắp xếp: ");
        showCategoryList(new CategoryService().sortByName());
    }

    public void findCategoryByName() {
        System.out.println("Nhập tên danh mục muốn tìm kiếm: ");
        String catName = Validation.validateString();
        int count = 0;
        showTHead();
        for (Category category : categoryService.findAll()) {
            if (category.getCategoryName().toLowerCase().contains(catName.toLowerCase())) {
                showTBody(category);
                count++;
            }
        }
        if (count > 0) {
            new ProductView().showTLine();
            System.out.println(PURPLE_BRIGHT+"Tìm được " + count + " danh mục theo tên danh mục " + "'" + catName + "'" + " vừa nhập"+RESET);
            System.out.println();
        } else {
            System.out.println("|" + RED + "  Không có danh mục nào!!!" + RESET);
            new ProductView().showTLine();
            System.out.println(" ");
        }
    }

    private void deleteCategory() {
        System.out.println("Nhập mã danh mục muốn xoá: ");
        int deleteId = Validation.validateInt();
        for (Product product : productService.findAll()) {
            if (product.getCategory().getId() == deleteId) {
                System.out.println(RED + "Danh mục có chứa sản phẩm, không được xoá!!!" + RESET);
                break;
            }
        }
        Category deleteCategory = categoryService.findById((long) deleteId);
        if (deleteCategory == null) {
            System.out.println(RED + "Không tìm thấy danh mục với mã vừa nhập!!!" + RESET);
            System.out.println();
        } else {
            System.out.println("Thông tin danh mục muốn xoá: ");
            showCategory(deleteCategory);
            System.out.println(" ");
            categoryService.deleteById((long) deleteId);
            updateProductList(deleteCategory);
            System.out.println(PURPLE_BRIGHT + "Đã xoá danh mục thành công!!!" + RESET);
            System.out.println();
        }
    }

    private void editCategory() {
        System.out.println("Nhập mã danh mục muốn chỉnh sửa: ");
        int editId = Validation.validateInt();
        Category editCategory = categoryService.findById((long) editId);
        if (editCategory == null) {
            System.out.println(RED + "Không tìm thấy danh mục với mã vừa nhập!!!" + RESET);
            System.out.println();
        } else {
            System.out.println("Thông tin danh mục muốn chỉnh sửa: ");
            showCategory(editCategory);
            System.out.println(" ");
            System.out.println("Chọn nội dung cần chỉnh sửa: ");
            System.out.println(PURPLE + "+--------------------------------------------------------------------------------------------------------------+");
            System.out.println("|" + WHITE_BRIGHT + " 1. TÊN DANH MỤC                    | 2. MÔ TẢ DANH MỤC                     | 3. TRẠNG THÁI DANH MỤC                             " + PURPLE + "|");
            System.out.println(PURPLE + "+--------------------------------------------------------------------------------------------------------------+" + RESET);

            switch (Validation.validateInt()) {
                case 1:
                    System.out.println("Nhập tên danh mục muốn thay đổi: ");
                    editCategory.setCategoryName(Validation.validateString());
                    break;
                case 2:
                    System.out.println("Nhập mô tả danh mục muốn thay đổi: ");
                    editCategory.setDescription(Validation.validateString());
                    break;
                case 3:
                    System.out.println("Trạng thái danh mục đã thay đổi: ");
                    editCategory.setStatus(!editCategory.isStatus());
                    break;
                default:
                    System.out.println(RED + "Không có chức năng phù hợp!!!" + RESET);
                    break;
            }
            categoryService.save(editCategory);
            updateProductList(editCategory);
            System.out.println(PURPLE_BRIGHT + "Cập nhật danh mục thành công!" + RESET);
            System.out.println();
        }
    }

    public void addCategory() {
        System.out.println("Nhập số lượng danh mục muốn thêm mới: ");
        int n = Validation.validateInt();
        for (int i = 0; i < n; i++) {
            if (n > 1) {
                System.out.println(PURPLE+"Thêm mới danh mục thứ " + (i + 1)+RESET);
            }
            addOneCategory();
            if (n > 1) {
                System.out.println(PURPLE_BRIGHT + "Thêm mới danh mục thứ " + (i + 1) + " thành công!!!" + RESET);
            } else {
                System.out.println(PURPLE_BRIGHT + "Thêm mới danh mục thành công!" + RESET);
            }
        }
    }

    public void addOneCategory() {
        Category newCategory = new Category();
        newCategory.setId(categoryService.getNewId());

        while (true) {
            System.out.println("Nhập tên danh mục: ");
            String catName = Validation.validateString();
            boolean isExist = false;

            for (Category category : categoryService.findAll()) {
                if (category.getCategoryName().equalsIgnoreCase(catName)) {
                    isExist = true;
                }
            }

            if (isExist) {
                System.out.println(RED + "Tên danh mục đã tồn tại, vui lòng nhập lại!!!" + RESET);
            } else {
                newCategory.setCategoryName(catName);
                break;
            }
        }
        System.out.println("Nhập mô tả danh mục sản phẩm: ");
        newCategory.setDescription(Validation.validateString());

        categoryService.save(newCategory);
    }

    public void showCategoryList(List<Category> categoryList) {
        System.out.println(WHITE_BOLD_BRIGHT + "DANH MỤC SẢN PHẨM                       ");
        showTHead();
        if (categoryService.findAll().isEmpty()) {
            System.out.println("|" + RED + "  Không có danh mục nào!!!" + RESET);
            new ProductView().showTLine();
            System.out.println(" ");
        } else {
            List<Category> categories = categoryList.stream().sorted((c1, c2) -> (int) (-c1.getId() - c2.getId())).collect(Collectors.toList());
            for (Category category : categories) {
                showTBody(category);
            }
            new ProductView().showTLine();
        }
    }

    public void showCategoryListForPro() {
        System.out.println(WHITE_BOLD_BRIGHT + "DANH MỤC SẢN PHẨM                       ");
        showTHead();
        if (categoryService.findAll().isEmpty()) {
            System.out.println("|" + RED + "  Không có danh mục nào!!!" + RESET);
            new ProductView().showTLine();
            System.out.println(" ");
        } else {
            for (Category category : categoryService.findAll()) {
                if (category.isStatus()) {
                    showTBody(category);
                }
            }
            new ProductView().showTLine();
        }
    }

    public void showTHead() {
        new ProductView().showTLine();
        System.out.println(PURPLE + "|" + WHITE_BRIGHT + "      ID      |             DANH MỤC            |                 MÔ TẢ                |     TRẠNG THÁI     |  SỐ LƯỢNG SẢN PHẨM " + PURPLE + "|");
        new ProductView().showTLine();
    }

    public void showTBody(Category category) {
        System.out.printf(PURPLE + "|" + RESET + "      %-8d|     %-28s|         %-29s| %-16s   |        %3s         " + PURPLE + "|\n",
                category.getId(), category.getCategoryName(), category.getDescription(), (category.isStatus() ? "Hoạt động" : "Ngừng kinh doanh"), productService.findAll().stream().filter(cat -> Objects.equals(cat.getCategory().getId(), category.getId())).count());
    }


    public void showCategory(Category category) {
        showTHead();
        showTBody(category);
        new ProductView().showTLine();
    }
}

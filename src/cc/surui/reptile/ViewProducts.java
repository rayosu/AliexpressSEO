package cc.surui.reptile;import java.util.List;import cc.surui.model.Product;public class ViewProducts {	public static void main(String[] args) {		String storeId = "316411";		HtmlInfo htmlInfo = HtmlInfo.create("http://www.aliexpress.com/store/all-wholesale-products/%s.html", storeId);		List<Product> products = htmlInfo.getMyProducts(storeId);		for (Product product : products) {			System.out.printf("%s	%s\n",product.getId(), product.getName());		}	}}
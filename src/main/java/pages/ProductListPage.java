package main.java.pages;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import main.java.util.TestUtil;

public class ProductListPage extends TestUtil{
	

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
	private MobileElement SearchBox;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/item_title")
	private List<MobileElement> Products;

	@AndroidFindBy(xpath = "//*[@text='See All Buying Options']")
	private MobileElement SeeAllBuyingOptions;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_results_count_text")
	private MobileElement SearchCount;

	@AndroidFindBy(xpath = "//*[@resource-id='title']")
	private MobileElement ProductName;

	@AndroidFindBy(xpath = "//*[contains(@text,'Add to Cart')]")
	private MobileElement AddToCart;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/action_bar_cart_count")
	private MobileElement CartIcon;

	@AndroidFindBy(xpath = "//*[@class='android.widget.Button']")
	private MobileElement ProceedToBuy;

	@AndroidFindBy(xpath = "//*[@resource-id='add-to-wishlist-button-submit']")
	private MobileElement AddToWishList;

	@AndroidFindBy(xpath = "//*[@text='Delete']")
	private MobileElement Delete;

	@AndroidFindBy(xpath = "//*[@class='android.view.View'][@index='6']|//*[@class='android.widget.TextView'][@index='6']")
	private MobileElement PlusButton;

	@AndroidFindBy(xpath = "//*[@resource-id='sc-proceed-to-checkout-params-form']/*[@class='android.widget.TextView'][@index='2']")
	private MobileElement Price;

	
	public ProductListPage() {
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
	}
	
	/**
	 * This Method is used to search the product, what we are passing from the excel sheet.
	 * @param productname : We have to pass the Product name which we want to searching
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void searchProduct(String productname) throws Exception {
		
		clickElement(SearchBox, "SearchBox");
		Thread.sleep(1500);
		Sendkeys(SearchBox, productname, "Search name");
		((AndroidDriver<MobileElement>) driver).pressKeyCode(66);
		checkCurrentPage(SearchCount);
		String Results=SearchCount.getText();
		System.out.println("Found Results "+Results);
		
		int num=new Random().nextInt(Products.size());
		String name=Products.get(num).getText();
		System.out.println("Product Name : "+name);
		Products.get(num).click();
		
		checkCurrentPage(AddToWishList);
		String productame=ProductName.getText();
		System.out.println("Product Name :"+productame);
		
		if(name.replaceAll(" ", "").equals(productame.replaceAll(" ", ""))) {
			System.out.println("==============PASS=============");
		}
			
	}

	/**
	 * This method is used to add the product to cart
	 * @throws Exception
	 */
	public void addtoCart() throws Exception {
		if(!checkCurrentPage(SeeAllBuyingOptions)) {
			swipe();
		}
		clickElement(SeeAllBuyingOptions, "SeeAllBuyingOptions");
		clickElement(AddToCart, "Add to Cart button");
		clickElement(CartIcon, "Cart Icon");
		Thread.sleep(1500);
		checkCurrentPage(Price);
		String cost=gettext(Price).replaceAll("[$,]*", "");
		System.out.println("Cost of the product is $:"+cost);
	}
	
	/**
	 * This method id used to update the product quantity in cart page
	 * 
	 * @param qty
	 *            : We have to pass the product quantity.
	 * @throws Exception
	 */
	public void updateQty(String qty) throws Exception {

		if (!qty.equals("1")) {
			String cost = gettext(Price).replaceAll("[$,]*", "");
			System.out.println(cost);

			for (int i = 1; i < Integer.parseInt(qty); i++) {
				clickElement(PlusButton, "Plus Button");
				Thread.sleep(2000);
			}
			
			String cost1 = gettext(Price).replaceAll("[$,]*", "");
			Double total = Double.parseDouble(cost) * Integer.parseInt(qty);
			System.out.println("Total amount" + total);

			Assert.assertNotEquals(cost, cost1);
		}
	}
	
	/**
	 * This method is used to delete the product from cart page
	 * @throws Exception
	 */
	public void deleteProduct() throws Exception {
		
		String numofproducts=gettext(CartIcon);
		clickElement(Delete, "Delete Button");
		Thread.sleep(1500);
		Assert.assertNotEquals(numofproducts, CartIcon.getText());
	}
	
	/**
	 * This method is used to click Proceed to Buy button
	 * @throws Exception
	 */
	public void buyproduct() throws Exception {
		clickElement(ProceedToBuy, "Proceed to Buy");
	}
}

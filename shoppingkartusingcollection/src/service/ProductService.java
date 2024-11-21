package shoppingkartusingcollection.service;

import java.util.ArrayList;
import java.util.Scanner;

import shoppingkartusingcollection.ShoppingKartArrayListOperation;
import shoppingkartusingcollection.entity.OrderDetails;
import shoppingkartusingcollection.entity.Product;
import shoppingkartusingcollection.exception.GlobalExceptionForProduct;

public class ProductService {

	// ArrayList to store and display product
	static ArrayList<Product> product = new ArrayList<Product>();
	// ArrayList to store and display OrderDetails
	static ArrayList<OrderDetails> orders = new ArrayList<OrderDetails>();
	static Scanner sc = new Scanner(System.in);

	public static void addProduct() {

		System.out.println("Enter product Id: ");
		int Id = sc.nextInt();

		sc.nextLine();
		System.out.println("Enter product Name: ");
		String Name = sc.nextLine();
		System.out.println("Enter product Brand: ");
		String Brand = sc.nextLine();
		System.out.println("Enter product Price: ");
		double price = sc.nextDouble();
		System.out.println("Enter product Stock: ");
		int stock = sc.nextInt();

		product.add(new Product(Id, Name, Brand, price, stock));

		System.out.println("Product has been added sucessfully!");

	}

	// To update a product using Id

	public static void updateProductById(int Id) throws GlobalExceptionForProduct {
		boolean status = false;
		for (Product p : product) {
			if (Id == p.getId()) {
				System.out.println("Enter new product Id: ");
				int id = sc.nextInt();
				p.setId(id);

				sc.nextLine();
				System.out.println("Enter new product Name: ");
				String Name = sc.nextLine();
				p.setName(Name);
				System.out.println("Enter new product Brand: ");
				String Brand = sc.nextLine();
				p.setBrand(Brand);
				System.out.println("Enter new product Price: ");
				double price = sc.nextDouble();
				p.setPrice(price);
				System.out.println("Enter new product Stock: ");
				int stock = sc.nextInt();
				p.setStock(stock);

				System.out.println("Product has been updated sucessfully!");
				status = true;
			}
		}
		if (status == false)

			throw new GlobalExceptionForProduct("Product Id not found");

	}

	// To display a product using Id

	public static void getProductById(int Id) throws GlobalExceptionForProduct {
		boolean status = false;
		for (Product p : product) {
			if (Id == p.getId()) {
				System.out.println("Product Name: " + p.getName());
				System.out.println("Product Brand: " + p.getBrand());
				System.out.println("Product Price: " + p.getPrice());
				System.out.println("Product Stock: " + p.getStock());
				status = true;
			}
		}
		if (status == false)

			throw new GlobalExceptionForProduct("No such Id related product can be found!");

	}

	// To delete a product using Id

	public static void deleteProductById(int Id) throws GlobalExceptionForProduct {
		boolean status = false;
		for (Product p : product) {
			if (Id == p.getId()) {
				product.remove(p);
				System.out.println("Product bearing Id " + Id + " has been deleted sucessfully! ");
				status = true;
			}
		}
		if (status == false)
			throw new GlobalExceptionForProduct("No such Id related product can be found!");
	}

	// To display all the products that have been added
	public static void displayAllProduct() {
		for (Product p : product) {
			System.out.println("Product Name: " + p.getName());
			System.out.println("Product Brand: " + p.getBrand());
			System.out.println("Product Price: " + p.getPrice());
			System.out.println("Product Stock: " + p.getStock());
			System.out
					.println("---------------------------------------------------------------------------------------");
		}

	}
	// To delete all the products that have been added

	public static void deleteAllProduct() {
		product.clear();

		System.out.println("All the products has been removed sucessfully!");
		System.out.println("Cart is empty!");

	}

	// To place an order
	public static void orderProduct() {
		sc.nextLine();
		System.out.println("Enter product name: ");
		String name = sc.nextLine();
		System.out.println("=======================================================================================");

		Product selectedProduct = null;

		for (Product p : product) {
			if (p.getName().equalsIgnoreCase(name)) {
				selectedProduct = p;
				break;
			}
		}

		if (selectedProduct == null) {
			System.out.println("OOPS! No such product found!");
			return;
		}

		System.out.println("Product Id: " + selectedProduct.getId());
		System.out.println("Product Name: " + selectedProduct.getName());
		System.out.println("Product Brand: " + selectedProduct.getBrand());
		System.out.println("Product Price: " + selectedProduct.getPrice());

		if (selectedProduct.getStock() <= 0) {
			System.out.println("OOPS! This product is currently unavailable!");
			System.out
					.println("=======================================================================================");
			return;
		}

		System.out.println("Product Stock: " + selectedProduct.getStock());
		System.out.println("=======================================================================================");
		System.out.println("Press 1 To go back to Main menu\nPress 2 To Continue Booking");

		int confirmation = sc.nextInt();
		if (confirmation == 1) {
			ShoppingKartArrayListOperation.mainMenu();
			return;
		}

		System.out.println("Enter quantity: ");
		int quantity = sc.nextInt();

		if (selectedProduct.getStock() >= quantity) {
			int remStock = selectedProduct.getStock() - quantity;
			double total = quantity * selectedProduct.getPrice();
			System.out.println("\nYour Booking has been done successfully!");
			System.out.println(
					"---------------------------------------------------------------------------------------------");
			System.out.println("Please find your booking details below : \n");
			System.out.println("Product Name: " + selectedProduct.getName());
			System.out.println("Product Brand: " + selectedProduct.getBrand());
			System.out.println("Product Quantity: " + quantity);
			System.out.println("Total amount: " + total);

			selectedProduct.setStock(remStock);
			orders.add(new OrderDetails(name, selectedProduct.getBrand(), quantity, selectedProduct.getId(), total));
		} else {
			System.out.println("OOPS! Insufficient stock!");
		}
	}

	// To display all the placed orders

	public static void displayAllOrder() throws GlobalExceptionForProduct {
		System.out.println("Your order history is as follows:  ");
		System.out.println(" ");
		boolean status = false;
		for (OrderDetails o : orders) {
			System.out.println("Product Name: " + o.getName2());
			System.out.println("Product Brand: " + o.getBrand2());
			System.out.println("Product Price: " + o.getTotalAmount());
			System.out.println("Product Stock: " + o.getStock2());
			System.out
					.println("---------------------------------------------------------------------------------------");
			status = true;
		}
		if (status == false)
			throw new GlobalExceptionForProduct("No order history can be found!");
	}

	// To cancel an order

	public static void cancelOrderById(int Id) throws GlobalExceptionForProduct {
		boolean status = false;
		OrderDetails orderToRemove = null;
		for (OrderDetails order : orders) {
			if (Id == order.getProductId()) {
				orderToRemove = order;
				status = true;
				break;
			}
		}
		if (status) {
			orders.remove(orderToRemove);
			System.out.println("Product bearing Id " + Id + " has been deleted sucessfully! ");
		} else
			throw new GlobalExceptionForProduct("No such Id related product can be found!");
	}

}

package com.MenuManagement;

public class Menu {

	    private String itemName;
	    private String category;
	    private String description;
	    private double price;
	    private String status;   
	    public Menu(String itemName, String category,String description,double price,String status) {
			this.itemName = itemName;
			this.category = category;
			this.description = description;
			this.price = price;
			this.status = status;
		}
	    public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {          
			this.price = price;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}

		@Override
	    public String toString() {
	        return "MenuItem{" +
	                ", itemName='" + itemName + '\'' +
	                ", category='" + category + '\'' +
	                ", description='" + description + '\'' +
	                ", price=" + price +
	                ", Status='" + status + '\'' +
	                '}';
	    }
}



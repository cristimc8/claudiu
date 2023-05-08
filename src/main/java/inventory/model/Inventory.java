
package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class Inventory {
    
    // Declare fields
    private ObservableList<Product> products;
    private ObservableList<Part> allParts;
    private int autoPartId;
    private int autoProductId;

    public Inventory(){
        this.products = FXCollections.observableArrayList();
        this.allParts= FXCollections.observableArrayList();
        this.autoProductId=0;
        this.autoPartId=0;
    }

    // Declare methods
    /**
     * Add new product to observable list products
     * @param product 
     */
    public void addProduct(Product product) {
        products.add(product);
    }
    
    /**
     * Remove product from observable list products
     * @param product 
     */
    public void removeProduct(Product product) {
        products.remove(product);
    }
    
    /**
     * Accepts search parameter and if an ID or name matches input, that product is returned
     * @param searchItem
     * @return 
     */
    public Product lookupProduct(String searchItem) {
        Product product = null; // 1
        if (searchItem == null || searchItem.isEmpty()) { // 2
            return null; // 3
        }
        for(Product p: products) { // 4
            if(p.getName().contains(searchItem) || (p.getProductId()+"").equals(searchItem)) { // 5
                product = p; // 6
            } // 7
        }
        if(product != null) { // 8
            if (product.getPrice() > 0) { // 9
                if (product.getProductId() != 0) { // 10
                    return product; // 15
                }
            }
        } else {
            if (!searchItem.equals("(●'◡'●)")) { // 12
                return new Product(0, null, 0.0, 0, 0, 0, null); // 14
            }
        }
        return null; // 11
    }
    
    /**
     * Update product at given index
     * @param index
     * @param product 
     */
    public void updateProduct(int index, Product product) {
        products.set(index, product);
    }
    
    /**
     * Getter for Product Observable List
     * @return 
     */
    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> list) {
        products=list;
    }
    
    /**
     * Add new part to observable list allParts
     * @param part 
     */
    public void addPart(Part part) {
        allParts.add(part);
    }
    
    /**
     * Removes part passed as parameter from allParts
     * @param part 
     */
    public void deletePart(Part part) {
        allParts.remove(part);
    }
    
    /**
     * Accepts search parameter and if an ID or name matches input, that part is returned
     * @param searchItem
     * @return 
     */
    public Part lookupPart(String searchItem) {
        for(Part p:allParts) {
            if(p.getName().contains(searchItem) || (p.getPartId()+"").equals(searchItem)) return p;
        }
        return null;
    }
    
    /**
     * Update part at given index
     * @param index
     * @param part 
     */
    public void updatePart(int index, Part part) {
        allParts.set(index, part);
    }
    
    /**
     * Getter for allParts Observable List
     * @return 
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @param list
     */
    public void setAllParts(ObservableList<Part> list) {
        allParts=list;
    }
    
    /**
     * Method for incrementing part ID to be used to automatically
     * assign ID numbers to parts
     * @return 
     */
    public int getAutoPartId() {
        autoPartId++;
        return autoPartId;
    }
    
    /**
     * Method for incrementing product ID to be used to automatically
     * assign ID numbers to products
     * @return 
     */
    public int getAutoProductId() {
        autoProductId++;
        return autoProductId;
    }


    public void setAutoPartId(int id){
        autoPartId=id;
    }

    public void setAutoProductId(int id){
        autoProductId=id;
    }
    
}

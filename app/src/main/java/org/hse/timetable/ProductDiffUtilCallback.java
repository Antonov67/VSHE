package org.hse.timetable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ProductDiffUtilCallback {

/*    private final List<Product> oldList;
    private final List<Product> newList;

    public ProductDiffUtilCallback(List<Product> oldList, List<Product> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize(){
        return oldList.size();
    }

    @Override
    public int getNewListSize(){
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition){
        Product oldProduct = oldList.get(oldItemPosition);
        Product newProduct = newList.get(newItemPosition);
        return oldProduct.getId() == newProduct.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition){
        Product oldProduct = oldList.get(oldItemPosition);
        Product newProduct = newList.get(newItemPosition);
        return oldProduct.getName().equals(newProduct.getName())
                && oldProduct.getPrice() == newProduct.getPrice();
    }


    List<Product> productList = new LinkedList<>();
    productList.add(new Product(1, "Name1", 100));
    productList.add(new Product(2, "Name21", 200));
    productList.add(new Product(3, "Name3", 300));
    productList.add(new Product(4, "Name4", 400));
    productList.add(new Product(5, "Name5", 501));

    ProductDiffUtilCallback productDiffUtilCallback=
            new ProductDiffUtilCallback(adapter.getData(), productList);
    DiffUtil.DiffResult productDiffResult = DiffUtil.calculateDiff(productDiffUtilCallback);

    adapter.setData(productList);
    productDiffResult.dispatchUpdatesTo(adapter); */


}

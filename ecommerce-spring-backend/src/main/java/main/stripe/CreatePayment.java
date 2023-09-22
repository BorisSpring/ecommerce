package main.stripe;

import com.google.gson.annotations.SerializedName;

public class CreatePayment {

	
    @SerializedName("items")
    CreatePaymentItem[] items;

    



	public CreatePaymentItem[] getItems() {
      return items;
    }

}

package main.stripe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

//@RestController
//@RequestMapping("api/stripe")
public class StripePaymentControlle {

//	@Value("${stripe.apikey}")
//	private String stripeKey;
//
//
//
//	@PostMapping("/create-payment-intent")
//    public CreatePaymentResponse  createPaymentIntent(@RequestBody(required = false) Long amount ) throws StripeException {
//
//		Stripe.apiKey= "sk_test_51Nqs5LHeveq0G9DVZIdJACluk0wDlhZsP9dZfB4JwE4CneoSPACzQnFkLIb2GXIhkE9zFiAs1Co6PfhK3SFyZS8v00Vic2XNKT";
//		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
//				.setCurrency("eur")
//				.setAmount(amount)
//		        .build();
//
//		PaymentIntent intent = PaymentIntent.create(params);
//		return new CreatePaymentResponse(intent.getClientSecret());
//
//     }



	
}
